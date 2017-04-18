package org.onyxplatform.api.java.utils;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import clojure.lang.PersistentArrayMap;
import clojure.lang.IPersistentMap;

import org.onyxplatform.api.java.OnyxNames;
import org.onyxplatform.api.java.OnyxEntity;

public class MapFns implements OnyxNames {

	/**
 	* Loads the clojure namespaces.
 	*/
	static {
    		IFn require = Clojure.var(CORE, Require);
	}

	public static OnyxEntity toEntityMap(IPersistentMap m) {
		return null;
	}

	
	// assoc 
	
	// dissoc
	
	// update


	// assoc-in
	
	// dissoc-in
	
	// update-in
	//
}

