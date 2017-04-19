package org.onyxplatform.api.java;

import clojure.java.api.Clojure;
import clojure.lang.IFn;

/**
 * The purpose of the task scheduler is to control the order in which available
 * peers are allocated to which tasks. There are a few different Task Scheduler
 * implementations, contained in the class as ENUM options.
 */
public class TaskScheduler implements OnyxNames {

	private static IFn kwFn;
	private static IFn nameFn;

	static {
		IFn kwFn = Clojure.var(CORE, Keyword);
		IFn nameFn = Clojure.var(CORE, Name);
	}

    private final Object kwType;

	/**
	 * Creates a new TaskScheduler object
	 * @param  String s             type of task scheduler to create
	 * @return        new TaskScheduler object
	 */
    private TaskScheduler(String s) {
	kwType = kwFn.invoke(s);
    }

	/**
	 * Returns TaskScheduler content string into a proper onyx
	 * task scheduler. Does not change existing contents.
	 * @return onyx representation of TaskScheduler content
	 */
    public Object schedule() {
	    return kwType;
    }

	/**
	 * returns a string representation of the TaskScheduler content string.
	 * Useful for debugging and testing.
	 * @return string representation of TaskScheduler content
	 */
	@Override
	public String toString() {
		return (String)nameFn.invoke(kwType);
	}
}
