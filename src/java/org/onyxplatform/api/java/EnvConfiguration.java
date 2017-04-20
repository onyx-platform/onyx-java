package org.onyxplatform.api.java;

/**
 * An EnvConfiguration (environment configuration) specifies how a development
 * environment should be initialized, which uses an in-memory implementation
 * of zookeeper.
 */
public class EnvConfiguration extends OnyxMap
{
	private Object tenancyId;

	/**
	* Calls empty superconstructor for OnyxMap
	* @return newly created EnvConfiguration object
	*/
    	public EnvConfiguration(Object tenancyId) {
		super();
		tenancyId = tenancyId;
		addObjectParameter("onyx/tenancy-id", tenancyId);
    	}

	/**
	* Calls OnyxMap superconstructor with existing environment
	* configuration.
	* @param  EnvConfiguration cfg           existing environment
	*                          		configuration to use for construction
	* @return                  newly created EnvConfiguration object
	*/
    	public EnvConfiguration(EnvConfiguration cfg) {
	    	super(cfg.entry);
    	}

	public EnvConfiguration(OnyxMap e) {
		super(e);
	}
}
