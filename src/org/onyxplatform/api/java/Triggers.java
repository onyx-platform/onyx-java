package org.onyxplatform.api.java;

public class Triggers 
	extends OnyxVector
{
    public Triggers() {
	    super();
    }
    
    private Triggers(Triggers ts) {
	    super (ts.vContents);
    }
    
    public void addTrigger(Trigger te) {
	    addElement( te );
    }
}
