package org.onyxplatform.api.java.instance;

import clojure.lang.IPersistentMap;
import clojure.lang.IFn;

import java.lang.reflect.Constructor;

public class Loader {

	// TODO: Create a custom class loader so that 
	//       the class will be garbage collected 
	//       when its no longer referenced. 
	//
	//       Otherwise the class will remain in 
	//       memory and any native library 
	//       loaded into it will not be unloaded.
	//
	public static IFn loadOnyxFn(String fqClassName, IPersistentMap args) 
		throws ClassNotFoundException, 
		NoSuchMethodException, 
		InstantiationException,
		IllegalAccessException,
		java.lang.reflect.InvocationTargetException
	{
		Class<?> ifnClazz = Class.forName(fqClassName);
	        Class<?> ipmClazz = Class.forName("clojure.lang.IPersistentMap");	
		Constructor ctr = ifnClazz.getConstructor(new Class[] { ipmClazz });
		IFn instance = (IFn)ctr.newInstance(new Object[] { args });
		return instance;
	}
}

