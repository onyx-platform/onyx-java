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
	 * Creates a new FlowCondition object.
	 * Uses the OnyxMap superconstructor.
	 */
	public FlowCondition() {
		super();
	}

	/**
	 * Constructs a new FlowCondition object based on an existing FlowCondition.
	 * Uses the OnyxMap superconstructor.
	 * @param  flowCondition  existing FlowCondition to use for new FlowCondition
	 */
	public FlowCondition(FlowCondition flowCondition) {
    		entry = flowCondition.entry;
	}

	/**
 	* Constructs a new FlowCondition object using an existing FlowCondition.
 	* Uses OnyxMap superconstructor.
 	* @param  m           OnyxMap to use for new FlowCondition
 	*/
	public FlowCondition(OnyxMap m) {
		entry = m.entry;
	}

}
