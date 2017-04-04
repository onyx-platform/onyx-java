package org.onyxplatform.api.java;

import clojure.lang.PersistentArrayMap;
import java.util.Map;

/**
 * There are several interesting points to execute arbitrary code during a
 * task in Onyx. Onyx lets you plug in and calls functions before a task,
 * after a task, before a batch, and after a batch on every peer.
 * Additionally, there is another lifecycle hook that allows you to delay
 * starting a task in case you need to do some work like acquiring a
 * lock under contention. A peer’s lifecycle is isolated to itself,
 * and lifecycles never coordinate across peers. Usage of lifecycles
 * are entirely optional. Lifecycle data is submitted as a data structure
 * at job submission time.
 * Lifecycle derives from OnyxEntity.
 */
public class Lifecycle extends OnyxEntity
{
	protected static String coerceKw = OnyxLifecycleEntry;

	/**
	 * Creates a new Lifecycle object using OnyxEntity superconstructor.
	 * @return new Lifecycle object.
	 */
	public Lifecycle() {
	}

	/**
	 * Creates a new Lifecycle object using an existing content map.
	 * Uses OnyxEntity superconstructor.
	 * @param  Lifecycle c             existing map to use for new Lifecycle
	 * @return           new Lifecycle object
	 */
	private Lifecycle(Lifecycle c) {
    	super(c.entry);
	}

	/**
	 * Coerces Lifecycle object content map into proper onyx Lifecycle.
	 * Returns the onyx representation without altering the existing content map.
	 * @param  Map<String, Object>       jMap Content map to coerce
	 * @return             onyx representation of content map
	 */
	protected PersistentArrayMap coerce(Map<String, Object> jMap) {
		return (PersistentArrayMap) castTypesFn.invoke(coerceKw, jMap);
	}

}
