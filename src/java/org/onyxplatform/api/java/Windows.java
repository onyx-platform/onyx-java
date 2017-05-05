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
	 * Constructs a new Windows object using OnyxVector superconstructor.
	 */
    public Windows() {
    }

	/**
	 * Constructs a new Windows object using an existing Windows
	 * object (uses contents from existing Windows).
	 * Uses OnyxVector superconstructor.
	 * @param  wes   existing windows object to use for content
	 */
    public Windows(Windows wes) {
	    super(wes.vContents);
    }


	/**
	 * Adds an existing Window object to the Windows content vector.
	 * Returns the windows object so that methods can be chained.
	 * @param w Window to add
	 * @return the updated Windows object
	 */
    public Windows addWindow(Window w) {
	    addElement(w);
	    return this;
    }

    /**
     * Returns the windows objects contained by the Windows object vector.
     * Returns them as a PersistentVector rather than a Windows object.
     * @return  windows contained by the Windows object
     */
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
