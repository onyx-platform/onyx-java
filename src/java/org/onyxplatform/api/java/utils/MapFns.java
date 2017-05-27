package org.onyxplatform.api.java.utils;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import clojure.lang.IPersistentMap;
import clojure.lang.PersistentVector;


import org.onyxplatform.api.java.OnyxNames;
import org.onyxplatform.api.java.OnyxMap;

import org.onyxplatform.api.java.utils.VectorFns;

/**
 * MapFns is a static utility class used to operate on the main data type
 * used by Onyx, the IPersistentMap. It is useful for translating between
 * Java type maps, loading IPersistentMaps from resource files, and manipulating
 * the maps using functions like get-in(getting a nested value from a nested key),
 * assoc (associating a new entry to the map), etc.
 */
public class MapFns implements OnyxNames {

	protected final static IFn emptyFn;
	protected final static IFn isEmptyFn;
	protected final static IFn containsFn;
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
		emptyFn = Clojure.var(MAP_FNS, EmptyMap);
		isEmptyFn = Clojure.var(MAP_FNS, IsEmptyMap);
		containsFn = Clojure.var(MAP_FNS, Contains);
		eMapFn = Clojure.var(MAP_FNS, ToOnyxMap);
		ednFn = Clojure.var(MAP_FNS, EdnFromRsrc);

		rawGetFn = Clojure.var(CORE, Get);
		rawAssocFn = Clojure.var(CORE, Assoc);
		rawDissocFn = Clojure.var(CORE, Dissoc);
		rawGetInFn = Clojure.var(CORE, GetIn);
		rawAssocInFn = Clojure.var(CORE, AssocIn);
		rawMergeFn = Clojure.var(CORE, Merge);
	}

	/**
	 * Create a new empty IPersistentMap.
	 * @return new empty IPersistentMap
	 */
	public static IPersistentMap emptyMap() {
		return (IPersistentMap) emptyFn.invoke();
	}

	public static boolean isEmpty(IPersistentMap m) {
		return (boolean) isEmptyFn.invoke(m);
	}

	public static boolean isEmpty(OnyxMap m) {
		return isEmpty(m.toMap());
	}

	public static boolean contains(IPersistentMap m, String key) {
		Object k = kwFn.invoke(key);
		return (boolean) containsFn.invoke(m, k);
	}

	public static boolean contains(OnyxMap m, String key) {
		return contains(m.toMap(), key);
	}


	/**
	 * Converts an IPersistentMap to an OnyxMap object.
	 * @param  m             the IPersistentMap to convert
	 * @return               new OnyxMap object
	 */
	public static OnyxMap toOnyxMap(IPersistentMap m) {
		return (OnyxMap) eMapFn.invoke(m);
	}

	/**
	 * Loads a resource file (EDN map file) and converts it into an OnyxMap
	 * object. OnyxMaps are objects with an IPersistentMap entry property.
	 * @param  rsrcPath      fully qualified name of path to resource file
	 * @return        new OnyxMap object
	 */
	public static OnyxMap fromResources(String rsrcPath) {
		return (OnyxMap) ednFn.invoke(rsrcPath);
	}

	/**
	 * Retrieves a value for the specified key from the given IPersistentMap.
	 * Does not alter the map.
	 * @param  m             source map
	 * @param  key           key to retrive the value for
	 * @return                value Object associated by key
	 */
	public static Object get(IPersistentMap m, String key) {
		Object k = kwFn.invoke(key);
		return rawGetFn.invoke(m, k);
	}

	/**
	 * Retrieves a value for the specified key from the given IPersistentMap.
	 * Does not alter the map.
	 * @param  m             source map
	 * @param  key           key to retrive the value for
	 * @return                value Object associated by key
	 */
	public static Object get(OnyxMap m, String key) {
		return get(m.toMap(), key);
	}

	/**
	 * Adds a new key-value pair (string-object) to the given IPersistentMap.
	 * @param  m             map to add the new pair
	 * @param  key           key string to add
	 * @param  value         value object to add
	 * @return               updated map with new key-value pair
	 */
	public static IPersistentMap assoc(IPersistentMap m, String key, Object value) {
		Object k = kwFn.invoke(key);
		return (IPersistentMap)rawAssocFn.invoke(m, k, value);
	}

	/**
	 * Adds a new key-value pair (string-object) to the given IPersistentMap.
	 * @param  m             map to add the new pair
	 * @param  key           key string to add
	 * @param  value         value object to add
	 * @return               updated map with new key-value pair
	 */
	public static OnyxMap assoc(OnyxMap m, String key, Object value) {
		return toOnyxMap( assoc(m.toMap(), key, value) );
	}

	/**
	 * Removes a key-value pair (string-object) from the given IPersistentMap.
	 * @param  m             The map for which to remove the key-value pair
	 * @param  key          The name of the key to remove
	 * @return                The updated map without the key-value pair
	 */
	public static IPersistentMap dissoc(IPersistentMap m, String key) {
		Object k = kwFn.invoke(key);
		return (IPersistentMap)rawDissocFn.invoke(m, k);
	}

	/**
	 * Removes a key-value pair (string-object) from the given IPersistentMap.
	 * @param  m             The map for which to remove the key-value pair
	 * @param  key          The name of the key to remove
	 * @return                The updated map without the key-value pair
	 */
	public static OnyxMap dissoc(OnyxMap m, String key) {
		return toOnyxMap( dissoc(m.toMap(), key) );
	}

	/**
	 * Similar to get, but retrieves a nested value for the given keys.
	 * For example, if the map had three levels, with a top level key "1",
	 * and "1" value was another map with a value "2", and "2" was another map
	 * with a value "3", this method could get the value for "3" by using
	 * get-in(map, "1", "2", "3")
	 * @param  m             The map holding the key-values to search
	 * @param  keys          The set of keys describing the chain of retrieval holding the desired value
	 * @return               The value held by the specified key chain
	 */
	public static IPersistentMap getIn(IPersistentMap m, String... keys) {
		PersistentVector ks = VectorFns.keywordize(keys);
		return (IPersistentMap)rawGetInFn.invoke(m, ks);
	}

	/**
	 * Similar to get-in and assoc, associates a key-value pair with a nested map.
	 * The value describes the value to place, and the string of keys describes a specific
	 * nesting order for which to place the value. The last key in the chain will be the
	 * one added as a key in the value-map of the second last key
	 * @param  m        The map in which to associate the key-value pair
	 * @param  value    The value to be added to the nested map
	 * @param  keys     The key chain specifying how to add the key-value pair
	 * @return          The updated nested map
	 */
	public static IPersistentMap assocIn(IPersistentMap m, Object value, String... keys) {
		PersistentVector ks = VectorFns.keywordize(keys);
		return (IPersistentMap)rawAssocInFn.invoke(m, ks);
	}

	/**
	 * Merges two maps together, combining their key/value pairs. The result
	 * map will have a set of keys as (kset1 + kset2). Returns the new map.
	 * @param  m             First map to merge
	 * @param  m2            Second map to merge
	 * @return              The merged map
	 */
	public static IPersistentMap merge(IPersistentMap m, IPersistentMap m2) {
		return (IPersistentMap)rawMergeFn.invoke(m, m2);
	}

	/**
	 * Merges two maps together, combining their key/value pairs. The result
	 * map will have a set of keys as (kset1 + kset2). Returns the new map.
	 * @param  m             First map to merge
	 * @param  m2            Second map to merge
	 * @return              The merged map
	 */
	public static OnyxMap merge(OnyxMap m, OnyxMap m2) {
		return toOnyxMap( merge(m.toMap(), m2.toMap()) );
	}
}
