package org.onyxplatform.api.java;

import clojure.lang.PersistentHashMap;
import java.util.Map;

public class Task {
    
    public final PersistentHashMap entry;
    
    public Task() {
        entry = PersistentHashMap.EMPTY;
    }
    
    private Task(PersistentHashMap ent) {
        entry = ent;
    }
    
    public Task addParameter(String param, Object arg) {
        return new Task((PersistentHashMap) entry.assoc(param, arg));
    }
    
    public Map<String, Object> toMap() {
        return entry;
    }
    
    @Override
    public String toString() {
        return entry.toString();
    }
    
}
