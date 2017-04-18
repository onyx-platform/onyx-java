
package org.onyxplatform.api.java.utils;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import clojure.lang.PersistentArrayMap;
import clojure.lang.IPersistentMap;

import org.onyxplatform.api.java.OnyxNames;
import org.onyxplatform.api.java.OnyxEntity;
import org.onyxplatform.api.java.Catalog;
import org.onyxplatform.api.java.Task;

public class AsyncCatalog implements OnyxNames {

	protected final static IFn inFn;
	protected final static IFn outFn;

	/**
 	* Loads the clojure namespaces.
 	*/
	static {
    		IFn requireFn = Clojure.var(CORE, Require);
		requireFn.invoke(Clojure.read(ASYNC_CATALOG));
		inFn = Clojure.var(ASYNC_CATALOG, AsyncCatalogIn);
		outFn = Clojure.var(ASYNC_CATALOG, AsyncCatalogOut);
	}

	public static void addInput(Catalog catalog, String name, int batchSize, int batchTimeout) {

		IPersistentMap inCat = (IPersistentMap) inFn.invoke(name, batchSize, batchTimeout);
		OnyxEntity e = MapFns.toEntityMap(inCat);
		Task inTask = new Task(e);
		catalog.addTask(inTask);
	}

	public static void addOutput(Catalog catalog, String name, int batchSize, int batchTimeout) {

		IPersistentMap outCat = (IPersistentMap) inFn.invoke(name, batchSize, batchTimeout);
		OnyxEntity e = MapFns.toEntityMap(outCat);
		Task outTask = new Task(e);
		catalog.addTask(outTask);
	}

}

