package org.onyxplatform.api.java;

public class FlowConditions 
	extends OnyxVector
{
    public FlowConditions() {
	    super();
    }
    
    private FlowConditions(FlowConditions fc) {
	    super (fc.vContents);
    }
    
    public void addCondition(FlowCondition ent) {
	    addElement( ent );
    }
}
