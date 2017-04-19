package org.onyxplatform.api.java;

import clojure.lang.IPersistentMap;
import java.util.Map;

/**
 * Triggers are a feature that interact with Windows.
 * Windows capture and bucket data over time.
 * Triggers let you release the captured data over a variety stimuli.
 * Triggers derive from OnyxMap.
 */
public class Trigger extends OnyxMap 
{
	/**
	 * Creates a new Trigger object using OnyxMap superconstructor.
	 * @return new Trigger object
	 */
	public Trigger() {
		super();
	}

	/**
	 * Creates a new Trigger object using an existing Trigger objects content.
	 * Uses OnyxMap superconstructor.
	 * @param  Trigger e             existing trigger to use for new Trigger
	 * @return         new Trigger object
	 */
	public Trigger(Trigger e) {
    		super(e.entry);
	}

	public Trigger(OnyxMap m) {
		super(m);
	}

}
