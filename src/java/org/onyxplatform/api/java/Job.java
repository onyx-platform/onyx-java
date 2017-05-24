package org.onyxplatform.api.java;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import clojure.lang.PersistentHashMap;
import clojure.lang.PersistentArrayMap;
import clojure.lang.IPersistentMap;
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
    public Metadata metadata;

    /**
     * Constructs a new Job object. Creating a new Job requires passing an existing
     * TaskScheduler object. The new Job will be initialized with empty
     * components, including associated Workflow, Catalog, Lifecycles,
     * FlowConditions, Windows, and Triggers objects.
     * @param  ts            taskScheduler object specifying running job conditions
     */
    public Job(TaskScheduler ts) {
        taskScheduler = ts;
        workflow = new Workflow();
        catalog = new Catalog();
        lifecycles = new Lifecycles();
        flowConditions = new FlowConditions();
        windows = new Windows();
        triggers = new Triggers();
        metadata = new Metadata();
    }

    /**
     * Constructs a new Job object based on an existing Job object. This new Job will
     * use the existing Job object components, including the existing
     * TaskScheduler, Workflow, Catalog, Lifecycles, FlowConditions,
     * Windows, and Triggers.
     * @param  j             existing Job to use as a template for new Job
     */
    public Job(Job j) {
	    taskScheduler = j.taskScheduler;
	    workflow = j.workflow;
	    catalog = j.catalog;
        lifecycles = j.lifecycles;
	    flowConditions = j.flowConditions;
	    windows = j.windows;
	    triggers = j.triggers;
        metadata = j.metadata;
    }

    /**
     * Associates the specified Workflow object to the Job.
     * Returns the updated job object so that methods can be chained.
     * @param wf workflow object to be associated with the job
     * @return the updated Job object
     */
    public Job setWorkflow(Workflow wf) {
	    workflow = wf;
	    return this;
    }

    /**
     * Returns the Workflow object currently associated with the job object.
     * @return Workflow object
     */
    public Workflow getWorkflow(){
        return workflow;
    }

    /**
     * Adds a new Workflow Edge to the Job Workflow.
     * Returns the updated job object so that methods can be chained.
     * @param srcTask independent Task edge
     * @param dstTask dependent Task edge
     * @return the updated Job object
     */
    public Job addWorkflowEdge(String srcTask, String dstTask) {
	    workflow.addEdge(srcTask, dstTask);
	    return this;
    }

    /**
     * Associates the specified Catalog object to the Job.
     * Returns the updated job object so that methods can be chained.
     * @param cat Catalog object to be associated with the job
     * @return the updated Job object
     */
    public Job setCatalog(Catalog cat) {
	    catalog = cat;
	    return this;
    }

    /**
     * Returns the Catalog object currently associated with the Job.
     * Returns the updated job object so that methods can be chained.
     * @return Catalog object
     */
    public Catalog getCatalog(){
        return catalog;
    }

    /**
     * Adds a new Task object to the Job Catalog.
     * Returns the updated job object so that methods can be chained.
     * @param t Task to be added to the Job Catalog
     * @return the updated job object
     */
    public Job addCatalogTask(Task t) {
	    catalog.addTask(t);
	    return this;
    }

    /**
     * Associates the specified Lifecycles object to the Job.
     * Returns the updated job object so that methods can be chained.
     * @param lfcs Lifecycles object to be associated with the job
     * @return the updated job object
     */
    public Job setLifecycles(Lifecycles lfcs) {
	    lifecycles = lfcs;
	    return this;
    }

    /**
     * Returns the Lifecycles object currently associated with the job.
     * @return Lifecycles object
     */
    public Lifecycles getLifecycles(){
        return lifecycles;
    }

    /**
     * Adds a Lifecycle Call (Lifecycle object) to the existing Job Lifecycles.
     * Returns the updated job object so that methods can be chained.
     * @param lf Lifecycle to be added to Job Lifecycles
     * @return the updated job object
     */
    public Job addLifecycle(Lifecycle lf) {
	    lifecycles.addLifecycle(lf);
	    return this;
    }

    /**
     * Associates the specified FlowConditions object to the Job.
     * Returns the updated job object so that methods can be chained.
     * @param fcs FlowConditions to be associated with the Job
     * @return the updated job object
     */
    public Job setFlowConditions(FlowConditions fcs) {
	    flowConditions = fcs;
	    return this;
    }

    /**
     * Returns the FlowConditions object currently associated with the Job.
     * @return FlowConditions object
     */
    public FlowConditions getFlowConditions(){
        return flowConditions;
    }

    /**
     * Adds a FlowCondition object to the existing Job FlowConditions.
     * Returns the updated job object so that methods can be chained.
     * @param fc FlowCondition to be added to the Job FlowConditions object.
     * @return the updated job object
     */
    public Job addFlowCondition(FlowCondition fc) {
	    flowConditions.addCondition(fc);
	    return this;
    }

    /**
     * Associates the specified Windows object to the Job.
     * Returns the updated job object so that methods can be chained.
     * @param ws Windows object to be associated with the Job
     * @return the updated Job object
     */
    public Job setWindows(Windows ws) {
	    windows = ws;
	    return this;
    }

    /**
     * Returns the Windows object currently associated with the Job.
     * @return Windows object
     */
    public Windows getWindows(){
        return windows;
    }

    /**
     * Adds a Window object to the existing Job Windows object.
     * Returns the updated job object so that methods can be chained.
     * @param w Window object to be added to the Job Windows
     * @return the updated Job object
     */
    public Job addWindow(Window w) {
	    windows.addWindow(w);
	    return this;
    }

    /**
     * Associates the specified Triggers object to the Job.
     * Returns the updated job object so that methods can be chained.
     * @param trs Triggers object to be associated with the Job object
     * @return the updated Job object
     */
    public Job setTriggers(Triggers trs) {
	    triggers = trs;
	    return this;
    }

    /**
     * Returns the Triggers object currently associated with the Job.
     * @return Triggers object associated with the Job
     */
    public Triggers getTriggers(){
        return triggers;
    }

    /**
     * Adds a Trigger object to the existing Job Triggers object.
     * Returns the updated job object so that methods can be chained.
     * @param t Trigger object to be added
     * @return the updated Job object
     */
    public Job addTrigger(Trigger t) {
	    triggers.addTrigger(t);
	    return this;
    }

    /**
     * Adds an object property for the given keyword to the metadata map
     * associated with a job. This is useful for associating job information
     * to a job before it is submitted for running. Returns the updated
     * job so that methods can be chained.
     * @param  k             The metadata property to be added
     * @param  v             The metadata property value
     * @return        updated job object.
     */
    public Job addMetadataProperty(String k, Object v) {
        metadata.addObjectParameter(k, v);
        return this;
    }

    /**
     * Returns the fully described Job as a PersistentArrayMap of key/value pairs,
     * where the key is the keyword associated with the Job component (Catalog, Windows, etc.)
     * and the values are themselves PersistentArrayMaps representing the components themselves.
     * @return PersistentArrayMap of PersistentArrayMaps representing job
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

        IPersistentMap m = metadata.toMap();
        job = (PersistentArrayMap) job.assoc(kwFn.invoke(OnyxMetadata), m);

    	return job;
    }
}
