package org.onyxplatform.api.java;

import clojure.lang.PersistentVector;
import java.util.Arrays;

public final class Workflow {
    
    public final PersistentVector workflow;
     
    public Workflow() {
        workflow = PersistentVector.EMPTY;
    }
    
    private Workflow(PersistentVector wf) {
        workflow = wf;
    }
    
    public Workflow addEdge(String srcTask, String dstTask) {
        PersistentVector pair = PersistentVector.create(srcTask, dstTask);
        return new Workflow(workflow.cons(pair));
    }
    
    public Object[] graph() {
        return workflow.toArray();
    }
    
    @Override
    public String toString() {
        return Arrays.toString(this.graph());
    }
    
}
