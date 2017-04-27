package onyxplatform.test;

import java.util.UUID;

import clojure.lang.IPersistentMap;
import clojure.lang.PersistentHashMap;
import clojure.lang.PersistentVector;
import java.util.HashMap;

import org.onyxplatform.api.java.*;
import org.onyxplatform.api.java.instance.*;
import org.onyxplatform.api.java.utils.*;

public abstract class SingleFnTest {

	public Job job;
	public TaskScheduler taskScheduler;
	public Task task;
	public Catalog catalog;
	public Workflow workflow;
	public EnvConfiguration envConfig;
	public PeerConfiguration peerConfig;
	public Lifecycles lifecycles;
	public FlowConditions flowConditions;

	public Object tenancyId;
	public int batchSize;
	public int batchTimeout;
	public int virtualPeerCount;

	public Object onyxEnv;
	public Object peerGroup;
	public Object virtualPeers;
	public IPersistentMap runningJob;

	public void defaultSetup(IPersistentMap setupMap){
		setBatchSize(((Long) MapFns.get(setupMap, "batchSize")).intValue());
        setBatchTimeout(((Long) MapFns.get(setupMap, "batchTimeout")).intValue());
		setVirtualPeerCount(((Long) MapFns.get(setupMap, "virtualPeerCount")).intValue());
		createTaskScheduler((String) MapFns.get(setupMap, "taskScheduler"));
		setTenancyId();
		createEnvConfig((String) MapFns.get(setupMap, "envEdn"));
		createPeerConfig((String) MapFns.get(setupMap, "peerEdn"));
		createCatalog();
		updateCatalog();
		createFlowConditions();
		createLifecycles();
		createWorkflow();
		createJob();
	}

	public void setTenancyId(){
		tenancyId = UUID.randomUUID();
		System.out.println("Tenancy ID set to: ");
		System.out.println(tenancyId);
	}

	public void setBatchSize(int bs){
		batchSize = bs;
		System.out.println("Batch Size set to: ");
		System.out.println(batchSize);
	}

	public void setBatchTimeout(int bt){
		batchTimeout = bt;
		System.out.println("Batch Timeout set to: ");
		System.out.println(batchTimeout);
	}

	public void setVirtualPeerCount(int vp){
		virtualPeerCount = vp;
		System.out.println("Virtual Peer Count set to: ");
		System.out.println(virtualPeerCount);
	}

	public void createTaskScheduler(String tsString){
		taskScheduler = new TaskScheduler(tsString);
		System.out.println("Task Scheduler Created: ");
		System.out.println(taskScheduler.toString());
	}


	public abstract void createCatalog();

	public abstract void setup();

	public void updateCatalog(){
		catalog = AsyncCatalog.addInput(catalog, "in", batchSize, batchTimeout);
		catalog = AsyncCatalog.addOutput(catalog, "out", batchSize, batchTimeout);
		System.out.println("Catalog Updated: ");
		System.out.println(catalog.toString());
	}

	public void createFlowConditions(){
		flowConditions = new FlowConditions();
		System.out.println("FlowConditions Created: ");
		System.out.println(flowConditions.toString());
	}

	public void createEnvConfig(String envEdn){
		OnyxMap envMap = MapFns.fromResources(envEdn);
		envConfig = new EnvConfiguration(tenancyId, envMap);
		System.out.println("EnvConfig Created: ");
		System.out.println(envConfig.toString());
	}

	public void createPeerConfig(String peerEdn){
		OnyxMap peerMap = MapFns.fromResources(peerEdn);
		peerConfig = new PeerConfiguration(tenancyId, peerMap);
		System.out.println("PeerConfig Created: ");
		System.out.println(peerConfig.toString());
	}

	public void createLifecycles(){
		lifecycles = new Lifecycles();
		lifecycles = AsyncLifecycles.addInput(lifecycles, "in");
		lifecycles = AsyncLifecycles.addOutput(lifecycles, "out");
		System.out.println("Lifecycles Created: ");
		System.out.println(lifecycles.toString());
	}

	public void createWorkflow(){
		workflow = new Workflow();
		workflow.addEdge("in", "pass");
		workflow.addEdge("pass", "out");
		System.out.println("Workflow Created: ");
		System.out.println(workflow.toString());
	}

	public void createJob(){
		job = new Job(taskScheduler);
		job.setCatalog(catalog);
		job.setWorkflow(workflow);
		job.setLifecycles(lifecycles);
		job.setFlowConditions(flowConditions);
		System.out.println("Job Created: ");
		System.out.println(job.toString());
	}


	public IPersistentMap runJob(PersistentVector inputs){
		System.out.println("Inputs: ");
		System.out.println(inputs);
		onyxEnv = API.startEnv(envConfig);
		System.out.println("Onyx environment running.");
		peerGroup = API.startPeerGroup(peerConfig);
		System.out.println("Peer group started.");
		virtualPeers = API.startPeers(virtualPeerCount, peerGroup);
		System.out.println("Virtual peers created.");
		AsyncLifecycles.bindInputs(job.getLifecycles(), inputs);
		System.out.println("Inputs bound.");
		runningJob = API.submitJob(peerConfig, job);
		System.out.println("Job submitted.");
		return AsyncLifecycles.collectOutputs(lifecycles, "out");
	}

	public void shutdownEnv(){
		System.out.println("Beginning shutdown...");
		API.shutdownPeers(virtualPeers);
		System.out.println("Shutdown virtual peers.");
		API.shutdownPeerGroup(peerGroup);
		System.out.println("Shutdown peer group.");
		API.shutdownEnv(onyxEnv);
		System.out.println("Shutdown onyx env. Shutdown complete.");
	}

	public IPersistentMap runTest(PersistentVector inputs) throws Exception{
		try {
			System.out.println("Starting job.");
			setup();
			return runJob(inputs);
		}
		catch (Exception e) {
			System.out.println("Exception.");
		}
		finally {
			shutdownEnv();
		}
		return null;
	}


}
