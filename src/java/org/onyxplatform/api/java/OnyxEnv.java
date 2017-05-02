package org.onyxplatform.api.java;

import java.util.UUID;

import clojure.lang.IPersistentMap;
import clojure.lang.PersistentHashMap;
import clojure.lang.PersistentVector;

import org.onyxplatform.api.java.API;
import org.onyxplatform.api.java.TaskScheduler;
import org.onyxplatform.api.java.EnvConfiguration;
import org.onyxplatform.api.java.PeerConfiguration;
import org.onyxplatform.api.java.OnyxMap;

import org.onyxplatform.api.java.utils.AsyncCatalog;
import org.onyxplatform.api.java.utils.AsyncLifecycles;
import org.onyxplatform.api.java.utils.MapFns;

public class OnyxEnv {

	protected IPersistentMap onyxEnvConfig;

	protected TaskScheduler taskScheduler;
	protected EnvConfiguration envConfig;
	protected PeerConfiguration peerConfig;

	protected Object tenancyId;

	protected int virtualPeerCount;
	protected Object onyxEnv;
	protected Object peerGroup;
	protected Object virtualPeers;


	public OnyxEnv(String onyxEnvConfig) {
		OnyxMap config = MapFns.fromResources(onyxEnvConfig);
		applySetupMap(config.toMap());
	}

	public OnyxEnv(String onyxEnvConfig, boolean start) {
		this(onyxEnvConfig);
		if (start) {
			startEnv();
		}
	}

	public OnyxEnv applySetupMap(IPersistentMap setupMap){

		if( (boolean) MapFns.get(setupMap, "generateTenancyId") ) {
			generateTenancyId();
		}

		setTaskScheduler(new TaskScheduler((String) MapFns.get(setupMap, "taskScheduler")));
		setVirtualPeerCount(((Long) MapFns.get(setupMap, "virtualPeerCount")).intValue());
		loadEnvConfig((String) MapFns.get(setupMap, "envEdn"));
		loadPeerConfig((String) MapFns.get(setupMap, "peerEdn"));
		return this;
	}

	public OnyxEnv generateTenancyId(){
		tenancyId = UUID.randomUUID();
		System.out.println("Tenancy ID set to: " + tenancyId);
		return this;
	}

	public OnyxEnv setTenancyId(Object id) {
		tenancyId = id;
		return this;
	}

	public Object tenancyId() {
		return tenancyId;
	}

	public OnyxEnv setVirtualPeerCount(int vp){
		virtualPeerCount = vp;
		System.out.println("Virtual Peer Count set to: " + virtualPeerCount);
		return this;
	}

	public int virtualPeerCount() {
		return virtualPeerCount;
	}

	public OnyxEnv setTaskScheduler(TaskScheduler ts) {
		taskScheduler = ts;
		System.out.println("Task Scheduler Created: " + taskScheduler);
		return this;
	}

	public TaskScheduler taskScheduler() {
		return taskScheduler;
	}

	public OnyxEnv setEnvConfig(EnvConfiguration ec) {
		envConfig = ec;
		System.out.println("EnvConfig set: " + envConfig);
		return this;
	}

	public OnyxEnv loadEnvConfig(String envEdn){
		OnyxMap envMap = MapFns.fromResources(envEdn);
		EnvConfiguration ec = new EnvConfiguration(tenancyId, envMap);
		return setEnvConfig(ec);
	}

	public EnvConfiguration envConfig() {
		return envConfig;
	}

	public OnyxEnv setPeerConfig(PeerConfiguration pc){
		peerConfig = pc;
		System.out.println("PeerConfig set: " + peerConfig);
		return this;
	}

	public OnyxEnv loadPeerConfig(String peerEdn){
		OnyxMap peerMap = MapFns.fromResources(peerEdn);
		PeerConfiguration pc = new PeerConfiguration(tenancyId, peerMap);
		return setPeerConfig(pc);
	}

	public PeerConfiguration peerConfig() {
		return peerConfig;
	}

	public OnyxEnv startEnv() {
		System.out.println("Starting Onyx environment.");
		onyxEnv = API.startEnv(envConfig);
		System.out.println("Onyx environment started.");
		peerGroup = API.startPeerGroup(peerConfig);
		System.out.println("Peer group started.");
		virtualPeers = API.startPeers(virtualPeerCount, peerGroup);
		System.out.println("Virtual peers created.");
		return this;
	}

	public OnyxEnv stopEnv() {
		System.out.println("Beginning shutdown...");
		API.shutdownPeers(virtualPeers);
		System.out.println("Shutdown virtual peers.");
		API.shutdownPeerGroup(peerGroup);
		System.out.println("Shutdown peer group.");
		API.shutdownEnv(onyxEnv);
		System.out.println("Shutdown onyx env. Shutdown complete.");
		return this;
	}

	public boolean gc() {
		try {
			return API.gc(peerConfig);
		}
		catch (Exception e) {
			System.out.println("GC> " + e);
			return false;
		}
	}

	public IPersistentMap submitJob(Job job){
		try {
			return API.submitJob(peerConfig, job);
		}
		catch (Exception e) {
			System.out.println("Submit job failed. Exception follows:");
			System.out.println(e);
			return null;
		}
	}

	public IPersistentMap submitAsyncJob(Job job, PersistentVector inputs){
		try {
			System.out.println("Inputs: ");
			System.out.println(inputs);
			AsyncLifecycles.bindInputs(job.getLifecycles(), inputs);
			System.out.println("Inputs bound.");
			return API.submitJob(peerConfig, job);
		}
		catch (Exception e) {
			System.out.println("Killing job failed. Exception follows:");
			System.out.println(e);
			return null;
		}
	}

	public boolean killJob(IPersistentMap jobMetadata) throws Exception{
		try {
			String jobId = (MapFns.get(jobMetadata, "job-id")).toString();
			System.out.println("Killing job...");
			boolean killStatus = API.killJob(peerConfig, jobId);
			System.out.println("Kill status: ");
			System.out.println(killStatus);
			return killStatus;
		}
		catch (Exception e) {
			System.out.println("Killing job failed. Exception follows:");
			System.out.println(e);
			return false;
		}
	}

	public boolean awaitJobCompletion(IPersistentMap jobMetadata) throws Exception{
		try {
			String jobId = (MapFns.get(jobMetadata, "job-id")).toString();
			return API.awaitJobCompletion(peerConfig, jobId);
		}
		catch (Exception e) {
			System.out.println("Await job completion failed. Exception follows:");
			System.out.println(e);
			return false;
		}
	}

	// TODO: toString?
}
