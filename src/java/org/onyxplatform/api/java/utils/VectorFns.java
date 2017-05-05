package org.onyxplatform.api.java.utils;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import clojure.lang.PersistentVector;

import org.onyxplatform.api.java.OnyxNames;

/**
 * VectorFns is a static utility class used to operate on a common Onyx
 * datatype, the PersistentVector.
 */
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

	/**
	 * Turns all strings in the given array into clojure.lang.keyword types.
	 * Returns the keywords in a PersistentVector
	 * @param  array         array of strings to convert
	 * @return          a PersistentVector of keywords converted from an array of strings
	 */
	public static PersistentVector keywordize(String[] array) {
		return (PersistentVector) kwdzFn.invoke(array);
	}
}
