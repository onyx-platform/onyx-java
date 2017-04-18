package org.onyxplatform.api.java.utils;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import clojure.lang.IPersistentMap;

import org.onyxplatform.api.java.OnyxNames;
import org.onyxplatform.api.java.OnyxEntity;

public class MapFns implements OnyxNames {

	protected final static IFn eMapFn;

	/**
 	* Loads the clojure namespaces.
 	*/
	static {
    		IFn requireFn = Clojure.var(CORE, Require);
		requireFn.invoke(Clojure.read(MAP_FNS));
		eMapFn = Clojure.var(MAP_FNS, ToEntityMap);
	}

	public static OnyxEntity toEntityMap(IPersistentMap m) {
		return (OnyxEntity) eMapFn.invoke(m);
	}

	
	// assoc 
	
	// dissoc
	
	// update


	// assoc-in
	
	// dissoc-in
	
	// update-in
	//
}

