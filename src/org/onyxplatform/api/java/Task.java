package org.onyxplatform.api.java;

import clojure.lang.PersistentHashMap;
import java.util.Map;


public class Task
	extends OnyxEntity
{
	protected static String coerceKw = "catalog-entry";

    	public Task() {
			super();
    	}

    	private Task(PersistentHashMap ent) {
	    	super(ent);
    	}

    	protected PersistentHashMap coerce(Map<String, Object> jMap) {
		return (PersistentHashMap) super.castTypesFn.invoke( coerceKw, jMap);
    	}
}
