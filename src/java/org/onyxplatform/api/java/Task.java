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
	 * Constructs a new Task object using the OnyxMap superconstructor.
	 * @return new Task object
	 */
	public Task() {
		super();
	}

	/**
	 * Constructs a new Task object using an existing Task.
	 * Uses OnyxMap superconstructor.
	 * @param  Task task           existing task to use for new task
	 * @return                   new Task object
	 */
	public Task(Task task) {
    		super(task.entry);
	}

	/**
	 * Constructs a new Task object using a map representing the task to be created.
	 * Uses the OnyxMap superconstructor.
	 * @param  OnyxMap e             existing map to use for new Task
	 * @return         new Task object
	 */
	public Task(OnyxMap e) {
		super(e);
	}

}
