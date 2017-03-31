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
	 * Creates a new catalog from an existing catalog.
	 * @param  Catalog c             existing catalog to use for new catalog
	 * @return         new catalog object
	 */
    private Catalog(Catalog c) {
	    super(c.vContents);
    }

	/**
	 * Adds an existing task to the catalog.
	 * @param Task task task object to be added to the catalog
	 */
    public void addTask(Task task) {
	    addElement(task);
    }
}
