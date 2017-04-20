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
	 * Creates a new catalog, which derives from OnyxVector.
	 * @return new catalog object
	 */
    public Catalog() {
    }

	/**
	 * Creates a new Catalog from an existing Catalog.
	 * @param  Catalog c             existing Catalog to use for new Catalog
	 * @return         new Catalog object
	 */
    public Catalog(Catalog c) {
	    super(c.vContents);
    }

	/**
	 * Adds an existing Task object to the catalog.
	 * @param Task task task object to be added to the catalog
	 */
    public void addTask(Task task) {
	    addElement(task);
    }


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

