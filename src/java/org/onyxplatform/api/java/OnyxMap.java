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
	 * @return new OnyxMap object
	 */
	public OnyxMap() {
		entry = PersistentHashMap.EMPTY;
	}


	/**
	 * Constructs a new OnyxMap object with an initial contents map set
	 * to the passed PersistentHashMap.
	 * @param  PersistentHashMap ent           existing PersistentHashMap
	 *                           		to use as this object's content map
	 * @return                   new OnyxMap object with initial content map
	 */
	public OnyxMap(IPersistentMap m) {
		entry = m;
	}

	public OnyxMap(OnyxMap ent) {
		entry = ent.entry;
	}

	/**
	 * Adds a new key-value entry to the existing content PersistentHashMap.
	 * This method updates the existing object content vector to be equal
	 * to the old plus the new entry.
	 * @param String param key to add to the existing content PersistentHashMap
	 * @param Object arg   value to associate with the added key
	 */
	public void addKeywordParameter(String keyname, String keywordValue) {
		Object k = kwFn.invoke(keyname); 
	 	Object v = kwFn.invoke(keywordValue);
		entry = (IPersistentMap) entry.assoc(k, v);
	}

	/**
	 * Adds a new key-value entry to the existing content PersistentHashMap.
	 * This method updates the existing object content vector to be equal
	 * to the old plus the new entry.
	 * @param String param key to add to the existing content PersistentHashMap
	 * @param Object arg   value to associate with the added key
	 */
	public void addObjectParameter(String keyname, Object arg) {
		Object k = kwFn.invoke(keyname); 
		entry = (IPersistentMap) entry.assoc(k, arg);
	}

	/**
	 * Returns a Java Map representation of the existing
	 * PersistentHashMap content 'entry'.
	 * @return Java Map representation of existing PersistentHashMap 'entry'
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
