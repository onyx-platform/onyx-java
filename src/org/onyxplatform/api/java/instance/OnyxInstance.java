package org.onyxplatform.api.java.utils;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import clojure.lang.PersistentArrayMap;

import org.onyxplatform.api.java.OnyxNames;

// TODO: Create an abstract class that takes a 
//
//       HashMap as a constructor
//
//       Proxy static fns to make map manipulation
//       easier
//
public class OnyxInstance implements OnyxNames {

	/**
 	* Loads the clojure namespaces.
 	*/
	static {
    		IFn require = Clojure.var(CORE, Require);
	}



}


