package org.onyxplatform.api.java;

import clojure.lang.PersistentHashMap;
import java.util.Map;

public class PeerConfiguration {

    private final PersistentHashMap config;
    
    public PeerConfiguration () {
        config = PersistentHashMap.EMPTY;
    }
    
    private PeerConfiguration(PersistentHashMap cfg) {
        config = cfg;
    }
    
    public PeerConfiguration addParameter(String param, Object arg) {
        return new PeerConfiguration ((PersistentHashMap) config.assoc(param, arg));
    }
    
    public Map<String, Object> toMap() {
        return config;
    }
    
    @Override
    public String toString() {
        return config.toString();
    }
    
}
