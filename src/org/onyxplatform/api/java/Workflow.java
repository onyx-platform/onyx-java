package org.onyxplatform.api.java;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import clojure.lang.PersistentVector;
import clojure.lang.PersistentHashMap;
import java.util.Map;

public final class Workflow 
	implements OnyxNames
{
    static {

	IFn requireFn = Clojure.var(CORE, Require);
	requireFn.invoke(Clojure.read(INTEROP));
    }

	protected PersistentVector edges;

    	public Workflow() {
		edges = PersistentVector.EMPTY;
    	}
    
    	private Workflow(Workflow wf) {
		edges = wf.edges;
    	}
    	
    	public void addEdge(String srcTask, String dstTask) {
        	PersistentVector pair = PersistentVector.create(srcTask, dstTask);
		edges = edges.cons( pair );
    	}

	public Object[] graph() {
	       return edges.toArray();
	}	       

	public PersistentVector cljGraph() {
        	IFn coerceWorkflow = Clojure.var("onyx.interop", "coerce-workflow");
        	return (PersistentVector) coerceWorkflow.invoke(edges);
	}
}
