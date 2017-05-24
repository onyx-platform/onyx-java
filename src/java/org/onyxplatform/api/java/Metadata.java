package org.onyxplatform.api.java;

public class Metadata extends OnyxMap
{
	/**
	 * Constructs a new Lifecycle object using OnyxMap superconstructor.
	 */
	public Metadata() {
		super();
	}

	/**
	 * Constructs a new Metadata object using an existing Metadata object.
	 * Uses OnyxMap superconstructor.
	 * @param  m             existing Metadata object to use for new Metdata
	 */
	public Metadata(Metadata m) {
    		super(m.entry);
	}

	/**
	 * Constructs a new Metadata object using an existing content map.
	 * Uses OnyxMap superconstructor.
	 * @param   e             existing OnyxMap to use for new Metadata
	 */
	public Metadata(OnyxMap e) {
		super(e);
	}

}
