package org.onyxplatform.api.java;

/**
 * Lifecycles objects are a set of Lifecycle objects that can be applied to
 * Tasks running as part of a job.
 * Lifecycles derive from OnyxVector.
 */
public class Lifecycles extends OnyxVector
{
    /**
     * Creates a new Lifecycles object using OnyxVector superconstructor.
     * @return new Lifecycles object
     */
    public Lifecycles() {
    }

    /**
     * Creates a new Lifecycles object using an existing Lifecycles object.
     * Uses OnyxVector superconstructor.
     * @param  Lifecycles ls            existing Lifecycles object containing
     *                              contents to use for new Lifecycles object
     * @return            new Lifecycles object
     */
    public Lifecycles(Lifecycles ls) {
	    super(ls.vContents);
    }


    public Lifecycles(OnyxVector ov) {
	    super(ov);
    }

    /**
     * Adds an existing Lifecycle to the Lifecycles content vector.
     * @param Lifecycle cs Lifecycle to add to the Lifecycles contents
     */
    public void addLifecycle(Lifecycle cs) {
	    addElement(cs);
    }
}
