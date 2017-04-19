package org.onyxplatform.api.java;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import clojure.lang.PersistentHashMap;
import clojure.lang.PersistentArrayMap;
import clojure.lang.PersistentVector;
import java.util.Map;

/**
 * A job is the collection of a workflow, catalog, flow conditions,
 * lifecycles, and execution parameters. A job is most coarse unit of work,
 * and every task is associated with exactly one job - hence a peer can only
 * be working at most one job at any given time.
 */
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

    /**
     * Creates a new Job object. Creating a new Job requires passing an existing
     * TaskScheduler object. The new Job will be initialized with empty
     * components, including associated Workflow, Catalog, Lifecycles,
     * FlowConditions, Windows, and Triggers objects.
     * @param  TaskScheduler ts            taskScheduler object specifying
     *                                      running job conditions
     * @return               new Job object
     */
    public Job(TaskScheduler ts) {
        taskScheduler = ts;
        workflow = new Workflow();
        catalog = new Catalog();
        lifecycles = new Lifecycles();
        flowConditions = new FlowConditions();
        windows = new Windows();
        triggers = new Triggers();
    }

    /**
     * Creates a new Job object using an existing Job. This new Job will
     * use the existing Job components, including the existing
     * TaskScheduler, Workflow, Catalog, Lifecycles, FlowConditions,
     * Windows, and Triggers.
     * @param  Job j             existing Job to use as a template for new Job
     * @return     new Job object
     */
    public Job(Job j) {
	    taskScheduler = j.taskScheduler;
	    workflow = j.workflow;
	    catalog = j.catalog;
        lifecycles = j.lifecycles;
	    flowConditions = j.flowConditions;
	    windows = j.windows;
	    triggers = j.triggers;
    }

    /**
     * Sets the Job Workflow to the passed Workflow object.
     * @param Workflow wf Workflow object to be added
     */
    public void setWorkflow(Workflow wf) {
	    workflow = wf;
    }

    /**
     * Returns the current Job Workflow object.
     * @return Workflow object
     */
    public Workflow getWorkflow(){
        return workflow;
    }

    /**
     * Adds a new Workflow Edge to the Job Workflow.
     * @param String srcTask independent Task
     * @param String dstTask dependent Task
     */
    public void addWorkflowEdge(String srcTask, String dstTask) {
	    workflow.addEdge(srcTask, dstTask);
    }

    /**
     * Sets the Job Catalog to the passed Catalog object.
     * @param Catalog cat Catalog object to be added
     */
    public void setCatalog(Catalog cat) {
	    catalog = cat;
    }

    /**
     * Returns the current Job Catalog object.
     * @return Catalog object
     */
    public Catalog getCatalog(){
        return catalog;
    }

    /**
     * Adds a new Task object to the Job Catalog.
     * @param Task t Task to be added to the Catalog
     */
    public void addCatalogTask(Task t) {
	    catalog.addTask(t);
    }

    /**
     * Sets the Job Lifecycles to the passed Lifecycles object.
     * @param Lifecycles lfcs Lifecycles object to be added
     */
    public void setLifecycles(Lifecycles lfcs) {
	    lifecycles = lfcs;
    }

    /**
     * Returns the current Job Lifecycles object.
     * @return Lifecycles object
     */
    public Lifecycles getLifecycles(){
        return lifecycles;
    }

    /**
     * Adds a Lifecycle Call (Lifecycle object) to the existing Job Lifecycles.
     * @param Lifecycle lf Lifecycle to be added to Job Lifecycles
     */
    public void addLifecycle(Lifecycle lf) {
	    lifecycles.addLifecycle(lf);
    }

    /**
     * Sets the Job FlowConditions to the passed FlowConditions object.
     * @param FlowConditions fcs FlowConditions to be added
     */
    public void setFlowConditions(FlowConditions fcs) {
	    flowConditions = fcs;
    }

    /**
     * Returns the current Job FlowConditions object.
     * @return FlowConditions object
     */
    public FlowConditions getFlowConditions(){
        return flowConditions;
    }

    /**
     * Adds a FlowCondition object to the existing Job FlowConditions.
     * @param FlowCondition fc FlowCondition to be added
     */
    public void addFlowCondition(FlowCondition fc) {
	    flowConditions.addCondition(fc);
    }

    /**
     * Sets the Job Windows to the passed Windows object.
     * @param Windows ws Windows to be added
     */
    public void setWindows(Windows ws) {
	    windows = ws;
    }

    /**
     * Returns the current Job Windows object.
     * @return Windows object
     */
    public Windows getWindows(){
        return windows;
    }

    /**
     * Adds a Window object to the existing Job Windows object.
     * @param Window w Window object to be added
     */
    public void addWindow(Window w) {
	    windows.addWindow(w);
    }

    /**
     * Sets the Job Triggers to the passed Triggers object.
     * @param Triggers trs Triggers to be added
     */
    public void setTriggers(Triggers trs) {
	    triggers = trs;
    }

    /**
     * Returns the current Job Triggers object.
     * @return Triggers object
     */
    public Triggers getTriggers(){
        return triggers;
    }

    /**
     * Adds a Trigger object to the existing Job Triggers object.
     * @param Trigger t Trigger to be added
     */
    public void addTrigger(Trigger t) {
	    triggers.addTrigger(t);
    }

    /**
     * Returns the fully described Job.
     * @return onyx Job.
     */
    public PersistentArrayMap toArray() {

    	PersistentArrayMap job = PersistentArrayMap.EMPTY;

    	Object s = taskScheduler.schedule();
    	job = (PersistentArrayMap) job.assoc(kwFn.invoke(OnyxTaskScheduler), s);

    	PersistentVector wf = workflow.graph();
    	job = (PersistentArrayMap) job.assoc(kwFn.invoke(OnyxWorkflow), wf);

    	PersistentVector c = catalog.tasks();
    	job = (PersistentArrayMap) job.assoc(kwFn.invoke(OnyxCatalog), c);

    	PersistentVector l = lifecycles.cycles();
    	job = (PersistentArrayMap) job.assoc(kwFn.invoke(OnyxLifecycles), l);

    	PersistentVector fc = flowConditions.conditions();
    	job = (PersistentArrayMap) job.assoc(kwFn.invoke(OnyxFlowConditions), fc);

    	PersistentVector w = windows.windows();
    	job = (PersistentArrayMap) job.assoc(kwFn.invoke(OnyxWindows), w);

    	PersistentVector t = triggers.triggers();
    	job = (PersistentArrayMap) job.assoc(kwFn.invoke(OnyxTriggers), t);

    	return job;
    }
}
