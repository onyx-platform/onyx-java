package org.onyxplatform.api.java;

import clojure.lang.PersistentHashMap;
import java.util.Map;

public class EnvConfiguration {

    private final PersistentHashMap config;
    
    public EnvConfiguration () {
        config = PersistentHashMap.EMPTY;
    }
    
    private EnvConfiguration(PersistentHashMap cfg) {
        config = cfg;
    }
    
    public EnvConfiguration addParameter(String param, Object arg) {
        return new EnvConfiguration ((PersistentHashMap) config.assoc(param, arg));
    }
    
    public Map<String, Object> toMap() {
        return config;
    }
    
    @Override
    public String toString() {
        return config.toString();
    }
    
}
