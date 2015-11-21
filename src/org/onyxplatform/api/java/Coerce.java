package org.onyxplatform.api.java;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import clojure.lang.PersistentVector;

class Coerce {
    
    private final static IFn kw;
    
    static {
        IFn require = Clojure.var("clojure.core", "require");
        require.invoke(Clojure.read("onyx.interop"));
        
        kw = Clojure.var("clojure.core", "keyword");
    }
    
    public static Object coerceWorkflow(Workflow wf) {
        IFn coerceWorkflow = Clojure.var("onyx.interop", "coerce-workflow");
        return coerceWorkflow.invoke(wf.workflow);
    }
    
    public static Object coerceCatalog(Catalog cat) {
        IFn coerceCatalog = Clojure.var("onyx.interop", "coerce-catalog");
        PersistentVector tasks = PersistentVector.EMPTY;
        
        for (Object entry : cat.tasks) {
            tasks = tasks.cons(((Task) entry).toMap());
        }
        
        return coerceCatalog.invoke(tasks);
    }
    
    public static Object coerceLifecycles(Lifecycles lifecycle) {
        IFn coerceLifecycles = Clojure.var("onyx.interop", "coerce-lifecycles");
        PersistentVector entries = PersistentVector.EMPTY;
        
        for (Object entry : lifecycle.calls) {
            entries = entries.cons(((LifecycleCalls) entry).toMap());
        }
        
        return coerceLifecycles.invoke(entries);
    }
    
    public static Object coerceFlowConditions(FlowConditions fcs) {
        IFn coerceFlowConditions = Clojure.var("onyx.interop", "coerce-flow-conditions");
        PersistentVector entries = PersistentVector.EMPTY;
        
        for (Object entry : fcs.entries) {
            entries = entries.cons(((FlowConditionEntry) entry).toMap());
        }
        
        return coerceFlowConditions.invoke(entries);
    }
    
    public static Object coerceWindows(Windows windows) {
        IFn coerceWindows = Clojure.var("onyx.interop", "coerce-windows");
        PersistentVector entries = PersistentVector.EMPTY;
        
        for (Object entry : windows.windowEntries) {
            entries = entries.cons(((WindowEntry) entry).toMap());
        }
        
        return coerceWindows.invoke(entries);
    }
    
    public static Object coerceTriggers(Triggers triggers) {
        IFn coerceTriggers = Clojure.var("onyx.interop", "coerce-triggers");
        PersistentVector entries = PersistentVector.EMPTY;
        
        for (Object entry : triggers.triggerEntries) {
            entries = entries.cons(((TriggerEntry) entry).toMap());
        }
        
        return coerceTriggers.invoke(entries);
    }
    
    public static Object coerceTaskScheduler(TaskScheduler ts) {
        return kw.invoke(ts.toString());
    }
    
}
