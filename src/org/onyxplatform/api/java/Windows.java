package org.onyxplatform.api.java;

public class Windows 
	extends OnyxVector
{
    public Windows() {
	    super();
    }
    
    private Windows(Windows wes) {
	    super( wes.vContents );
    }
    
    public void addWindow(Window w) {
	    addElement( w );
    }
}
