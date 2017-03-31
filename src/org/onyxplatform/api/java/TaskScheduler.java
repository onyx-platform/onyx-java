package org.onyxplatform.api.java;

import clojure.java.api.Clojure;
import clojure.lang.IFn;

/**
 * The purpose of the task scheduler is to control the order in which available
 * peers are allocated to which tasks. There are a few different Task Scheduler
 * implementations, contained in the class as ENUM options.
 */
public enum TaskScheduler implements OnyxNames
{
    BALANCED("onyx.task-scheduler/balanced"),
    PERCENTAGE("onyx.task-scheduler/percentage");

    private final String strRepr;

	/**
	 * Creates a new TaskScheduler object
	 * @param  String s             type of task scheduler to create
	 * @return        new TaskScheduler object
	 */
    private TaskScheduler(String s) {
        strRepr = s;
    }

	/**
	 * converts the TaskScheduler content string into a proper onyx
	 * task scheduler. Does not change existing contents.
	 * @return onyx representation of TaskScheduler content
	 */
    public Object toCljString() {
		IFn requireFn = Clojure.var(CORE, Require);
		IFn kwFn = Clojure.var(CORE, Keyword);
		return kwFn.invoke(strRepr);
    }

	/**
	 * returns a string representation of the TaskScheduler content string.
	 * Useful for debugging and testing.
	 * @return string representation of TaskScheduler content
	 */
	@Override
	public String toString() {
		return strRepr;
	}

}
