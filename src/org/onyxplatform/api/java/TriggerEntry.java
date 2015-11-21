package org.onyxplatform.api.java;

import clojure.lang.PersistentHashMap;
import java.util.Map;

public class TriggerEntry {
    
    public final PersistentHashMap entry;
    
    public TriggerEntry() {
        entry = PersistentHashMap.EMPTY;
    }
    
    private TriggerEntry(PersistentHashMap ent) {
        entry = ent;
    }
    
    public TriggerEntry addParameter(String param, Object arg) {
        return new TriggerEntry((PersistentHashMap) entry.assoc(param, arg));
    }
    
    public Map<String, Object> toMap() {
        return entry;
    }
    
    @Override
    public String toString() {
        return entry.toString();
    }
    
}
