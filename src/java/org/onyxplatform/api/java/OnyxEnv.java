package org.onyxplatform.api.java;

import java.util.UUID;

import clojure.lang.IPersistentMap;
import clojure.lang.PersistentHashMap;
import clojure.lang.PersistentVector;

import org.onyxplatform.api.java.utils.AsyncCatalog;
import org.onyxplatform.api.java.utils.AsyncLifecycles;
import org.onyxplatform.api.java.utils.MapFns;
import org.onyxplatform.api.java.utils.Timbre;


/**
 * High level functionality and general utilities for environment control,
 * such as peer configuration and environment configuration management, as
 * well as job control (starting, stopping, waiting), and garbage collection.
 * OnyxEnv should be used as an API utility/convenience class, that holds
 * an OnyxEnv object for use in running jobs.
 *
 * **Note** OnyxEnvs contain both EnvConfiguration and PeerConfiguration
 * objects, an OnyxEnv should not be confused with an EnvConfiguration.
 */
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


	/**
	 * Constructs a new OnyxEnv object based on a string representing a path
	 * to an EDN file (that contains a map specifying environment conditions
	 * with keyword value pairs).
	 * @param  onyxEnvConfig (string) path to the EDN file containing environment spec
	 */
	public OnyxEnv(String onyxEnvConfig) {
		OnyxMap config = MapFns.fromResources(onyxEnvConfig);
		applySetupMap(config.toMap());
	}

	/**
	 * Constructs a new OnyxEnv object based on a string representing a path
	 * to an EDN file (that contains a map specifying environment conditions
	 * with keyword value pairs), and a boolean for convenience in automatically
	 * starting the environment upon creation.
	 * @param  onyxEnvConfig (string) path to the EDN file containing environment spec
	 * @param  start         boolean specifies whether the environment should start
	 */
	public OnyxEnv(String onyxEnvConfig, boolean start) {
		this(onyxEnvConfig);
		if (start) {
			startEnv();
		}
	}

	/**
	 * Constructs a new OnyxEnv object directly from a setup map of type IPersistentMap.
	 * The setupMap should contain keyword value pairs directing how the environment
	 * should be constructed.
	 * @param  setupMap      a map containing keywords and values describing the environment
	 * @return                new OnyxEnv object
	 */
	public OnyxEnv applySetupMap(IPersistentMap setupMap){

		if( (boolean) MapFns.get(setupMap, "generateTenancyId") ) {
			generateTenancyId();
		}

		setTaskScheduler(new TaskScheduler((String) MapFns.get(setupMap, "taskScheduler")));
		setVirtualPeerCount(((Long) MapFns.get(setupMap, "virtualPeerCount")).intValue());
		loadEnvConfig((String) MapFns.get(setupMap, "envEdn"));
		loadPeerConfig((String) MapFns.get(setupMap, "peerEdn"));
		String logEdn = (String) MapFns.get(setupMap, "logEdn");

		// Don't force people to use logging
		if (null != logEdn) {
			configureLog(logEdn);
		}

		return this;
	}

	/**
	 * Convenience function for generating a unique UUID automatically. The
	 * id, called a tenancyId, is used to track Environment and Peer Configurations
	 * across job tasks. This method acts on an existing OnyxEnv object.
	 * @return the updated OnyxEnv object
	 */
	public OnyxEnv generateTenancyId(){
		tenancyId = UUID.randomUUID();
		System.out.println("Tenancy ID set to: " + tenancyId);
		return this;
	}

	/**
	 * Sets the tenancyId of an OnyxEnv object to the specified id object.
	 * Returns the updated OnyxEnv object.
	 * @param  id     a tenancyId object to associate with the OnyxEnv.
	 * @return        the updated OnyxEnv object
	 */
	public OnyxEnv setTenancyId(Object id) {
		tenancyId = id;
		return this;
	}

	/**
	 * Returns the tenancyId object associated with the OnyxEnv object.
	 * @return tenancyId object
	 */
	public Object tenancyId() {
		return tenancyId;
	}

	/**
	 * Sets the virtual peer count of the OnyxEnv object to the specified integer.
	 * The number of virtual peers is generally linked to the number of tasks
	 * in a job catalog.
	 * @param  vp            number of virtualPeers to associate with the OnyxEnv object
	 * @return     the updated OnyxEnv object
	 */
	public OnyxEnv setVirtualPeerCount(int vp){
		virtualPeerCount = vp;
		System.out.println("Virtual Peer Count set to: " + virtualPeerCount);
		return this;
	}

	/**
	 * Returns the number of virtual peers associated with the OnyxEnv object.
	 * @return integer number of Virtual peers
	 */
	public int virtualPeerCount() {
		return virtualPeerCount;
	}

	/**
	 * Associates the given taskScheduler object with the OnyxEnv object.
	 * @param  ts      taskScheduler object to associate with the OnyxEnv object
	 * @return               the updated OnyxEnv object
	 */
	public OnyxEnv setTaskScheduler(TaskScheduler ts) {
		taskScheduler = ts;
		return this;
	}

	/**
	 * Returns the TaskScheduler object currently associated with the OnyxEnv object.
	 * @return taskScheduler object
	 */
	public TaskScheduler taskScheduler() {
		return taskScheduler;
	}

	/**
	 * Associates the given EnvConfiguration object with the OnyxEnv object.
	 * @param  ec      an existing EnvConfiguration object to associate with the OnyxEnv object
	 * @return         the updated OnyxEnv object
	 */
	public OnyxEnv setEnvConfig(EnvConfiguration ec) {
		envConfig = ec;
		return this;
	}

	/**
	 * Loads an EnvConfiguration object from a string path to an EDN map file,
	 * creating an EnvConfiguration object from that map, and associating it
	 * with the OnyxEnv object.
	 * @param  envEdn     a string path to the EDN file containing the EnvConfiguration spec
	 * @return        the updated OnyxEnv object
	 */
	public OnyxEnv loadEnvConfig(String envEdn){
		OnyxMap envMap = MapFns.fromResources(envEdn);
		EnvConfiguration ec = new EnvConfiguration(tenancyId, envMap);
		return setEnvConfig(ec);
	}

	/**
	 * Returns the envConfig object associated with the OnyxEnv object.
	 * @return the EnvConfiguration object currently associated with the OnyxEnv object
	 */
	public EnvConfiguration envConfig() {
		return envConfig;
	}

	/**
	 * Associates the given EnvConfiguration object with the OnyxEnv object.
	 * @param  pc    an existing PeerConfiguration object to associate with the OnyxEnv object
	 * @return       the updated OnyxEnv object
	 */
	public OnyxEnv setPeerConfig(PeerConfiguration pc){
		peerConfig = pc;
		return this;
	}

	/**
	 * Loads a PeerConfiguration object from a string path to an EDN map file,
	 * creating a PeerConfiguration object from that map, and associating it
	 * with the OnyxEnv object.
	 * @param  peerEdn    a string path to the EDN file containing the PeerConfiguration spec
	 * @return       the updated OnyxEnv object
	 */
	public OnyxEnv loadPeerConfig(String peerEdn){
		OnyxMap peerMap = MapFns.fromResources(peerEdn);
		PeerConfiguration pc = new PeerConfiguration(tenancyId, peerMap);
		return setPeerConfig(pc);
	}

	/**
	 * Loads the log configuration from the log configuration keyword in the
	 * environment set-up map. Configures the log to the specification found in
	 * the map.
	 * @param logEdn A string containing the path to the log configuration EDN map
	 */
	public void configureLog(String logEdn){
		Timbre.configure(peerConfig, logEdn);
	}

	/**
	 * Returns the peerConfig object currently associated with the OnyxEnv object.
	 * @return the PeerConfiguration object currently associated with the OnyxEnv object
	 */
	public PeerConfiguration peerConfig() {
		return peerConfig;
	}

	/**
	 * Starts the OnyxEnv object.
	 * @return the now started OnyxEnv object
	 */
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

	/**
	 * Stops the OnyxEnv object.
	 * @return the now stopped OnyxEnv object
	 */
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

	/**
	 * Garbage collects on an OnyxEnv (cleans up log, removes orphaned peers, etc).
	 * For more documentation, see the Onyx Docs.
	 * @return true unless an exception happens while garbage collecting
	 */
	public boolean gc() {
		try {
			return API.gc(peerConfig);
		}
		catch (Exception e) {
			System.out.println("GC> " + e);
			return false;
		}
	}

	/**
	 * Submits the specified job object using the OnyxEnv object. The OnyxEnv must
	 * be started to successfully run the Job.
	 * @param  job           Job object to be run in the started OnyxEnv object
	 * @return     a map of the started job if successful
	 */
	public IPersistentMap submitJob(Job job) {
		try {
			return API.submitJob(peerConfig, job);
		}
		catch (Exception e) {
			System.out.println("Submit job failed. Exception follows:");
			System.out.println(e);
			return null;
		}
	}

	/**
	 * Submits the specified job object as an asynchronous job, which binds
	 * the given inputs segments (a PersistentVector of inputs) to core-async
	 * channels via the AsyncLifecycles utility. If successful, returns a map
	 * of the started job.
	 * @param  job        Job object to be run in the started OnyxEnv object
	 * @param  inputs   PersistentVector of inputs to be bound to the job asynchronously
	 * @return             a map of the started job if successful
	 */
	public IPersistentMap submitAsyncJob(Job job, PersistentVector inputs) {
		try {
			System.out.println("Inputs: ");
			System.out.println(inputs);
			AsyncLifecycles.bindInputs(job.getLifecycles(), inputs);
			System.out.println("Inputs bound.");
			return submitJob(job);
		}
		catch (Exception e) {
			System.out.println("Killing job failed. Exception follows:");
			System.out.println(e);
			return null;
		}
	}

	/**
	 * Submits the specified job object as an asynchronous job, which binds
	 * the given inputs segments (a PersistentVector of inputs) to core-async
	 * channels via the AsyncLifecycles utility. If successful, returns a map
	 * of the started job.
	 * @param  job          Job object to be run in the started OnyxEnv object
	 * @param  inputs        OnyxVector of inputs to be bound to the Job asynchronously
	 * @return            a map of the started job if successful
	 */
	public IPersistentMap submitAsyncJob(Job job, OnyxVector inputs) {
		PersistentVector v = inputs.toVector();
		return submitAsyncJob(job, v);
	}

	/**
	 * Uses an IPersistentMap of Job data (metadata) which contains a keyword reference
	 * to the job-id to kill a currently running job.
	 * @param  jobMetadata   IPersistentMap containing a keyword linked to the current job id.
	 * @return               a true/false status of job kill effort
	 * @throws Exception     if the job cannot be killed returns false
	 */
	public boolean killJob(IPersistentMap jobMetadata) throws Exception {
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

	/**
	 * Uses an IPersistentMap of Job data (metadata) which contains a keyword
	 * reference to the job-id to wait for a currently running job to finish
	 * before continuing with the next code block.
	 * @param  jobMetadata  IPersistentMap containing a keyword linked to the current job id.
	 * @return               a true/false status of job completion
	 * @throws Exception      if the job cannot wait (wait attempt failed) returns false
	 */
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

}
