package org.onyxplatform.api.java.instance;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.Thread;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import clojure.lang.IPersistentMap;
import clojure.lang.PersistentVector;

import org.onyxplatform.api.java.OnyxNames;
import org.onyxplatform.api.java.OnyxMap;
import org.onyxplatform.api.java.Catalog;
import org.onyxplatform.api.java.Task;
import org.onyxplatform.api.java.Job;

import org.onyxplatform.api.java.utils.MapFns;

/**
 * BindUtils is a static utility class designed to work with User Classes
 * which extend the OnyxFn abstract class.
 * This utility provides a method which can add an object instance that is
 * derived from a user class to a job catalog.
 * It also provides methods related to memory management of these catalog objects,
 * allowing users to manually unload the instances when they are no longer used
 * by the job.
 */
public class BindUtils implements OnyxNames {

	protected static IFn instcatFn;
	protected static IFn releaseFn;
	protected static IFn releaseAllFn;

	/**
 	* Loads the clojure namespaces.
 	*/
	static {
    		IFn requireFn = Clojure.var(CORE, Require);

		requireFn.invoke(Clojure.read(INSTANCE_CATALOG));
		instcatFn = Clojure.var(INSTANCE_CATALOG, CreateMethod);

		requireFn.invoke(Clojure.read(INSTANCE_BIND));
		releaseFn = Clojure.var(INSTANCE_BIND, ReleaseInst);
		releaseAllFn = Clojure.var(INSTANCE_BIND, ReleaseAllInst);
	}

	/**
	 * Creates and adds an object instance to an existing Catalog object.
	 * The object instance is derived from a user class which extends
	 * the OnyxFn abstract class. To use this method, a name for the object
	 * must be provided, along with the fully qualified user base class,
	 * a map of arguments to use as constructor args for the class,
	 * and the environment parameters batchSize and batchTimeout.
	 * @param  catalog       the catalog object to which the new object instance will be added as a task
	 * @param  taskName      a string to use as a name for the object instance task in the Catalog
	 * @param  batchSize     an integer describing the number of segments that will be read at a time
	 * @param  batchTimeout  an integer describing the longest amount of time (ms) that a task will wait before reading segments
	 * @param  fqClassName   a string naming the fully qualified user class to use in object instance creation
	 * @param  ctrArgs       an IPersistentMap containing arguments to use in the user class constructor
	 * @return                returns the updated catalog which includes the added task
	 */
	public static Catalog addFn(Catalog catalog, String taskName,
				    int batchSize, int batchTimeout,
				    String fqClassName, IPersistentMap ctrArgs) {
		IPersistentMap methodCat = (IPersistentMap) instcatFn.invoke(taskName,
								             batchSize, batchTimeout,
							  	             fqClassName, ctrArgs);
		OnyxMap e = MapFns.toOnyxMap(methodCat);
		Task methodTask = new Task(e);
		return catalog.addTask(methodTask);
	}

	/**
	 * Returns an IFn representation of a dynamically loaded object instance derived
	 * from a user class extending OnyxFn.
	 * @param  fqClassName   The fully qualified classname of the class from which to derive an object instance
	 * @param  args          An IPersistentMap of constructor args to use in object instance creation
	 * @return                                             IFn representation of the object instance
	 * @throws ClassNotFoundException                      Class cannot be found
	 * @throws NoSuchMethodException                       Class doesnt have a proper constructor
	 * @throws InstantiationException                      Object cannot be instantiated do to any instantiation error
	 * @throws IllegalAccessException                      method or class definition was unavailable
	 * @throws java.lang.reflect.InvocationTargetException an abstracted error in the method call, unpack to see actual cause
	 */
	public static IFn loadOnyxFn(Loader loader, String fqClassName, IPersistentMap args)
		throws ClassNotFoundException,
		NoSuchMethodException,
		InstantiationException,
		IllegalAccessException,
		java.lang.reflect.InvocationTargetException
	{
		Class<?> ifnClazz = loader.loadClass(fqClassName);
		System.out.println("class " + ifnClazz);

	        Class<?> ipmClazz = loader.loadClass("clojure.lang.IPersistentMap");
		Constructor ctr = ifnClazz.getConstructor(new Class[] { ipmClazz });
		return (IFn) ctr.newInstance(new Object[] { args });
	}

	/**
	 * Given a task, releases the object instance associated with the task,
	 * freeing it up for garbage collection by the JVM.
	 * @param task task associated with the object instance to release for gc
	 */
	public static void releaseInstance(Task task) {
		IPersistentMap e = task.toMap();
		releaseFn.invoke(e);
		System.gc();
	}

	/**
	 * Given a job, releases all object instances for all tasks in the job
	 * catalog which are associated with a user object instance.
	 * @param j the job containing a catalog of tasks to release for gc
	 */
	public static void releaseInstances(Job j) {
		PersistentVector at = j.getCatalog().tasks();
		releaseAllFn.invoke(at);
		System.gc();
	}
}
