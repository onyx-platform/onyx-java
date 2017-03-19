package org.onyxplatform.api.java;

public class Lifecycles 
	extends OnyxVector
{
    public Lifecycles() {
	    super();
    }
    
    private Lifecycles(Lifecycles ls) {
	    super( ls.vContents );
    }
    
    public void addCall(Lifecycle cs) {
	    super.vContents = super.vContents.cons( cs );
    }
}
