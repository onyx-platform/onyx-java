package org.onyxplatform.api.java;

import clojure.lang.PersistentHashMap;
import java.util.Map;

public class EnvConfiguration extends OnyxEntity
{
	protected static String coerceKw = "env-config";

		/**
		 * Calls empty superconstructor for OnyxEntity
		 * @return newly created EnvConfiguration object
		 */
    	public EnvConfiguration () {
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
    	protected PersistentHashMap coerce(Map<String, Object> jMap) {
			return (PersistentHashMap) castTypesFn.invoke(coerceKw, jMap);
    	}
}
