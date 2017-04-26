package onyxplatform.test;

import clojure.lang.IPersistentMap;

import org.onyxplatform.api.java.instance.OnyxMethod;

public class PassMethod extends OnyxMethod {

	public PassMethod(IPersistentMap m) {
		super(m);
	}

	public Object consumeSegment(IPersistentMap m) {
		return m;
	}
}

