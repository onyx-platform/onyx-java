package org.onyxplatform.api.java.utils;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import clojure.lang.IPersistentMap;
import clojure.lang.PersistentVector;


import org.onyxplatform.api.java.OnyxNames;
import org.onyxplatform.api.java.OnyxMap;

import org.onyxplatform.api.java.utils.VectorFns;

public class MapFns implements OnyxNames {

	protected final static IFn eMapFn;
	protected final static IFn ednFn;

	protected final static IFn kwFn;
	protected final static IFn rawGetFn;
	protected final static IFn rawAssocFn;
	protected final static IFn rawDissocFn;
	protected final static IFn rawGetInFn;
	protected final static IFn rawAssocInFn;
	protected final static IFn rawMergeFn;

	/**
 	* Loads the clojure namespaces.
 	*/
	static {
    		IFn requireFn = Clojure.var(CORE, Require);
		kwFn = Clojure.var(CORE, Keyword);
		requireFn.invoke(Clojure.read(MAP_FNS));
		eMapFn = Clojure.var(MAP_FNS, ToOnyxMap);
		ednFn = Clojure.var(MAP_FNS, EdnFromRsrc);

		rawGetFn = Clojure.var(CORE, Get);
		rawAssocFn = Clojure.var(CORE, Assoc);
		rawDissocFn = Clojure.var(CORE, Dissoc);
		rawGetInFn = Clojure.var(CORE, GetIn);
		rawAssocInFn = Clojure.var(CORE, AssocIn);
		rawMergeFn = Clojure.var(CORE, Merge);
	}

	public static OnyxMap toOnyxMap(IPersistentMap m) {
		return (OnyxMap) eMapFn.invoke(m);
	}

	public static OnyxMap fromResources(String rsrcPath) {
		return (OnyxMap) ednFn.invoke(rsrcPath);
	}

	public static Object get(IPersistentMap m, String key) {
		Object k = kwFn.invoke(key);
		return rawGetFn.invoke(m, k);
	}

	public static IPersistentMap assoc(IPersistentMap m, String key, Object value) {
		Object k = kwFn.invoke(key);
		return (IPersistentMap)rawAssocFn.invoke(m, k, value);
	}

	public static IPersistentMap dissoc(IPersistentMap m, String key) {
		Object k = kwFn.invoke(key);
		return (IPersistentMap)rawDissocFn.invoke(m, k);
	}

	public static IPersistentMap getIn(IPersistentMap m, String... keys) {
		PersistentVector ks = VectorFns.keywordize(keys);
		return (IPersistentMap)rawGetInFn.invoke(m, ks);
	}

	public static IPersistentMap assocIn(IPersistentMap m, Object value, String... keys) {
		PersistentVector ks = VectorFns.keywordize(keys);
		return (IPersistentMap)rawAssocInFn.invoke(m, ks);
	}

	public static IPersistentMap merge(IPersistentMap m, IPersistentMap m2) {
		return (IPersistentMap)rawMergeFn.invoke(m, m2);
	}
}

