package org.onyxplatform.api.java.instance;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import clojure.lang.AFn;
import clojure.lang.IPersistentMap;
import clojure.lang.PersistentHashMap;

import org.onyxplatform.api.java.OnyxNames;
import org.onyxplatform.api.java.OnyxMap;
import org.onyxplatform.api.java.utils.MapFns;

public abstract class OnyxMethod extends AFn implements OnyxNames {

	protected IPersistentMap cntrArgs;

	/**
	 * This MUST be overridden by the concrete subclass. 
	 * It is called during the first method invocation.
	 */
	public OnyxMethod(IPersistentMap m) {
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

