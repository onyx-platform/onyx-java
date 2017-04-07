package org.onyxplatform.api.java;

import clojure.lang.IPersistentMap;
import java.util.Map;

/**
 * Windows allow you to group and accrue data into possibly overlapping buckets.
 * Windows are intimately related to the Triggers feature.
 * Windowing splits up a possibly unbounded data set into finite, possibly
 * overlapping portions. Windows allow us create aggregations over distinct
 * portions of a stream, rather than stalling and waiting for the entire data
 * data set to arrive.
 * Window objects are derived from OnyxEntity.
 */
public class Window extends OnyxEntity
{
	protected static String coerceKw = OnyxWindowEntry;

	/**
	 * Creates a new Window object using OnyxEntity superconstructor.
	 * @return new Window object
	 */
	public Window() {
	}

	/**
	 * Creates a new Window object using an existing Window (uses content map).
	 * Uses OnyxEntity superconstructor.
	 * @param  Window e             existing window to use for content
	 * @return        new Window object
	 */
	private Window(Window e) {
    	super(e.entry);
	}

	/**
	 * Coerces Window object content map into proper onyx window.
	 * Returns the onyx representation without altering the existing content map.
	 * @param  Map<String, Object>       jMap content map to coerce
	 * @return             onyx representation of content map
	 */
	protected IPersistentMap coerce(Map<String, Object> jMap) {
		return (IPersistentMap) castTypesFn.invoke(coerceKw, jMap);
	}
}
