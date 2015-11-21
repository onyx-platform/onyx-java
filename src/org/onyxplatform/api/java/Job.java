package org.onyxplatform.api.java;

public class Job {

    public final TaskScheduler taskScheduler;
    public final Workflow workflow;
    public final Catalog catalog;
    public final Lifecycles lifecycles;
    public final FlowConditions flowConditions;
    public final Windows windows;
    public final Triggers triggers;
    
    public Job(TaskScheduler ts) {
        taskScheduler = ts;
        workflow = null;
        catalog = null;
        lifecycles = null;
        flowConditions = null;
        windows = null;
        triggers = null;
    }
    
    private Job(TaskScheduler ts, Workflow wf, Catalog cat, Lifecycles lfcs, FlowConditions fcs, Windows ws, Triggers trs) {
        taskScheduler = ts;
        workflow = wf;
        catalog = cat;
        lifecycles = lfcs;
        flowConditions = fcs;
        windows = ws;
        triggers = trs;
    }
    
    public Job addWorkflow(Workflow wf) {
        return new Job(taskScheduler, wf, catalog, lifecycles, flowConditions, windows, triggers);
    }
    
    public Job addCatalog(Catalog cat) {
        return new Job(taskScheduler, workflow, cat, lifecycles, flowConditions, windows, triggers);
    }
    
    public Job addLifecycles(Lifecycles lfcs) {
        return new Job(taskScheduler, workflow, catalog, lifecycles, flowConditions, windows, triggers);
    }
    
    public Job addFlowConditions(FlowConditions fcs) {
        return new Job(taskScheduler, workflow, catalog, lifecycles, fcs, windows, triggers);
    }
    
    public Job addWindows(Windows ws) {
        return new Job(taskScheduler, workflow, catalog, lifecycles, flowConditions, ws, triggers);
    }
    
    public Job addTriggers(Triggers trs) {
        return new Job(taskScheduler, workflow, catalog, lifecycles, flowConditions, windows, trs);
    }
    
}
