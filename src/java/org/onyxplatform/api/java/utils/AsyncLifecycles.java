package org.onyxplatform.api.java.utils;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import clojure.lang.PersistentVector;
import clojure.lang.IPersistentMap;

import org.onyxplatform.api.java.OnyxNames;
import org.onyxplatform.api.java.OnyxMap;
import org.onyxplatform.api.java.OnyxVector;
import org.onyxplatform.api.java.Lifecycles;
import org.onyxplatform.api.java.Lifecycle;

import org.onyxplatform.api.java.utils.VectorFns;

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

	public static void bindInputs(Lifecycles l, PersistentVector inputs) {

		PersistentVector cycles = l.cycles();

		// bind-inputs! expects a map with the vector of input segments
		// with the key 'in'
		//
		OnyxMap om = new OnyxMap();
		om.addObjectParameter("in", inputs);
		bindFn.invoke(cycles, om.toMap());
	}

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
	 * plugin and collects outputs. NOTE: this and the below static function are
	 * broken into 2 pieces because Clojure doesn't handle variable arity functions
	 * across the langauge boundry.
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
	 * plugin and collects outputs. NOTE: this and the above static function are
	 * broken into 2 pieces because Clojure doesn't handle variable arity functions
	 * across the langauge boundry.
	 */
	public static IPersistentMap collectOutputs(Lifecycles l, String outputName1, String... restOfTheNames) {
		PersistentVector cycles = l.cycles();
		PersistentVector outputs = VectorFns.keywordize(restOfTheNames);
		Object k = kwFn.invoke(outputName1);
		outputs = outputs.cons(k);
		return (IPersistentMap) collectFn.invoke(cycles, outputs);
	}
}

