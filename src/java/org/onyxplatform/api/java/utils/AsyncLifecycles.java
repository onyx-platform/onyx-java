
package org.onyxplatform.api.java.utils;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import clojure.lang.PersistentVector;
import clojure.lang.IPersistentMap;

import org.onyxplatform.api.java.OnyxNames;
import org.onyxplatform.api.java.OnyxMap;
import org.onyxplatform.api.java.Lifecycles;
import org.onyxplatform.api.java.Lifecycle;

public class AsyncLifecycles implements OnyxNames {

	protected final static IFn inFn;
	protected final static IFn outFn;

	/**
 	* Loads the clojure namespaces.
 	*/
	static {
    		IFn requireFn = Clojure.var(CORE, Require);
		requireFn.invoke(Clojure.read(ASYNC_LIFECYCLES));
		inFn = Clojure.var(ASYNC_CATALOG, AsyncLifecycleIn);
		outFn = Clojure.var(ASYNC_CATALOG, AsyncLifecycleOut);
	}

	public static void addInput(Lifecycles lifecycles, String name) {

		PersistentVector in = (PersistentVector) inFn.invoke(name);

		for (Object e : in) {
			IPersistentMap ie = (IPersistentMap)e;
			OnyxMap oe = MapFns.toOnyxMap(ie);
			Lifecycle l = new Lifecycle(oe);
			lifecycles.addLifecycle(l);
		}
	}

	public static void addOutput(Lifecycles lifecycles, String name) {

		PersistentVector out = (PersistentVector) outFn.invoke(name);

		for (Object e : out) {
			IPersistentMap ie = (IPersistentMap)e;
			OnyxMap oe = MapFns.toOnyxMap(ie);
			Lifecycle l = new Lifecycle(oe);
			lifecycles.addLifecycle(l);
		}
	}
}

