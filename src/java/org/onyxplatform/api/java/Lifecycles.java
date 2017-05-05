package org.onyxplatform.api.java;

import clojure.lang.PersistentVector;
import clojure.lang.IPersistentMap;

/**
 * Lifecycles objects are a set of Lifecycle objects that can be applied to
 * Tasks running as part of a job.
 * Lifecycles derive from OnyxVector.
 */
public class Lifecycles extends OnyxVector
{
    /**
     * Constructs a new Lifecycles object using OnyxVector superconstructor.
     */
    public Lifecycles() {
        super();
    }

    /**
     * Constructs a new Lifecycles object using an existing Lifecycles object.
     * Uses OnyxVector superconstructor.
     * @param  ls            existing Lifecycles object containing contents to use for new Lifecycles object
     */
    public Lifecycles(Lifecycles ls) {
	    super(ls.vContents);
    }


    /**
     * Adds an existing Lifecycle object to the Lifecycles object contents.
     * Returns the Lifecycles object so that methods can be chained.
     * @param cs Lifecycle to add to the Lifecycles contents
     * @return the updated Lifecycles object.
     */
    public Lifecycles addLifecycle(Lifecycle cs) {
	    addElement(cs);
	    return this;
    }

    /**
     * Creates and returns a PersistentVector of IPersistentMaps based on map representations
     * of the Lifecycles contained by the Lifecycles vector.
     * @return PersistentVector of IPersistentMaps
     */
    public PersistentVector cycles() {
	PersistentVector out = PersistentVector.EMPTY;
	for (Object o : super.vContents) {
		Lifecycle l = (Lifecycle)o;
		IPersistentMap m = l.toMap();
		out = out.cons(m);
	}
	return out;
    }
}
