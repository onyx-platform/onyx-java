package onyxplatform.test;

import java.util.HashMap;

import clojure.lang.IPersistentMap;
import clojure.lang.PersistentHashMap;
import clojure.lang.PersistentVector;

import org.onyxplatform.api.java.OnyxNames;
import org.onyxplatform.api.java.OnyxMap;
import org.onyxplatform.api.java.Catalog;

import org.onyxplatform.api.java.instance.CatalogUtils;

import org.onyxplatform.api.java.utils.MapFns;

public class SingleJavaTest extends SingleFnTest implements OnyxNames{

    public String methodName;
    public IPersistentMap setupMap;

    public SingleJavaTest(String setupEdn){
        setupMap = MapFns.fromResources(setupEdn).toMap();
    }

    public void createCatalog(){
        catalog = new Catalog();
		catalog = CatalogUtils.addMethod(catalog, "pass", batchSize,
								batchTimeout, methodName, PersistentHashMap.EMPTY);
		System.out.println("Java Catalog Created: ");
		System.out.println(catalog.toString());
    }

    public void setup(){
        setMethodName((String) MapFns.get(setupMap, "methodName"));
        defaultSetup(setupMap);
    }

    public void setMethodName(String methodNameString){
        methodName = methodNameString;
        System.out.println("Set method name: ");
        System.out.println(methodName);

    }

}
