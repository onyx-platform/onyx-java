package org.onyxplatform.api.java;

public class Catalog 
	extends OnyxVector
{
    public Catalog() {
	    super();
    }
    
    private Catalog(Catalog c) {
	    super(c.vContents);
    }
    
    public void addTask(Task task) {
	    addElement( task );
    }
}
