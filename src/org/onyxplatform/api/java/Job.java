package org.onyxplatform.api.java;

public class Job {

    public final TaskScheduler taskScheduler;
    public final Workflow workflow;
    public final Catalog catalog;
    public final Lifecycles lifecycles;
    
    public Job(TaskScheduler ts) {
        taskScheduler = ts;
        workflow = null;
        catalog = null;
        lifecycles = null;
    }
    
    private Job(TaskScheduler ts, Workflow wf, Catalog cat, Lifecycles lfcs) {
        taskScheduler = ts;
        workflow = wf;
        catalog = cat;
        lifecycles = lfcs;
    }
    
    public Job addWorkflow(Workflow wf) {
        return new Job(taskScheduler, wf, catalog, lifecycles);
    }
    
    public Job addCatalog(Catalog cat) {
        return new Job(taskScheduler, workflow, cat, lifecycles);
    }
    
    public Job addLifecycles(Lifecycles lfcs) {
        return new Job(taskScheduler, workflow, catalog, lfcs);
    }
    
}
