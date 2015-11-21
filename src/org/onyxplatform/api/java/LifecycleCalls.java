package org.onyxplatform.api.java;

import clojure.lang.PersistentHashMap;
import java.util.Map;

public class LifecycleCalls {

    private final PersistentHashMap entry;
    
    public LifecycleCalls() {
        entry = PersistentHashMap.EMPTY;
    }
    
    private LifecycleCalls(PersistentHashMap ent) {
        entry = ent;
    }
    
    public LifecycleCalls addParameter(String param, Object arg) {
        return new LifecycleCalls((PersistentHashMap) entry.assoc(param, arg));
    }
    
    public Map<String, Object> toMap() {
        return entry;
    }
    
    @Override
    public String toString() {
        return entry.toString();
    }
    
}
