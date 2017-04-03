package org.onyxplatform.api.java;

import clojure.lang.PersistentHashMap;
import java.util.Map;

/**
 * Flow conditions are used for isolating logic about whether or not segments
 * should pass through different tasks in a workflow, and support a rich degree
 * of composition with runtime parameterization.
 * Flow conditions derive from OnyxEntity.
 */
public class FlowCondition extends OnyxEntity
{
	protected static String coerceKw = OnyxFlowConditionsEntry;

	/**
	 * Creates a new FlowCondition object using OnyxEntity superconstructor.
	 * @return new FlowCondition object
	 */
	public FlowCondition() {
    	entry = PersistentHashMap.EMPTY;
	}

	/**
	 * Creates a new FlowCondition object using an existing content map.
	 * Uses OnyxEntity superconstructor.
	 * @param  PersistentHashMap ent           existing map to use for new FlowCondition
	 * @return                  new FlowCondition object
	 */
	private FlowCondition(PersistentHashMap ent) {
    	entry = ent;
	}

	/**
	 * Coerces FlowCondition object content map into proper onyx FlowCondition.
	 * Returns the onyx representation without altering the existing content map.
	 * @param  Map<String, Object>       jMap content map to coerce
	 * @return             onyx representation of content map
	 */
	protected PersistentHashMap coerce(Map<String, Object> jMap) {
		return (PersistentHashMap) castTypesFn.invoke(coerceKw, jMap);
	}
}
