package org.onyxplatform.api.java;

import clojure.lang.PersistentVector;
import clojure.lang.IPersistentMap;

/**
 * FlowConditions objects are a set of FlowCondition objects that can be applied
 * to Tasks running as part of a job.
 * FlowConditions derive from OnyxVector.
 */
public class FlowConditions extends OnyxVector
{
	/**
	 * Constructs a new FlowConditions object.
	 * Uses the OnyxVector superconstructor.
	 */
    public FlowConditions() {
    }

	/**
	 * Constructs a new FlowConditions object using an existing FlowConditions
	 * object.
	 * Uses OnyxVector superconstructor.
	 * @param  fc   existing FlowConditions object
	 */
    public FlowConditions(FlowConditions fc) {
	    super(fc.vContents);
    }


	/**
	 * Adds an existing FlowCondition object to the FlowConditions vector.
	 * Returns the updated FlowConditions so that methods can be chained.
	 * @param ent FlowCondition object to be added.
	 * @return updated FlowConditions object
	 */
    public FlowConditions addCondition(FlowCondition ent) {
	    addElement(ent);
	    return this;
    }

    /**
     * Creates and returns a PersistentVector of IPersistentMaps based on the
     * FlowCondition objects contained by the FlowConditions vector object.
     * @return A PersistentVector of IPersistentMaps
     */
    public PersistentVector conditions() {
	PersistentVector out = PersistentVector.EMPTY;
	for (Object o : super.vContents) {
		FlowCondition fc = (FlowCondition)o;
		IPersistentMap m = fc.toMap();
		out = out.cons(m);
	}
	return out;
    }
}
