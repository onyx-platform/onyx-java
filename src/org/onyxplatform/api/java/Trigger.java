package org.onyxplatform.api.java;

import clojure.lang.PersistentHashMap;
import java.util.Map;

/**
 * Triggers are a feature that interact with Windows.
 * Windows capture and bucket data over time.
 * Triggers let you release the captured data over a variety stimuli.
 * Triggers derive from OnyxEntity.
 */
public class Trigger extends OnyxEntity
{
	protected static String coerceKw = "trigger-entry";

	/**
	 * Creates a new Trigger object using OnyxEntity superconstructor.
	 * @return new Trigger object
	 */
	public Trigger() {
	}

	/**
	 * Creates a new Trigger object using an existing Trigger objects content.
	 * Uses OnyxEntity superconstructor.
	 * @param  Trigger e             existing trigger to use for new Trigger
	 * @return         new Trigger object
	 */
	private Trigger(Trigger e) {
    	super(e.entry);
	}

	/**
	 * Coerces Trigger object content map into proper onyx Trigger.
	 * Returns the onyx representation without altering the existing content map.
	 * @param  Map<String, Object>       jMap content map to coerce
	 * @return             onyx representation of content map
	 */
	protected PersistentHashMap coerce(Map<String, Object> jMap) {
		return (PersistentHashMap) castTypesFn.invoke(coerceKw, jMap);
	}
}
