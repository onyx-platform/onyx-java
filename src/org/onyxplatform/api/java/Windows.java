package org.onyxplatform.api.java;

import clojure.lang.PersistentVector;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Windows {
    
    public final PersistentVector windowEntries;
    
    public Windows() {
        windowEntries = PersistentVector.EMPTY;
    }
    
    private Windows(PersistentVector wes) {
        windowEntries = wes;
    }
    
    public Windows addWindow(WindowEntry we) {
        return new Windows(windowEntries.cons(we));
    }
    
    public List<Map<String, Object>> toList() {
        return new ArrayList(windowEntries);
    }
    
    @Override
    public String toString() {
        return Arrays.toString(windowEntries.toArray());
    }
    
}
