package org.onyxplatform.api.java.instance;

import clojure.lang.IPersistentMap;
import clojure.lang.IFn;

import java.lang.reflect.Constructor;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.DataInputStream;

import org.onyxplatform.api.java.OnyxMap;
import org.onyxplatform.api.java.utils.MapFns;

/**
* Loader is a basic custom class loader used by BindUtils during 
* instance creation ensuring that the OnyxFn class (and its derived classes) 
* will be garbage collected when the instance is released. 
*
* This is to ensure that the class will be unloaded from memory along
* with any native library loaded into it. If this is not done then the
* default system class loader will always have a reachable reference to
* any class loaded preventing memory reclimation of underlying native
* libraries.
*/
public class Loader extends ClassLoader {
	
	protected OnyxMap cache = new OnyxMap();

	public Loader(ClassLoader parent) {
		super(parent);
	}


	public Class loadOnyxFnClass(String name) 
		throws ClassNotFoundException, java.lang.SecurityException
	{
		return loadClass(name);
	}


	@Override
	public Class loadClass(String n) 
		throws ClassNotFoundException, java.lang.SecurityException
	{
		if (n.startsWith("java.") || n.startsWith("clojure.")) {

			return super.loadClass(n);

		} else if (MapFns.contains(cache, n)) {

			return (Class)MapFns.get(cache, n);

		} else {

			// Convert '.' to '/'
			String file = n.replace('.', File.separatorChar) + ".class";
			byte[] b = null;
			try {
			//	System.out.println("Loader::loadClass> file=" + file);
				b = loadClassData(file);
				Class c = defineClass(n, b, 0, b.length);
				resolveClass(c);
				System.out.println("Loader::loadClass> resolved c=" + c);
				cache = MapFns.assoc(cache, n, c);
				return c;
	
			} catch (IOException e) {
				System.out.println("Loader::loadClass> IOEXCEPTION! n=" + n);
				e.printStackTrace();
				return null;
			}
		}	
	}

	private byte[] loadClassData(String name) 
		throws IOException 
	{
		InputStream stream = getClass().getClassLoader().getResourceAsStream(name);

		int size = stream.available();
		byte buff[] = new byte[size];
		DataInputStream in = new DataInputStream(stream);
		in.readFully(buff);
		in.close();
		return buff;
	}

	public void clearCache() {
		cache = new OnyxMap();
	}
}
