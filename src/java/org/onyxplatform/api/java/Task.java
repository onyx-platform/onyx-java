package org.onyxplatform.api.java;

/**
 * A task specifies a map that represents a single onyx unit of work. This
 * corresponds to an actual system function and piece of code to run in the
 * context of an onyx job.
 * Tasks derive from OnyxMap.
 */
public class Task extends OnyxMap
{
	/**
	 * Creates a new Task object using OnyxMap superconstructor.
	 * @return new Task object
	 */
	public Task() {
		super();
	}

	/**
	 * Creates a new Task object using an existing content map.
	 * Uses OnyxMap superconstructor.
	 * @param  PersistentHashMap ent           existing map to use for new Task
	 * @return                   new Task object
	 */
	public Task(Task task) {
    		super(task.entry);
	}

	public Task(OnyxMap e) {
		super(e);
	}

}
