package org.onyxplatform.api.java;

import clojure.lang.PersistentVector;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Triggers {
    
    public final PersistentVector triggerEntries;
    
    public Triggers() {
        triggerEntries = PersistentVector.EMPTY;
    }
    
    private Triggers(PersistentVector ts) {
        triggerEntries = ts;
    }
    
    public Triggers addTrigger(TriggerEntry te) {
        return new Triggers(triggerEntries.cons(te));
    }
    
    public List<Map<String, Object>> toList() {
        return new ArrayList(triggerEntries);
    }
    
    @Override
    public String toString() {
        return Arrays.toString(triggerEntries.toArray());
    }
    
}
