package onyxplatform.test;

import clojure.lang.IPersistentMap;
import clojure.lang.PersistentVector;

import org.onyxplatform.api.java.OnyxEnv;
import org.onyxplatform.api.java.Job;
import org.onyxplatform.api.java.Catalog;
import org.onyxplatform.api.java.Lifecycles;

import org.onyxplatform.api.java.utils.MapFns;
import org.onyxplatform.api.java.utils.AsyncLifecycles;

public abstract class JobBuilder {

    protected OnyxEnv onyxEnv;
    protected int batchSize;
    protected int batchTimeout;
    protected Job job;

    public JobBuilder(String onyxEnvConfig, int batchSize, int batchTimeout) {

	    onyxEnv = new OnyxEnv("onyx-env.edn", true);
	    job = createBaseJob();
    }

    public Job createBaseJob() {

	// Tests have a simple 1-fn core async backed
	// workflow that share all bootstrapping with 
	// other tests. Generates all job entries excepting
	// the actual fn catalog entity.
	//
	

	job = new Job(taskScheduler());

	job.addWorkflowEdge("in", "pass");
	job.addWorkflowEdge("pass", "out");

	Catalog c = job.getCatalog();
	AsyncCatalog.addInput(c, "in", batchSize, batchTimeout);
	AsyncCatalog.addOutput(c, "out", batchSize, batchTimeout);

	Lifecycles lc = job.getLifecycles();
	AsyncCatalog.addInput(lc, "in");
	AsyncCatalog.addOutput(lc, "out");

	return job;
    }

    public Job getJob() {
	return job;
    }

    public abstract void configureCatalog();

    public IPersistentMap runJob(PersistentVector inputs) {
	    configureCatalog();
	    return onxyEnv.submitAsyncJob(job, inputs);
    }

    public IPersistentMap runJobCollectOutputs(PersistentVector inputs) {
	    IPersistentMap jmeta = runJob(inputs);
	    return AsyncLifecycles.collectOutputs(job.lifecycles(), "out"); 
    }
}
