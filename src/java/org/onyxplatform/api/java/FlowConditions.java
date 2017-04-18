package org.onyxplatform.api.java;

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
    private FlowConditions(FlowConditions fc) {
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
}
