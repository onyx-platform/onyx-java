package org.onyxplatform.api.java;

import clojure.lang.PersistentVector;
import java.util.Arrays;
import java.util.ArrayList;

/**
 * Serves as a base for any Onyx concept that is represented by a vector of
 * onyx maps, e.g. workflows.
 */
public class OnyxVector
{
	protected PersistentVector vContents;

	/**
	 * Constructs a new OnyxVector object with an empty contents vector.
	 * @return new OnyxVector object with an empty content vector
	 */
	public OnyxVector() {
		vContents = PersistentVector.EMPTY;
	}

	/**
	 * Constructs a new OnyxVector object with an initial contents vector set
	 * to an existing passed vector
	 * @param  PersistentVector pv    existing vector to use as content vector
	 * @return                  new OnyxVector object
	 */
	public OnyxVector(PersistentVector pv) {
		vContents = pv;
	}

	public OnyxVector(OnyxVector ov) {
		vContents = ov.vContents;
	}

	/**
	 * Adds an existing object to the content vector of the object, appending
	 * it to the end of the content vector. 
	 */
	protected void addElement(OnyxMap o) {
	    vContents = vContents.cons(o);
	}

	/**
	 * Produces a string representation of the
	 * contents of the content vector without modifying the actual vector.
	 * @return string representation of the content vector
	 */
	@Override
	public String toString() {
    		return Arrays.toString(vContents.toArray());
	}
}
