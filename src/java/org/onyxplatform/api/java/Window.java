package org.onyxplatform.api.java;

/**
 * Windows allow you to group and accrue data into possibly overlapping buckets.
 * Windows are intimately related to the Triggers feature.
 * Windowing splits up a possibly unbounded data set into finite, possibly
 * overlapping portions. Windows allow us create aggregations over distinct
 * portions of a stream, rather than stalling and waiting for the entire data
 * data set to arrive.
 * Window objects are derived from OnyxMap.
 */
public class Window extends OnyxMap
{
	/**
	 * Creates a new Window object using OnyxMap superconstructor.
	 * @return new Window object
	 */
	public Window() {
		super();
	}

	/**
	 * Creates a new Window object using an existing Window (uses content map).
	 * Uses OnyxMap superconstructor.
	 * @param  Window e             existing window to use for content
	 * @return        new Window object
	 */
	public Window(Window e) {
    		super(e.entry);
	}

	public Window(OnyxMap e) {
		super(e);
	}
}
