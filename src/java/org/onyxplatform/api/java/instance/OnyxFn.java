package org.onyxplatform.api.java.instance;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import clojure.lang.AFn;
import clojure.lang.IPersistentMap;
import clojure.lang.PersistentHashMap;

import org.onyxplatform.api.java.OnyxNames;
import org.onyxplatform.api.java.OnyxMap;
import org.onyxplatform.api.java.utils.MapFns;

public abstract class OnyxFn extends AFn implements OnyxNames {

	// Class -------------------------------------------
	//
	// The ClassLoader member is added by the Loader
	// with affordance for null-ing it out when this object
	// is removed. This ensures that when all instances 
	// of the class are no longer referenced the class 
	// itself will also be garbage collected. 
	//
	// This matters for native code-backed class loading.
	// Java has no affordances for unloading libraries
	// pulled in by classes because the library is class-level bound
	// and the class itself is never gc'ed if loaded by 
	// the default class loader which is always reachable.
	//

	protected static ClassLoader classLoader = null;

	public static void setClassLoader(ClassLoader cl) {
		classLoader = cl;
	}

	public void releaseClassLoader() {
		// TODO: Release all class references in the class loader
		classLoader = null;
	}

	public static Class<?> findClass(String fqClassName) 
		throws ClassNotFoundException
	{
		return Loader.findClass(classLoader, fqClassName);
	}


	// Instance --------------------------------------
	// 

	protected IPersistentMap cntrArgs;

	/**
	 * This MUST be overridden by the concrete subclass. 
	 * It is called during the first method invocation.
	 */
	public OnyxFn(IPersistentMap m) {
		cntrArgs = m;
	}

	public OnyxMap cntrArgs() {
		return MapFns.toOnyxMap(cntrArgs);
	}

	/**
	 * Allowed return types:
	 *
	 *  1. IPersistentMap
	 *  2. PersistentVector containing IPersistentMap's
	 */
	public abstract Object consumeSegment(IPersistentMap m);

	public Object invoke(Object arg1) {
		IPersistentMap segment = (IPersistentMap)arg1;
		// TODO: check return type. 
		return consumeSegment(segment);
	}
}

