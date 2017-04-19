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
	 * Creates a new Lifecycle object using OnyxMap superconstructor.
	 * @return new Lifecycle object.
	 */
	public Lifecycle() {
		super();
	}

	/**
	 * Creates a new Lifecycle object using an existing content map.
	 * Uses OnyxMap superconstructor.
	 * @param  Lifecycle c             existing map to use for new Lifecycle
	 * @return           new Lifecycle object
	 */
	public Lifecycle(Lifecycle c) {
    		super(c.entry);
	}

	/**
	 * Creates a new Lifecycle object using an existing content map.
	 * Uses OnyxMap superconstructor.
	 * @param  Lifecycle c             existing map to use for new Lifecycle
	 * @return           new Lifecycle object
	 */
	public Lifecycle(OnyxMap e) {
		super(e);
	}
}
