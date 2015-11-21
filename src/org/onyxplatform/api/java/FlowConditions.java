package org.onyxplatform.api.java;

import clojure.lang.PersistentVector;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class FlowConditions {
    
    public final PersistentVector entries;
    
    public FlowConditions() {
        entries = PersistentVector.EMPTY;
    }
    
    private FlowConditions(PersistentVector ents) {
        entries = ents;
    }
    
    public FlowConditions addCondition(FlowConditionEntry ent) {
        return new FlowConditions(entries.cons(ent));
    }
    
    public List<Map<String, Object>> toList() {
        return new ArrayList(entries);
    }
    
    @Override
    public String toString() {
        return Arrays.toString(entries.toArray());
    }
    
}
