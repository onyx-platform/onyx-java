package org.onyxplatform.api.java;

import clojure.lang.PersistentHashMap;
import java.util.Map;

public class EnvConfiguration 
	extends OnyxEntity
{
	protected static String coerceKw = "env-config";

    	public EnvConfiguration () {
	    	super();
    	}
    
    	private EnvConfiguration(EnvConfiguration cfg) {
	    	super( cfg.entry );
    	}
	
    	protected PersistentHashMap coerce(Map<String, Object> jMap) {
		return (PersistentHashMap) super.castTypesFn.invoke( coerceKw, jMap);
    	}
}
