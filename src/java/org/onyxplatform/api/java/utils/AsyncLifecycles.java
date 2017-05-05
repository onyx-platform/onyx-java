package org.onyxplatform.api.java.utils;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import clojure.lang.PersistentVector;
import clojure.lang.IPersistentMap;

import org.onyxplatform.api.java.OnyxNames;
import org.onyxplatform.api.java.OnyxMap;
import org.onyxplatform.api.java.OnyxVector;
import org.onyxplatform.api.java.Job;
import org.onyxplatform.api.java.Lifecycles;
import org.onyxplatform.api.java.Lifecycle;

import org.onyxplatform.api.java.utils.VectorFns;

/**
 * AsyncLifecycles is a static utility class designed to allow easy binding
 * of user inputs (vector input segments) to input tasks in asynchronous
 * channels using the Onyx Lifecycle concept and the core-async plugin.
 * AsyncLifecycles should be used by Tasks set up using the AsyncCatalog
 * utility so that Onyx knows what tasks need to take care of what inputs.
 * With an existing Job Lifecycles object lc, and an async task "in", this
 * utility would be used as AsyncLifecycles.addInput(lc, "in");
 * this then lets Onyx know that the "in" task should happen upon job setup.
 */
public class AsyncLifecycles implements OnyxNames {

	protected final static IFn kwFn;
	protected final static IFn inFn;
	protected final static IFn bindFn;
	protected final static IFn outFn;
	protected final static IFn collectFn;

	/**
 	* Loads the clojure namespaces.
 	*/
	static {
    	IFn requireFn = Clojure.var(CORE, Require);
		kwFn = Clojure.var(CORE, Keyword);
		requireFn.invoke(Clojure.read(ASYNC_LIFECYCLES));
		inFn = Clojure.var(ASYNC_LIFECYCLES, AsyncLifecycleIn);
		bindFn = Clojure.var(ASYNC_LIFECYCLES, BindLifecycleInputs);
		outFn = Clojure.var(ASYNC_LIFECYCLES, AsyncLifecycleOut);
		collectFn = Clojure.var(ASYNC_LIFECYCLES, CollectOutputs);
	}

	/**
	 * Adds a new lifecycle object with the given name to the
	 * specified lifecycles object. Returns the updated Lifecycles object.
	 * @param  lifecycles    Lifecycles object to be updated with new Lifecycle
	 * @param  name          Name of new AsyncLifecycle to create and add
	 * @return            Returns the updated Lifecycles object
	 */
	public static Lifecycles addInput(Lifecycles lifecycles, String name) {

		PersistentVector in = (PersistentVector) inFn.invoke(name);

		for (Object e : in) {
			IPersistentMap ie = (IPersistentMap)e;
			OnyxMap oe = MapFns.toOnyxMap(ie);
			Lifecycle l = new Lifecycle(oe);
			lifecycles.addLifecycle(l);
		}

		return lifecycles;
	}

	/**
	 * Binds input segments (a PersistentVector of inputs, can be filename strings, etc.)
	 * to the lifecycles. This ensures the async channels are loaded with the inputs.
	 * @param l      The lifecycles object which the inputs should be bound to
	 * @param inputs a PersistentVector of input segments to be bound
	 */
	public static void bindInputs(Lifecycles l, PersistentVector inputs) {

		PersistentVector cycles = l.cycles();
		OnyxMap om = new OnyxMap();
		om.addObjectParameter("in", inputs);
		bindFn.invoke(cycles, om.toMap());
	}

	/**
	 * Adds an async output lifecycle with the specified name to
	 * the specified Lifecycles object.
	 * The lifecycle name should be the name of an existing asynchronous task.
	 * @param  lifecycles    The lifecycles object to which the output lifecycle should be added
	 * @param  name          name of the output lifecycle object
	 * @return            Returns the updated Lifecycles object
	 */
	public static Lifecycles addOutput(Lifecycles lifecycles, String name) {

		PersistentVector out = (PersistentVector) outFn.invoke(name);
		for (Object e : out) {
			IPersistentMap ie = (IPersistentMap)e;
			OnyxMap oe = MapFns.toOnyxMap(ie);
			Lifecycle l = new Lifecycle(oe);
			lifecycles.addLifecycle(l);
		}

		return lifecycles;
	}


	/**
	 * This binds the output specified in the Lifecycle to a core.async reader
	 * plugin and collects outputs.
	 * NOTE: this and the other collectOutputs static function are
	 * broken into 2 pieces because Clojure doesn't handle variable arity functions
	 * across the langauge boundary.
	 * @param l the lifecycles object the outputs should be bound to
	 * @param outputName the name of the output lifecycle to collect outputs from
	 * @return an IPersistentMap of the collected outputs
	 */
	public static IPersistentMap collectOutputs(Lifecycles l, String outputName) {
		PersistentVector cycles = l.cycles();
		PersistentVector outputs = PersistentVector.EMPTY;
		Object k = kwFn.invoke(outputName);
		outputs = outputs.cons(k);
		return (IPersistentMap) collectFn.invoke(cycles, outputs);
	}

	/**
	 * This binds the output specified in the Lifecycle to a core.async reader
	 * plugin and collects outputs.
	 * NOTE: this and the other collectOutputs static function are
	 * broken into 2 pieces because Clojure doesn't handle variable arity functions
	 * across the langauge boundary.
	 * @param l the lifecycles object the outputs should be bound to
	 * @param outputName1 the name of the first output lifecycle to collect outputs from
	 * @param restOfTheNames the name(s) of the rest of the output lifecycles to collect outputs from
	 * @return an IPersistentMap of the collected outputs
	 */
	public static IPersistentMap collectOutputs(Lifecycles l, String outputName1, String... restOfTheNames) {
		PersistentVector cycles = l.cycles();
		PersistentVector outputs = VectorFns.keywordize(restOfTheNames);
		Object k = kwFn.invoke(outputName1);
		outputs = outputs.cons(k);
		return (IPersistentMap) collectFn.invoke(cycles, outputs);
	}

	/**
	 * Convenience function which mirrors the behavior of the other collectOutputs
	 * methods, except this version can be called directly on a Job. For the specified
	 * job, collects the outputs from the specified output lifecycle names.
	 * Returns an IPersistentMap of the outputs.
	 * @param  j            the job object for which to collect outputs
	 * @param  names        the name(s) of the lifecycles for which outputs are expected
	 * @return           an IPersistentMap of the collected outputs
	 */
	public static IPersistentMap collectOutputs(Job j, String... names) {
		PersistentVector cycles = j.getLifecycles().cycles();
		PersistentVector outputs = VectorFns.keywordize(names);
		return (IPersistentMap) collectFn.invoke(cycles, outputs);
	}
}
