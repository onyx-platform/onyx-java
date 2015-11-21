package org.onyxplatform.api.java;

import clojure.lang.PersistentHashMap;
import java.util.Map;

public class WindowEntry {
    
    public final PersistentHashMap entry;
    
    public WindowEntry() {
        entry = PersistentHashMap.EMPTY;
    }
    
    private WindowEntry(PersistentHashMap ent) {
        entry = ent;
    }
    
    public WindowEntry addParameter(String param, Object arg) {
        return new WindowEntry((PersistentHashMap) entry.assoc(param, arg));
    }
    
    public Map<String, Object> toMap() {
        return entry;
    }
    
    @Override
    public String toString() {
        return entry.toString();
    }
    
}
