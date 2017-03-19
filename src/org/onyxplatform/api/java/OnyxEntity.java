package org.onyxplatform.api.java;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import clojure.lang.PersistentHashMap;
import java.util.Map;



public abstract class OnyxEntity
	implements OnyxNames
{
	// Class --------------------------------
	//
	
	protected final static IFn castTypesFn;
	protected final static IFn kwFn;

	static {

		IFn requireFn = Clojure.var(CORE, Require);
		kwFn = Clojure.var(CORE, Keyword);

		requireFn.invoke(Clojure.read(INTEROP));
		castTypesFn = Clojure.var(INTEROP, CastTypes);
	}


	// Instance ------------------------------
	// 

	protected PersistentHashMap entry;


	protected OnyxEntity() {
		entry = PersistentHashMap.EMPTY;
	}

	protected OnyxEntity(PersistentHashMap ent) {
		entry = ent;
	}

	// Abstract method that handles entity conversion
	// specifics.
	//
	protected abstract PersistentHashMap coerce(Map<String, Object> jMap);	

    
    	public void addParameter(String param, Object arg) {
		entry = (PersistentHashMap) entry.assoc(param, arg);
    	}


	public Map<String, Object> toMap() {
		return (Map<String, Object>) entry;
	}	

	public PersistentHashMap toCljMap() {
		return coerce( toMap() );
	}
    
    	@Override
    	public String toString() {
        	return entry.toString();
    	}
}
