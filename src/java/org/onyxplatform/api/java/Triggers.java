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
	 * Creates a new Triggers object using OnyxVector superconstructor.
	 * @return new Triggers object
	 */
    public Triggers() {
    }

	/**
	 * Creates a new Triggers object using an existing Triggers
	 * object (uses the existing trigger collection as content).
	 * Uses OnyxVector superconstructor.
	 * @param  Triggers ts         existing Triggers object to use for content
	 * @return          new Triggers object
	 */
    public Triggers(Triggers ts) {
	    super(ts.vContents);
    }

    public Triggers(OnyxVector ov) {
	    super(ov);
    }

	/**
	 * Adds an existing Trigger to the Triggers content vector.
	 * @param Trigger te Trigger to add to the Triggers content
	 */
    public void addTrigger(Trigger te) {
	    addElement(te);
    }

    public PersistentVector triggers() {
	PersistentVector out = PersistentVector.EMPTY;
	for (Object o : super.vContents) {
		Trigger t = (Trigger)o;
		IPersistentMap m = t.toMap();
		out.cons(m);
	}
	return out;
    }
}
