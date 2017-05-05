package org.onyxplatform.api.java;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import clojure.lang.PersistentHashMap;
import clojure.lang.IPersistentMap;
import java.util.Map;


/**
 * Serves as a base for any Onyx concept that is represented
 * only by a map. Implementors include things like Tasks, LifecycleCall,
 * FlowConditionEntry, WindowEntry, etc.
 */
public class OnyxMap implements OnyxNames
{
	/**
	 * Classwide functionality
	 */
	protected final static IFn kwFn;

	static {
		kwFn = Clojure.var(CORE, Keyword);
	}

	/**
	 * Instance specific functionality
	 */
	protected IPersistentMap entry;


	/**
	 * Constructs a new OnyxMap object with an empty contents map.
	 */
	public OnyxMap() {
		entry = PersistentHashMap.EMPTY;
	}


	/**
	 * Constructs a new OnyxMap object with an initial contents map set
	 * to the passed PersistentHashMap.
	 * @param  m   existing PersistentHashMap to use as this object's content map
	 */
	public OnyxMap(IPersistentMap m) {
		entry = m;
	}

	/**
	 * Constructs a new OnyxMap based on the passed OnyxMap object.
	 * @param  ent      the OnyxMap to use for construction
	 */
	public OnyxMap(OnyxMap ent) {
		entry = ent.entry;
	}

	/**
	 * Adds a new key-value entry to the existing content PersistentHashMap.
	 * This method updates the existing object content vector to be equal
	 * to the old plus the new entry.
	 * @param keyname key to add to the existing content PersistentHashMap
	 * @param keywordValue   Keyword value to associate with the added key
	 * @return returns the updated object so that methods can be chained
	 */
	public OnyxMap addKeywordParameter(String keyname, String keywordValue) {
		Object k = kwFn.invoke(keyname);
	 	Object v = kwFn.invoke(keywordValue);
		entry = entry.assoc(k, v);
		return this;
	}

	/**
	 * Adds a new key-value entry to the existing content PersistentHashMap.
	 * This method updates the existing object content vector to be equal
	 * to the old plus the new entry.
	 * @param keyname key to add to the existing content PersistentHashMap
	 * @param arg   Object type value to associate with the added key
	 * @return returns the updated object so that methods can be chained
	 */
	public OnyxMap addObjectParameter(String keyname, Object arg) {
		Object k = kwFn.invoke(keyname);
		entry = entry.assoc(k, arg);
		return this;
	}

	/**
	 * Returns a Java Map representation of the existing
	 * PersistentHashMap content 'entry'.
	 * @return Java Map representation of existing PersistentHashMap entry
	 */
	@SuppressWarnings("unchecked")
	public IPersistentMap toMap() {
		return entry;
	}

	/**
	 * Produces a string representation of the contents of the content map
	 * without modifying the actual content map.
	 * @return string representation of the content map
	 */
	@Override
	public String toString() {
	    	return entry.toString();
	}
}
