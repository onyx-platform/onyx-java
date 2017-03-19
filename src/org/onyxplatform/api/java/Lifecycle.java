package org.onyxplatform.api.java;

import clojure.lang.PersistentHashMap;
import java.util.Map;

public class Lifecycle
	extends OnyxEntity
{
	protected static String coerceKw = "catalog-entry";

    	public Lifecycle() {
	    	super();
    	}
    	
    	private Lifecycle(Lifecycle c) {
	    	super( c.entry );
    	}
	
	protected PersistentHashMap coerce(Map<String, Object> jMap) {
		return (PersistentHashMap) super.castTypesFn.invoke( coerceKw, jMap);
	}
}
