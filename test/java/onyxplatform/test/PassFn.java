package onyxplatform.test;

import clojure.lang.IPersistentMap;

import org.onyxplatform.api.java.instance.OnyxFn;

public class PassFn extends OnyxFn {

	public PassFn(IPersistentMap m) {
		super(m);
	}

	public Object consumeSegment(IPersistentMap m) {
		return m;
	}
}

