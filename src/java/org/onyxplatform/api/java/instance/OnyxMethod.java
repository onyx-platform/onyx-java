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

	public OnyxMethod(IPersistentMap m) {
		cntrArgs = m;
	}

	public OnyxMethod() {
		cntrArgs = PersistentHashMap.EMPTY;
	}

	public OnyxMap cntrArgs() {
		return MapFns.toOnyxMap(cntrArgs);
	}

	public abstract IPersistentMap procSegment(IPersistentMap m);

	public Object invoke(Object arg1) {
		IPersistentMap segment = (IPersistentMap)arg1;
		return procSegment(segment);
	}
}

