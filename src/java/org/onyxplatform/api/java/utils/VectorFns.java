package org.onyxplatform.api.java.utils;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import clojure.lang.PersistentVector;

import org.onyxplatform.api.java.OnyxNames;

public class VectorFns implements OnyxNames {

	protected final static IFn kwdzFn;

	/**
 	* Loads the clojure namespaces.
 	*/
	static {
    		IFn requireFn = Clojure.var(CORE, Require);
		requireFn.invoke(Clojure.read(VECTOR_FNS));
		kwdzFn = Clojure.var(VECTOR_FNS, KeywordizeStrArray);
	}

	public static PersistentVector keywordize(String[] a) {
		return (PersistentVector) kwdzFn.invoke(array);
	}
}

