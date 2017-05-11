package org.onyxplatform.api.java.instance;

import clojure.lang.IPersistentMap;
import clojure.lang.IFn;

import java.lang.reflect.Constructor;

/**
 * Loader is an internally used class that dynamically loads object instances
 * derived from user classes which extend OnyxFn.
 * This class should not be used by applications programmers using the Onyx-Java package.
 */
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
		System.out.println("Loader::findClass> fqClassName=" + fqClassName);
		if (cl == null) {
			return Class.forName(fqClassName);
		}
		else {

			return null;
		}
	}

	/**
	 * Returns an IFn representation of a dynamically loaded object instance derived
	 * from a user class extending OnyxFn.
	 * @param  fqClassName   The fully qualified classname of the class from which to derive an object instance
	 * @param  args          An IPersistentMap of constructor args to use in object instance creation
	 * @return                                             IFn representation of the object instance
	 * @throws ClassNotFoundException                      Class cannot be found
	 * @throws NoSuchMethodException                       Class doesnt have a proper constructor
	 * @throws InstantiationException                      Object cannot be instantiated do to any instantiation error
	 * @throws IllegalAccessException                      method or class definition was unavailable
	 * @throws java.lang.reflect.InvocationTargetException an abstracted error in the method call, unpack to see actual cause
	 */
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
