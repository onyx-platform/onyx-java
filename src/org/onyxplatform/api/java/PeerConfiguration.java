package org.onyxplatform.api.java;

import clojure.lang.PersistentHashMap;
import java.util.Map;

/**
 * A peer configuration specifies the set of system parameters that
 * will be used for all peers on a given machine.
 * PeerConfiguration derives from OnyxEntity.
 */
public class PeerConfiguration extends OnyxEntity
{
	protected static String coerceKw = PeerConfig;

	/**
	 * Creates a new PeerConfiguration object using OnyxEntity superconstructor.
	 * @return new PeerConfiguration object
	 */
	public PeerConfiguration () {
	}

	/**
	 * Creates a new PeerConfiguration object using an exsiting content map
	 * from an exsiting peer configuration. Uses OnyxEntity superconstructor.
	 * @param  PeerConfiguration cfg           existing PeerConfiguration object
	 * @return                   new PeerConfiguration object
	 */
	private PeerConfiguration(PeerConfiguration cfg) {
    	super(cfg.entry);
	}

	/**
	 * Coerces PeerConfiguration object content map into
	 * proper onyx PeerConfiguration. Returns the onyx representation without
	 * altering the existing content map.
	 * @param  Map<String, Object>       jMap Content map to coerce
	 * @return             onyx representation of content map
	 */
	protected PersistentHashMap coerce(Map<String, Object> jMap) {
		return (PersistentHashMap) castTypesFn.invoke(coerceKw, jMap);
	}
}
