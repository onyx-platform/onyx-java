package org.onyxplatform.api.java;

import clojure.lang.IPersistentMap;
import java.util.Map;

/**
 * Flow conditions are used for isolating logic about whether or not segments
 * should pass through different tasks in a workflow, and support a rich degree
 * of composition with runtime parameterization.
 * Flow conditions derive from OnyxMap.
 */
public class FlowCondition extends OnyxMap
{
	/**
	 * Creates a new FlowCondition object using OnyxMap superconstructor.
	 * @return new FlowCondition object
	 */
	public FlowCondition() {
		super();
	}

	/**
	 * Creates a new FlowCondition object using an existing FlowCondition.
	 * Uses OnyxMap superconstructor.
	 * @param  PersistentHashMap ent           existing map to use for new FlowCondition
	 * @return                  new FlowCondition object
	 */
	public FlowCondition(FlowCondition flowCondition) {
    		entry = flowCondition.entry;
	}

	/**
 	* Creates a new FlowCondition object using an existing FlowCondition.
 	* Uses OnyxMap superconstructor.
 	* @param  PersistentHashMap ent           existing map to use for new FlowCondition
 	* @return                  new FlowCondition object
 	*/
	public FlowCondition(OnyxMap m) {
		entry = m.entry;
	}

}
