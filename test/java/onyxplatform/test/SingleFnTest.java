package onyxplatform.test;

import java.util.UUID;

import clojure.lang.IPersistentMap;
import clojure.lang.PersistentHashMap;
import clojure.lang.PersistentVector;

import org.onyxplatform.api.java.API;
import org.onyxplatform.api.java.Job;
import org.onyxplatform.api.java.TaskScheduler;
import org.onyxplatform.api.java.Task;
import org.onyxplatform.api.java.Catalog;
import org.onyxplatform.api.java.Workflow;
import org.onyxplatform.api.java.EnvConfiguration;
import org.onyxplatform.api.java.PeerConfiguration;
import org.onyxplatform.api.java.Lifecycles;
import org.onyxplatform.api.java.FlowConditions;
import org.onyxplatform.api.java.OnyxMap;

import org.onyxplatform.api.java.utils.AsyncCatalog;
import org.onyxplatform.api.java.utils.AsyncLifecycles;
import org.onyxplatform.api.java.utils.MapFns;

public abstract class SingleFnTest {

	private Job job;
	private TaskScheduler taskScheduler;
	private Task task;
	protected Catalog catalog;
	private Workflow workflow;
	private EnvConfiguration envConfig;
	private PeerConfiguration peerConfig;
	private Lifecycles lifecycles;
	private FlowConditions flowConditions;

	private Object tenancyId;
	protected int batchSize;
	protected int batchTimeout;
	protected int virtualPeerCount;

	private Object onyxEnv;
	private Object peerGroup;
	private Object virtualPeers;
	private IPersistentMap runningJob;

	/**
	 * Abstract methods
	 */
	protected abstract void createCatalog();
	protected abstract void setup();


	protected void defaultSetup(IPersistentMap setupMap){
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

	private void setTenancyId(){
		tenancyId = UUID.randomUUID();
		System.out.println("Tenancy ID set to: ");
		System.out.println(tenancyId);
	}

	private void setBatchSize(int bs){
		batchSize = bs;
		System.out.println("Batch Size set to: ");
		System.out.println(batchSize);
	}

	private void setBatchTimeout(int bt){
		batchTimeout = bt;
		System.out.println("Batch Timeout set to: ");
		System.out.println(batchTimeout);
	}

	private void setVirtualPeerCount(int vp){
		virtualPeerCount = vp;
		System.out.println("Virtual Peer Count set to: ");
		System.out.println(virtualPeerCount);
	}

	private void createTaskScheduler(String tsString){
		taskScheduler = new TaskScheduler(tsString);
		System.out.println("Task Scheduler Created: ");
		System.out.println(taskScheduler.toString());
	}

	private void updateCatalog(){
		catalog = AsyncCatalog.addInput(catalog, "in", batchSize, batchTimeout);
		catalog = AsyncCatalog.addOutput(catalog, "out", batchSize, batchTimeout);
		System.out.println("Catalog Updated: ");
		System.out.println(catalog.toString());
	}

	private void createFlowConditions(){
		flowConditions = new FlowConditions();
		System.out.println("FlowConditions Created: ");
		System.out.println(flowConditions.toString());
	}

	private void createEnvConfig(String envEdn){
		OnyxMap envMap = MapFns.fromResources(envEdn);
		envConfig = new EnvConfiguration(tenancyId, envMap);
		System.out.println("EnvConfig Created: ");
		System.out.println(envConfig.toString());
	}

	private void createPeerConfig(String peerEdn){
		OnyxMap peerMap = MapFns.fromResources(peerEdn);
		peerConfig = new PeerConfiguration(tenancyId, peerMap);
		System.out.println("PeerConfig Created: ");
		System.out.println(peerConfig.toString());
	}

	private void createLifecycles(){
		lifecycles = new Lifecycles();
		lifecycles = AsyncLifecycles.addInput(lifecycles, "in");
		lifecycles = AsyncLifecycles.addOutput(lifecycles, "out");
		System.out.println("Lifecycles Created: ");
		System.out.println(lifecycles.toString());
	}

	private void createWorkflow(){
		workflow = new Workflow();
		workflow.addEdge("in", "pass");
		workflow.addEdge("pass", "out");
		System.out.println("Workflow Created: ");
		System.out.println(workflow.toString());
	}

	private void createJob(){
		job = new Job(taskScheduler);
		job.setCatalog(catalog);
		job.setWorkflow(workflow);
		job.setLifecycles(lifecycles);
		job.setFlowConditions(flowConditions);
		System.out.println("Job Created: ");
		System.out.println(job.toString());
	}

	private IPersistentMap executeJob(PersistentVector inputs){
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

	private void shutdownEnv(){
		System.out.println("Beginning shutdown...");
		API.shutdownPeers(virtualPeers);
		System.out.println("Shutdown virtual peers.");
		API.shutdownPeerGroup(peerGroup);
		System.out.println("Shutdown peer group.");
		API.shutdownEnv(onyxEnv);
		System.out.println("Shutdown onyx env. Shutdown complete.");
	}

	public IPersistentMap runJob(PersistentVector inputs) throws Exception{
		try {
			System.out.println("Starting job.");
			setup();
			return executeJob(inputs);
		}
		catch (Exception e) {
			System.out.println("Job evaluation failed. Exception follows:");
			System.out.println(e);
		}
		finally {
			shutdownEnv();
		}
		return null;
	}
}
