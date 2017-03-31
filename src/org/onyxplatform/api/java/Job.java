package org.onyxplatform.api.java;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import clojure.lang.PersistentHashMap;
import clojure.lang.PersistentArrayMap;
import clojure.lang.PersistentVector;
import java.util.Map;

public class Job implements OnyxNames
{
    protected final static IFn kwFn;

    static {
		IFn requireFn = Clojure.var(CORE, Require);
		kwFn = Clojure.var(CORE, Keyword);
    }

    public TaskScheduler taskScheduler;
    public Workflow workflow;
    public Catalog catalog;
    public Lifecycles lifecycles;
    public FlowConditions flowConditions;
    public Windows windows;
    public Triggers triggers;

    public Job(TaskScheduler ts) {
        taskScheduler = ts;
        workflow = new Workflow();
        catalog = new Catalog();
        lifecycles = new Lifecycles();
        flowConditions = new FlowConditions();
        windows = new Windows();
        triggers = new Triggers();
    }

    public Job(Job j) {
	    taskScheduler = j.taskScheduler;
	    workflow = j.workflow;
	    catalog = j.catalog;
	    flowConditions = j.flowConditions;
	    windows = j.windows;
	    triggers = j.triggers;
    }


    public void addWorkflow(Workflow wf) {
	    workflow = wf;
    }

    public void addWorkflowEdge(String srcTask, String dstTask) {
	    workflow.addEdge(srcTask, dstTask);
    }


    public void addCatalog(Catalog cat) {
	    catalog = cat;
    }

    public void addCatalogTask(Task t) {
	    catalog.addTask(t);
    }


    public void addLifecycles(Lifecycles lfcs) {
	    lifecycles = lfcs;
    }

    public void addLifecycle(Lifecycle lf) {
	    lifecycles.addCall(lf);
    }


    public void addFlowConditions(FlowConditions fcs) {
	    flowConditions = fcs;
    }

    public void addFlowCondition(FlowCondition fc) {
	    flowConditions.addCondition(fc);
    }


    public void addWindows(Windows ws) {
	    windows = ws;
    }

    public void addWindow(Window w) {
	    windows.addWindow(w);
    }


    public void addTriggers(Triggers trs) {
	    triggers = trs;
    }

    public void addTrigger(Trigger t) {
	    triggers.addTrigger(t);
    }

    public PersistentArrayMap toCljMap() {

	PersistentArrayMap coercedJob = PersistentArrayMap.EMPTY;

	Object coercedTaskScheduler = taskScheduler.toCljString();
	coercedJob = (PersistentArrayMap) coercedJob.assoc( kwFn.invoke("task-scheduler"),
							    coercedTaskScheduler );

	PersistentVector coercedWorkflow = workflow.cljGraph();
	coercedJob = (PersistentArrayMap) coercedJob.assoc( kwFn.invoke("workflow"),
			                                    coercedWorkflow );

	PersistentVector coercedCatalog = catalog.toCljVector();
	coercedJob = (PersistentArrayMap) coercedJob.assoc( kwFn.invoke("catalog"),
							    coercedCatalog );

	PersistentVector coercedLifecycles = lifecycles.toCljVector();
	coercedJob = (PersistentArrayMap) coercedJob.assoc( kwFn.invoke("lifecycles"),
							    coercedLifecycles );

	PersistentVector coercedFlowConditions = flowConditions.toCljVector();
	coercedJob = (PersistentArrayMap) coercedJob.assoc( kwFn.invoke("flow-conditions"),
							    coercedFlowConditions );

	PersistentVector coercedWindows = windows.toCljVector();
	coercedJob = (PersistentArrayMap) coercedJob.assoc( kwFn.invoke("windows"),
							    coercedWindows );

	PersistentVector coercedTriggers = triggers.toCljVector();
	coercedJob = (PersistentArrayMap) coercedJob.assoc( kwFn.invoke("triggers"),
							    coercedTriggers );

	return coercedJob;
    }
}
