package org.onyxplatform.api.java;

import clojure.lang.PersistentVector;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OnyxVector
{
	protected PersistentVector vContents;

	/**
	 * Constructs a new OnyxVector object with an empty contents vector.
	 * @return new OnyxVector object with an empty content vector
	 */
	protected OnyxVector() {
		vContents = PersistentVector.EMPTY;
	}

	/**
	 * Constructs a new OnyxVector object with an initial contents vector set
	 * to an existing vector passed in the signature.
	 * @param  PersistentVector pv    the existing vector to use as this
	 *                          vector object's initial content vector
	 * @return                  new OnyxVector object with initial content vector
	 */
	protected OnyxVector(PersistentVector pv) {
		vContents = pv;
	}

	/**
	 * Creates a new ArrayList by converting the object's existing content
	 * vector. Does not alter the existing content vector.
	 * @return the newly created ArrayList representation of the
	 *             object content vector
	 */
   	public List<Map<String, Object>> toList() {
        	return new ArrayList(vContents);
    	}

	/**
	 * Adds an existing object to the content vector of the object, appending
	 * it to the end of the content vector. The object should represent a map.
	 * @param Object o object to be added to the existing content vector
	 */
	protected void addElement( Object o) {
	    vContents = vContents.cons( o );
	}

	/**
	 * Iterates over the existing content vector, ensuring each constituent
	 * map is a Clojure PersistentHashMap in a new PersistentVector container.
	 * @return the new PersistentVector container containing guaranteed
	 * PersistentHashMap entities
	 */
	protected PersistentVector toCljVector() {

		PersistentVector v = PersistentVector.EMPTY;

		// Iterate each entity
		// coercing
		for (Object e : vContents) {
			OnyxEntity oe = (OnyxEntity) e;
			v = v.cons( oe.toCljMap() );
		}

		return v;
	}

		/**
		 * Produces without modification a string representation of the
		 * contents of the content vector
		 * @return string representation of the content vector
		 */
    	@Override
    	public String toString() {
        	return Arrays.toString(vContents.toArray());
    	}
}
