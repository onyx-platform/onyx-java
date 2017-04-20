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
	 * Creates a new FlowConditions object using OnyxVector superconstructor.
	 * @return new FlowConditions object
	 */
    public FlowConditions() {
    }

	/**
	 * Creates a new FlowConditions object using an existing FlowConditions
	 * object. Uses OnyxVector superconstructor.
	 * @param  FlowConditions fc            existing FlowConditions object
	 *                        				containing contents to use for
	 *                        				new FlowConditions object
	 * @return                new FlowConditions object
	 */
    public FlowConditions(FlowConditions fc) {
	    super(fc.vContents);
    }


	/**
	 * Adds an existing FlowCondition object to the FlowConditions
	 * content vector.
	 * @param FlowCondition ent FlowCondition to add to the FlowConditions
	 *                      	contents
	 */
    public void addCondition(FlowCondition ent) {
	    addElement(ent);
    }

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
