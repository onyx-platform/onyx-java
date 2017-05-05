package org.onyxplatform.api.java;

import clojure.lang.PersistentVector;
import clojure.lang.IPersistentMap;

/**
 * Triggers are a set of Trigger objects that can be collectively applied
 * to a window or windows associated with an onyx job.
 * the Triggers object derives from OnyxVector.
 */
public class Triggers extends OnyxVector
{
	/**
	 * Constructs a new, empty Triggers object using
	 * the OnyxVector superconstructor.
	 */
    public Triggers() {
        super();
    }

	/**
	 * Constructs a new Triggers object using an existing Triggers
	 * object (uses the existing Trigger collection as content).
	 * Uses OnyxVector superconstructor.
	 * @param  ts  existing Triggers object to use for content
	 */
    public Triggers(Triggers ts) {
	    super(ts.vContents);
    }


	/**
	 * Adds an existing Trigger to the Triggers content vector.
	 * Returns the Triggers object so that methods can be chained.
	 * @param  te Trigger to add to the Triggers content
	 * @return the updated Triggers object
	 */
    public Triggers addTrigger(Trigger te) {
	    addElement(te);
	    return this;
    }

    /**
     * Returns the trigger objects contained by the Triggers container as a
     * vector of maps, rather than objects.
     * @return vector of map representations of the triggers contained by
     * the triggers object.
     */
    public PersistentVector triggers() {
	PersistentVector out = PersistentVector.EMPTY;
	for (Object o : super.vContents) {
		Trigger t = (Trigger)o;
		IPersistentMap m = t.toMap();
		out = out.cons(m);
	}
	return out;
    }
}
