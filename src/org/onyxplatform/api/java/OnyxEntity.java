package org.onyxplatform.api.java;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import clojure.lang.PersistentHashMap;
import java.util.Map;


/**
 * Serves as a base for any Onyx concept that is represented
 * only by a map. Implementors include things like Tasks, LifecycleCall,
 * FlowConditionEntry, WindowEntry, etc.
 */
public abstract class OnyxEntity implements OnyxNames
{
	/**
	 * Classwide functionality
	 */
	protected final static IFn castTypesFn;

	/**
	 * Initializes classwide onyx typecasting as castTypesFn by initializing
	 * clojure-java interoperability via the onyx library.
	 * This allows type conversion between java maps and vectors and clojure
	 * PersistentHashMaps and PersistentVectors, required by onyx.
	 */
	static {
		IFn requireFn = Clojure.var(CORE, Require);
		requireFn.invoke(Clojure.read(INTEROP));
		castTypesFn = Clojure.var(INTEROP, CastTypes);
	}


	/**
	 * Instance specific functionality
	 */
	protected PersistentHashMap entry;


	/**
	 * Constructs a new OnyxEntity object with an empty contents map.
	 * @return new OnyxEntity object
	 */
	protected OnyxEntity() {
		entry = PersistentHashMap.EMPTY;
	}


	/**
	 * Constructs a new OnyxEntity object with an initial contents map set
	 * to the passed PersistentHashMap.
	 * @param  PersistentHashMap ent           existing PersistentHashMap
	 *                           		to use as this object's content map
	 * @return                   new OnyxEntity object with initial content map
	 */
	protected OnyxEntity(PersistentHashMap ent) {
		entry = ent;
	}


	/**
	 * Creates a clojure PersistentHashMap from a java Map made of string-object
	 * key value pairs based on the implementor's specification.
	 * @param  Map<String, Object>       jMap java Map to be converted
	 * @return             new PersistentHashMap representation of the java Map
	 */
	protected abstract PersistentHashMap coerce(Map<String, Object> jMap);


	/**
	 * Adds a new key-value entry to the existing content PersistentHashMap.
	 * This method updates the existing object content vector to be equal
	 * to the old plus the new entry.
	 * @param String param key to add to the existing content PersistentHashMap
	 * @param Object arg   value to associate with the added key
	 */
	public void addParameter(String param, Object arg) {
		entry = (PersistentHashMap) entry.assoc(param, arg);
	}


	/**
	 * Returns a Java Map representation of the existing
	 * PersistentHashMap content 'entry'.
	 * @return Java Map representation of existing PersistentHashMap 'entry'
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> toMap() {
		return (Map<String, Object>) entry;
	}


	/**
	 * Creates a clojure PersistentHashMap of the content Map by using the
	 * implementor specific coerce method.
	 * @return clojure PHM representation of content map
	 */
	public PersistentHashMap toCljMap() {
		return coerce( toMap() );
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
