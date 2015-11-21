package org.onyxplatform.api.java;

import clojure.lang.PersistentVector;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Lifecycles {
    
    public final PersistentVector calls;
    
    public Lifecycles() {
        calls = PersistentVector.EMPTY;
    }
    
    private Lifecycles(PersistentVector cs) {
        calls = cs;
    }
    
    public Lifecycles addLifecycleCalls(LifecycleCalls cs) {
        return new Lifecycles(calls.cons(cs));
    }
    
    public List<Map<String, Object>> toList() {
        return new ArrayList(calls);
    }
    
    @Override
    public String toString() {
        return Arrays.toString(calls.toArray());
    }
    
}
