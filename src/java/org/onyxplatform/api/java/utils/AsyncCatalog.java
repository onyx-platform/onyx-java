
package org.onyxplatform.api.java.utils;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import clojure.lang.PersistentArrayMap;
import clojure.lang.IPersistentMap;

import org.onyxplatform.api.java.OnyxNames;
import org.onyxplatform.api.java.OnyxMap;
import org.onyxplatform.api.java.Catalog;
import org.onyxplatform.api.java.Task;

/**
 * AsyncCatalog is a static utility class designed to modify an existing
 * Catalog object by easily adding asynchronous input and output tasks.
 * Methods in this class should operate on an existing Catalog object using
 * a pattern such as AsyncCatalog.addInput(ExistingCatalog, etc...)
 * This will automatically update the catalog object.
 */
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

	/**
	 * Adds an asynchronous input task to the specified catalog. This method
	 * requires a batchSize and batchTimeout, which should usually be uniform
	 * throughout tasks in a catalog, as well as a name for the catalog task.
	 * Updating of the catalog is handled automatically.
	 * @param  catalog       The catalog object to be modified
	 * @param  name          Name of the input task
	 * @param  batchSize     batch size for the input task/async channel
	 * @param  batchTimeout  batch timeout for the input task/async channel
	 * @return         Returns the updated catalog object with the new input task.
	 */
	public static Catalog addInput(Catalog catalog, String name, int batchSize, int batchTimeout) {

		IPersistentMap inCat = (IPersistentMap) inFn.invoke(name, batchSize, batchTimeout);
		OnyxMap e = MapFns.toOnyxMap(inCat);
		Task inTask = new Task(e);
		return catalog.addTask(inTask);
	}

	/**
	 * Adds an asynchronous input task to the specified catalog. This method
	 * requires a batchSize and batchTimeout, which should usually be uniform
	 * throughout tasks in a catalog, as well as a name for the catalog task.
	 * Updating of the catalog is handled automatically.
	 * @param  catalog       The catalog object to be modified
	 * @param  name          Name of the output task
	 * @param  batchSize     batch size for the output task/async channel
	 * @param  batchTimeout  batch timeout for the output task/async channel
	 * @return         Returns the updated catalog object with the new output task.
	 */
	public static Catalog addOutput(Catalog catalog, String name, int batchSize, int batchTimeout) {

		IPersistentMap outCat = (IPersistentMap) outFn.invoke(name, batchSize, batchTimeout);
		OnyxMap e = MapFns.toOnyxMap(outCat);
		Task outTask = new Task(e);
		return catalog.addTask(outTask);
	}
}
