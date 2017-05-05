package org.onyxplatform.api.java;

import clojure.lang.PersistentVector;
import clojure.lang.IPersistentMap;
import java.util.Arrays;
import java.util.ArrayList;

/**
 * Serves as a base for any Onyx concept that is represented by a vector of
 * onyx maps, e.g. windows, triggers, etc.
 */
public class OnyxVector
{
	protected PersistentVector vContents;

	/**
	 * Constructs a new OnyxVector object with an empty contents vector.
	 */
	public OnyxVector() {
		vContents = PersistentVector.EMPTY;
	}

	/**
	 * Constructs a new OnyxVector object with an initial contents vector set
	 * to an existing passed vector
	 * @param  pv    existing PersistentVector to use as content vector
	 */
	protected OnyxVector(PersistentVector pv) {
		vContents = pv;
	}

	/**
	 * Constructs a new OnyxVector from an existing OnyxVector object.
	 * @param  ov         existing OnyxVector object
	 */
	public OnyxVector(OnyxVector ov) {
		vContents = ov.vContents;
	}

	/**
	 * Adds an existing object to the content vector of the object, appending
	 * it to the end of the content vector.
	 * Returns the OnyxVector object so that methods can be chained.
	 * @param o an OnyxMap object to be added to the vector
	 * @return the updated OnyxVector object
	 */
	public OnyxVector addElement(OnyxMap o) {
	    vContents = vContents.cons(o);
	    return this;
	}

	/**
	 * Adds an arbitrary number of OnyxMap object elements to the OnyxVector.
	 * Returns the updated OnyxVector object so that methods can be chained.
	 * @param  oms        OnyxMap objects to be added to the vector
	 * @return            the updated OnyxVector object
	 */
	public OnyxVector addElements(OnyxMap... oms) {
		for (OnyxMap m : oms) {
			addElement(m);
		}
		return this;
	}

	/**
	 * Converts the OnyxVector object contents to a PersistentVector and returns it.
	 * Converts every contained OnyxMap object to an IPersistentMap object.
	 * @return A PersistentVector containing IPersistentMaps of content Objects
	 */
	public PersistentVector toVector() {
		PersistentVector out = PersistentVector.EMPTY;
		for (Object o : vContents) {
			OnyxMap om = (OnyxMap)o;
			IPersistentMap m = om.toMap();
			out = out.cons(m);
		}
		return out;
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
