package org.onyxplatform.api.java.instance;

import clojure.lang.IPersistentMap;
import clojure.lang.IFn;

import java.lang.reflect.Constructor;

public class Loader {

	// Custom Class Loader ----------------------------------------
	//
	// We use a custom class loader so that 
	// the class will be garbage collected 
	// when its no longer referenced. 
	//
	// Otherwise the class will remain in 
	// memory and any native library 
	// loaded into it will never be released.
	//

	// TODO: Add releaseClasses method to custom class loader
	//       which nukes the Class cache.
	//

	public static Class<?> findClass(ClassLoader cl, String fqClassName) 
		throws ClassNotFoundException
	{
		if (cl == null) {
			return Class.forName(fqClassName);
		}
		else {

			return null;
		}
	}

	public static IFn loadOnyxFn(String fqClassName, IPersistentMap args) 
		throws ClassNotFoundException, 
		NoSuchMethodException, 
		InstantiationException,
		IllegalAccessException,
		java.lang.reflect.InvocationTargetException
	{
		// Use the environment-wide default class loader for 
		// general classes we want permanently cached.
	        Class<?> ipmClazz = findClass(null, "clojure.lang.IPersistentMap");

		// Use a custom class loader for instances we want to 
		// unload during gc.
		ClassLoader loader = null;
		Class<?> ifnClazz = findClass(loader, fqClassName);

		Constructor ctr = ifnClazz.getConstructor(new Class[] { ipmClazz });
		OnyxFn instance = (OnyxFn)ctr.newInstance(new Object[] { args });
		instance.setClassLoader(loader);
		return (IFn)instance;
	}
}

