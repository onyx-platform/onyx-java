package org.onyxplatform.api.java;

/**
 * A peer configuration specifies the set of system parameters that
 * will be used for all peers on a given machine.
 * PeerConfiguration derives from OnyxMap.
 */
public class PeerConfiguration extends OnyxMap
{
	private Object tenancyId;

	/**
	 * Constructs a new empty PeerConfiguration object using a tenancyId.
	 * @param Object tenancyId		a unique id used to identify the PeerConfiguration
	 * @return new PeerConfiguration object
	 */
	public PeerConfiguration(Object tenancyId) {
		super();
		tenancyId = tenancyId;
		addObjectParameter("onyx/tenancy-id", tenancyId);
	}

	/**
	 * Constructs a new PeerConfiguration object using an exsiting PeerConfiguration.
	 * Uses OnyxMap superconstructor.
	 * @param  PeerConfiguration cfg           existing PeerConfiguration object
	 * @return                   new PeerConfiguration object
	 */
	public PeerConfiguration(PeerConfiguration cfg) {
    		super(cfg.entry);
	}

	/**
	 * Constructs a new PeerConfiguration object using an exsiting content map
	 * from an exsiting peer configuration and a tenancyId.
	 * Uses OnyxMap superconstructor.
	 * @param Object tenancyId		a unique id used to identify the PeerConfiguration
	 * @param  PeerConfiguration cfg           existing PeerConfiguration object
	 * @return                   new PeerConfiguration object
	 */
	public PeerConfiguration(Object tenancyId, OnyxMap m) {
    		super(m);
		tenancyId = tenancyId;
		addObjectParameter("onyx/tenancy-id", tenancyId);
	}

	/**
	 * Returns the tenancyId associated with the PeerConfiguration object
	 * @return the tenancyId object
	 */
	public Object tenancyId() {
		return tenancyId;
	}
}
