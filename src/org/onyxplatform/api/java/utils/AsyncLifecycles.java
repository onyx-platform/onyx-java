package org.onyxplatform.api.java.utils;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import clojure.lang.PersistentArrayMap;

import org.onyxplatform.api.java.OnyxNames;

public class AsyncLifecycles implements OnyxNames {

	/**
 	* Loads the clojure namespaces.
 	*/
	static {
    		IFn require = Clojure.var(CORE, Require);
	}

}

