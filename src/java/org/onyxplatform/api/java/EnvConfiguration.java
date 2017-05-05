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
	* Constructs a new EnvConfiguration object using the passed tenancyId object.
	* Uses the OnyxMap superconstructor.
	* @param tenancyId a unique id object used to identify the EnvConfiguration
	*/
    	public EnvConfiguration(Object tenancyId) {
		super();
		tenancyId = tenancyId;
		addObjectParameter("onyx/tenancy-id", tenancyId);
    	}

	/**
	* Constructs a new EnvConfiguration object based on an existing EnvConfiguration
	* object.
	* Uses the OnyxMap superconstructor.
	* @param  cfg     existing EnvConfiguration object to use for construction
	*/
    	public EnvConfiguration(EnvConfiguration cfg) {
	    	super(cfg.entry);
    	}

	/**
	 * Constructs a new EnvConfiguration based on the specified tenancyId object
	 * and an OnyxMap containing the EnvConfiguration specification.
	 * Uses the OnyxMap superconstructor.
	 * @param  tenancyId     a unique id object used to identify the EnvConfiguration
	 * @param  e     an OnyxMap containing keyword value pairs used to specify the EnvConfiguration
	 */
	public EnvConfiguration(Object tenancyId, OnyxMap e) {
		super(e);
		tenancyId = tenancyId;
		addObjectParameter("onyx/tenancy-id", tenancyId);
	}

	/**
	 * Returns the tenancyId object currently associated with the EnvConfiguration object.
	 * @return tenancyId object
	 */
	public Object tenancyId() {
		return tenancyId;
	}
}
