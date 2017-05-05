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
	 * Constructs a new Window object using the OnyxMap superconstructor.
	 */
	public Window() {
		super();
	}

	/**
	 * Constructs a new Window object using an existing Window (uses content map).
	 * Uses the OnyxMap superconstructor.
	 * @param	e	existing Window object to use for content
	 */
	public Window(Window e) {
    		super(e.entry);
	}

	/**
	 * Constructs a new Window object using an existing map representing a Window.
	 * @param  e       OnyxMap representing the window to be created.
	 */
	public Window(OnyxMap e) {
		super(e);
	}
}
