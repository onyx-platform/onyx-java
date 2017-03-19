package org.onyxplatform.api.java;

import clojure.lang.PersistentVector;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OnyxVector
{
	protected PersistentVector vContents;

	protected OnyxVector() {
		vContents = PersistentVector.EMPTY;
	}

	protected OnyxVector(PersistentVector pv) {
		vContents = pv;
	}
    
   	public List<Map<String, Object>> toList() {
        	return new ArrayList(vContents);
    	}

	protected void addElement( Object o) {
	    vContents = vContents.cons( o );
	}

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

    	@Override
    	public String toString() {
        	return Arrays.toString(vContents.toArray());
    	}
}
