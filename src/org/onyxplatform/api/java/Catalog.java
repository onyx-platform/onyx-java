package org.onyxplatform.api.java;

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
    private Catalog(Catalog c) {
	    super(c.vContents);
    }

	/**
	 * Adds an existing Task object to the catalog.
	 * @param Task task task object to be added to the catalog
	 */
    public void addTask(Task task) {
	    addElement(task);
    }
}
