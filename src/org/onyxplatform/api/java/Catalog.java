package org.onyxplatform.api.java;

import clojure.lang.PersistentVector;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Catalog {
    
    public final PersistentVector tasks;
    
    public Catalog() {
        tasks = PersistentVector.EMPTY;
    }
    
    private Catalog(PersistentVector ts) {
        tasks = ts;
    }
    
    public Catalog addTask(Task task) {
        return new Catalog(tasks.cons(task));
    }
    
    public List<Map<String, Object>> toList() {
        return new ArrayList(tasks);
    }
    
    @Override
    public String toString() {
        return Arrays.toString(tasks.toArray());
    }
    
}
