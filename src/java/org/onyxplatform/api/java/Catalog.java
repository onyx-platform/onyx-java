package org.onyxplatform.api.java;

import clojure.lang.PersistentVector;
import clojure.lang.IPersistentMap;

/**
 * A catalog holds a set of tasks which can be passed to and run in a job.
 * A catalog derives from an OnyxVector.
 */
public class Catalog extends OnyxVector
{
	/**
	 * Constructs a new empty Catalog object.
	 * Uses the OnyxVector superconstructor.
	 */
    public Catalog() {
        super();
    }

	/**
	 * Creates a new Catalog object based on an existing Catalog object.
	 * Uses the OnyxVector superconstructor.
	 * @param  c    an existing Catalog object to use as a template for the new Catalog
	 */
    public Catalog(Catalog c) {
	    super(c.vContents);
    }

	/**
	 * Adds an existing Task object to the catalog object.
	 * Returns the updated Catalog object so that methods can be chained.
	 * @param task a task object to be added to the catalog
	 * @return updated Catalog object
	 */
    public Catalog addTask(Task task) {
	    addElement(task);
	    return this;
    }


    /**
     * Returns a PersistentVector containing IPersistentMap representations
     * of the tasks currently associated with the catalog object.
     * @return PersistentVector of IPersistentMaps
     */
    public PersistentVector tasks() {
	PersistentVector out = PersistentVector.EMPTY;
	for (Object o : super.vContents) {
		Task t = (Task)o;
		IPersistentMap m = t.toMap();
		out = out.cons(m);
	}
	return out;
    }
}
