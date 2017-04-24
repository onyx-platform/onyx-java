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

	protected final static IFn inFn;
	protected final static IFn bindFn;
	protected final static IFn outFn;
	protected final static IFn collectFn;

	/**
 	* Loads the clojure namespaces.
 	*/
	static {
    		IFn requireFn = Clojure.var(CORE, Require);
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

	// java.lang.ClassCastException: java.lang.String cannot be cast to [Ljava.lang.String;
	//
	// This looks like it hates there only being 1 string passed although
	// I've declared it as a variable argument list. TODO: Debug this in Java
	// first, may need to have 2 fn declarations? 1 single, 1 variable?
	//
	public static PersistentVector collectOutputs(Lifecycles l, String... outputNames) {
		PersistentVector cycles = l.cycles();
		System.out.println("collectOutputs> outputNames=" + outputNames);
		PersistentVector outputs = VectorFns.keywordize(outputNames);
		return (PersistentVector) collectFn.invoke(cycles, outputs);
	}
}

