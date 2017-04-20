package org.onyxplatform.api.java;

import clojure.lang.PersistentVector;
import clojure.lang.IPersistentMap;

/**
 * Windows objects are a set of Window objects that can be used for a
 * given job.
 * Windows derive from OnyxVector.
 */
public class Windows extends OnyxVector
{
	/**
	 * Creates a new Windows object using OnyxVector superconstructor.
	 * @return new Windows object
	 */
    public Windows() {
    }

	/**
	 * Creates a new Windows object using an existing Windows
	 * object (uses contents from existing Windows).
	 * Uses OnyxVector superconstructor.
	 * @param  Windows wes           existing windows object to use for content
	 * @return         new Windows object
	 */
    public Windows(Windows wes) {
	    super(wes.vContents);
    }

    public Windows(OnyxVector ov) {
	    super(ov);
    }

	/**
	 * Adds an existing Window object to the Windows content vector.
	 * @param Window w Window to add
	 */
    public void addWindow(Window w) {
	    addElement(w);
    }

    public PersistentVector windows() {
	PersistentVector out = PersistentVector.EMPTY;
	for (Object o : super.vContents) {
		Window w = (Window)o;
		IPersistentMap m = w.toMap();
		out = out.cons(m);
	}
	return out;
    }
}
