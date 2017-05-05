package org.onyxplatform.api.java;

import clojure.java.api.Clojure;
import clojure.lang.IFn;

/**
 * The purpose of the task scheduler is to control the order in which available
 * peers are allocated to which tasks. There are a few different Task Scheduler
 * implementations to choose from. The user should consult with the Onyx
 * information model for options.
 */
public class TaskScheduler implements OnyxNames {

	private static IFn kwFn;
	private static IFn nameFn;

	static {
		kwFn = Clojure.var(CORE, Keyword);
		nameFn = Clojure.var(CORE, Name);
	}

    	private Object kwType;

	/**
	 * Constructs a new TaskScheduler object from an existing TaskScheduler
	 * object.
	 * @param  ts     existing TaskScheduler object
	 */
	public TaskScheduler(TaskScheduler ts) {
    	kwType = ts.kwType;
	}

	/**
	 * Constructs a new TaskScheduler object using a string representing
	 * a valid TaskScheduler type.
	 * @param  s   string representing type of task scheduler to create
	 */
    	public TaskScheduler(String s) {
		kwType = kwFn.invoke(s);
	}

	/**
	 * Returns TaskScheduler content keyword object.
	 * Does not change existing contents.
	 * @return TaskScheduler Keyword
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
