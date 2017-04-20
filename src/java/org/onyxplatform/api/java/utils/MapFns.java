package org.onyxplatform.api.java.utils;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import clojure.lang.IPersistentMap;

import org.onyxplatform.api.java.OnyxNames;
import org.onyxplatform.api.java.OnyxMap;

public class MapFns implements OnyxNames {

	protected final static IFn eMapFn;
	protected final static IFn ednFn;

	/**
 	* Loads the clojure namespaces.
 	*/
	static {
    		IFn requireFn = Clojure.var(CORE, Require);
		requireFn.invoke(Clojure.read(MAP_FNS));
		eMapFn = Clojure.var(MAP_FNS, ToOnyxMap);
		ednFn = Clojure.var(MAP_FNS, EdnFromRsrc);
	}

	public static OnyxMap toOnyxMap(IPersistentMap m) {
		return (OnyxMap) eMapFn.invoke(m);
	}

	public static OnyxMap fromResources(String rsrcPath) {
		return (OnyxMap) ednFn.invoke(rsrcPath);
	}


	
	// assoc 
	
	// dissoc
	
	// update

	// get-in

	// assoc-in

	
	// dissoc-in
	
	// update-in
	//
}

