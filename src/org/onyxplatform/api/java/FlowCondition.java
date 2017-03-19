package org.onyxplatform.api.java;

import clojure.lang.PersistentHashMap;
import java.util.Map;

public class FlowCondition
	extends OnyxEntity
{
	protected static String coerceKw = "flow-conditions-entry";
    
    	public FlowCondition() {
        	entry = PersistentHashMap.EMPTY;
    	}
    	
    	private FlowCondition(PersistentHashMap ent) {
        	entry = ent;
    	}
	
	protected PersistentHashMap coerce(Map<String, Object> jMap) {
		return (PersistentHashMap) super.castTypesFn.invoke( coerceKw, jMap);
	}
}
