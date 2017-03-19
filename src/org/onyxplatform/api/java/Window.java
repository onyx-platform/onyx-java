package org.onyxplatform.api.java;

import clojure.lang.PersistentHashMap;
import java.util.Map;

public class Window
	extends OnyxEntity
{
	protected static String coerceKw = "window-entry";

    	public Window() {
	    	super();
    	}
    	
    	private Window(Window e) {
	    	super( e.entry );
    	}
		
    	protected PersistentHashMap coerce(Map<String, Object> jMap) {
		return (PersistentHashMap) super.castTypesFn.invoke( coerceKw, jMap);
    	}
}
