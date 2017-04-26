package org.onyxplatform.api.java.instance;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import clojure.lang.AFn;
import clojure.lang.IPersistentMap;

import org.onyxplatform.api.java.OnyxNames;
import org.onyxplatform.api.java.OnyxMap;
import org.onyxplatform.api.java.utils.MapFns;

public class PassMethod extends OnyxMethod {

	public PassMethod(IPersistentMap m) {
		super(m);
	}

	public Object consumeSegment(IPersistentMap m) {
		return m;
	}
}

