package onyxplatform.test;

import clojure.lang.IPersistentMap;

import org.onyxplatform.api.java.Catalog;
import org.onyxplatform.api.java.instance.CatalogUtils;
import org.onyxplatform.api.java.utils.MapFns;

public class SingleJavaTest extends SingleFnTest {

    private String methodName;
    private IPersistentMap setupMap;

    public SingleJavaTest(String setupEdn){
        setupMap = MapFns.fromResources(setupEdn).toMap();
    }

    protected void createCatalog(){
        catalog = new Catalog();
		catalog = CatalogUtils.addMethod(catalog, "pass", batchSize,
								batchTimeout, methodName, MapFns.emptyMap());
        updateCatalog();
		System.out.println("Java Catalog Created: ");
		System.out.println(catalog.toString());
    }

    protected void setup(){
        setMethodName((String) MapFns.get(setupMap, "methodName"));
        defaultSetup(setupMap);
    }

    private void setMethodName(String methodNameString){
        methodName = methodNameString;
        System.out.println("Set method name: ");
        System.out.println(methodName);
    }

}
