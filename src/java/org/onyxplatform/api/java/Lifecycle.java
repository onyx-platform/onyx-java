package org.onyxplatform.api.java;

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
 * Lifecycle derives from OnyxMap.
 */
public class Lifecycle extends OnyxMap
{
	/**
	 * Constructs a new Lifecycle object using OnyxMap superconstructor.
	 */
	public Lifecycle() {
		super();
	}

	/**
	 * Constructs a new Lifecycle object using an existing Lifecycle object.
	 * Uses OnyxMap superconstructor.
	 * @param  c             existing Lifecycle object to use for new Lifecycle
	 */
	public Lifecycle(Lifecycle c) {
    		super(c.entry);
	}

	/**
	 * Constructs a new Lifecycle object using an existing content map.
	 * Uses OnyxMap superconstructor.
	 * @param   e             existing OnyxMap to use for new Lifecycle
	 */
	public Lifecycle(OnyxMap e) {
		super(e);
	}
}
