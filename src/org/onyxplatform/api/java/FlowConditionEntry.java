package org.onyxplatform.api.java;

import clojure.lang.PersistentHashMap;
import java.util.Map;

public class FlowConditionEntry {
    
    public final PersistentHashMap entry;
    
    public FlowConditionEntry() {
        entry = PersistentHashMap.EMPTY;
    }
    
    private FlowConditionEntry(PersistentHashMap ent) {
        entry = ent;
    }
    
    public FlowConditionEntry addParameter(String param, Object arg) {
        return new FlowConditionEntry((PersistentHashMap) entry.assoc(param, arg));
    }
    
    public Map<String, Object> toMap() {
        return entry;
    }
    
    @Override
    public String toString() {
        return entry.toString();
    }
    
}
