package org.onyxplatform.api.java;

/**
 * A peer configuration specifies the set of system parameters that
 * will be used for all peers on a given machine.
 * PeerConfiguration derives from OnyxMap.
 */
public class PeerConfiguration extends OnyxMap
{
	/**
	 * Creates a new PeerConfiguration object using OnyxMap superconstructor.
	 * @return new PeerConfiguration object
	 */
	public PeerConfiguration() {
		super();
	}

	/**
	 * Creates a new PeerConfiguration object using an exsiting content map
	 * from an exsiting peer configuration. Uses OnyxMap superconstructor.
	 * @param  PeerConfiguration cfg           existing PeerConfiguration object
	 * @return                   new PeerConfiguration object
	 */
	public PeerConfiguration(PeerConfiguration cfg) {
    		super(cfg.entry);
	}

	/**
	 * Creates a new PeerConfiguration object using an exsiting content map
	 * from an exsiting peer configuration. Uses OnyxMap superconstructor.
	 * @param  PeerConfiguration cfg           existing PeerConfiguration object
	 * @return                   new PeerConfiguration object
	 */
	public PeerConfiguration(OnyxMap m) {
    		super(m);
	}
}
