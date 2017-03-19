package org.onyxplatform.api.java;

import clojure.lang.PersistentHashMap;
import java.util.Map;

public class Trigger
	extends OnyxEntity
{
	protected static String coerceKw = "trigger-entry";

    	public Trigger() {
	    	super();
    	}
    	
    	private Trigger(Trigger e) {
	    	super( e.entry );
    	}
		
    	protected PersistentHashMap coerce(Map<String, Object> jMap) {
		return (PersistentHashMap) super.castTypesFn.invoke( coerceKw, jMap);
    	}
}
