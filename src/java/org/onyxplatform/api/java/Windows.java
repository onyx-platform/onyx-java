package org.onyxplatform.api.java;

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
    private Windows(Windows wes) {
	    super(wes.vContents);
    }

	/**
	 * Adds an existing Window object to the Windows content vector.
	 * @param Window w Window to add
	 */
    public void addWindow(Window w) {
	    addElement(w);
    }
}
