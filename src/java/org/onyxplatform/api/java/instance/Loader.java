package org.onyxplatform.api.java.instance;

import clojure.lang.IPersistentMap;
import clojure.lang.IFn;

import java.lang.reflect.Constructor;

public class Loader {

	public static IFn loadOnyxMethod(String fqClassName, IPersistentMap args) 
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

