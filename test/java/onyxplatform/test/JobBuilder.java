package onyxplatform.test;

import clojure.lang.IPersistentMap;
import clojure.lang.PersistentVector;

import org.onyxplatform.api.java.OnyxEnv;
import org.onyxplatform.api.java.Job;
import org.onyxplatform.api.java.Catalog;
import org.onyxplatform.api.java.Lifecycles;

import org.onyxplatform.api.java.utils.MapFns;
import org.onyxplatform.api.java.utils.AsyncCatalog;
import org.onyxplatform.api.java.utils.AsyncLifecycles;

public abstract class JobBuilder {

    protected OnyxEnv onyxEnv;
    protected Integer batchSize;
    protected Integer batchTimeout;
    protected Job job;

    public JobBuilder(String onyxEnvConfig, int batchSize, int batchTimeout) {

	    onyxEnv = new OnyxEnv("onyx-env.edn", true);

	    this.batchSize = new Integer(batchSize);
	    this.batchTimeout = new Integer(batchTimeout);
	    job = createBaseJob();
    }

    public Job createBaseJob() {

	// Tests have a simple 1-fn core async backed
	// workflow that share all bootstrapping with 
	// other tests. Generates all job entries excepting
	// the actual fn catalog entity.
	//
	

	job = new Job(onyxEnv.taskScheduler());

	job.addWorkflowEdge("in", "pass");
	job.addWorkflowEdge("pass", "out");

	Catalog c = job.getCatalog();
	AsyncCatalog.addInput(c, "in", batchSize, batchTimeout);
	AsyncCatalog.addOutput(c, "out", batchSize, batchTimeout);

	Lifecycles lc = job.getLifecycles();
	AsyncLifecycles.addInput(lc, "in");
	AsyncLifecycles.addOutput(lc, "out");

	return job;
    }

    public OnyxEnv getOnyx() {
	    return onyxEnv;
    }

    public Job getJob() {
	return job;
    }

    public Integer batchSize() {
	return batchSize;
    }

    public Integer batchTimeout() {
	return batchTimeout;
    }

    public abstract void configureCatalog();

    public IPersistentMap runJob(PersistentVector inputs) {
	    try {
	    	configureCatalog();
	    	return onyxEnv.submitAsyncJob(job, inputs);
	    } catch (Exception e) {
		shutdown();
		return null;
	    }
    }

    public IPersistentMap runJobCollectOutputs(PersistentVector inputs) {
	    IPersistentMap jmeta = runJob(inputs);
	    return AsyncLifecycles.collectOutputs(job.getLifecycles(), "out"); 
    }

    public void shutdown() {
	    onyxEnv.stopEnv();
    }
}
