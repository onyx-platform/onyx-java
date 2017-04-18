package org.onyxplatform.api.java;

import clojure.lang.IPersistentMap;
import java.util.Map;

/**
 * An EnvConfiguration (environment configuration) specifies how a development
 * environment should be initialized, which uses an in-memory implementation
 * of zookeeper.
 */
public class EnvConfiguration extends OnyxEntity
{
	protected static String coerceKw = EnvConfig;

		/**
		 * Calls empty superconstructor for OnyxEntity
		 * @return newly created EnvConfiguration object
		 */
    	public EnvConfiguration() {
    	}

		/**
		 * Calls OnyxEntity superconstructor with existing environment
		 * configuration.
		 * @param  EnvConfiguration cfg           existing environment
		 *                          		configuration to use for construction
		 * @return                  newly created EnvConfiguration object
		 */
    	private EnvConfiguration(EnvConfiguration cfg) {
	    	super(cfg.entry);
    	}

		/**
		 * Coerces the envConfig content map 'entry' into
		 * a proper onyx environment configuration.
		 * @param  Map<String, Object>       jMap content map to coerce
		 * @return             object representing onyx version of envConfig
		 */
    	protected IPersistentMap coerce(Map<String, Object> jMap) {
			return (IPersistentMap) castTypesFn.invoke(coerceKw, jMap);
    	}
}
