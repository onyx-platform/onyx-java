package onyxplatform.test;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import clojure.lang.IPersistentMap;

import org.onyxplatform.api.java.Catalog;
import org.onyxplatform.api.java.Task;
import org.onyxplatform.api.java.utils.MapFns;

import org.onyxplatform.api.java.instance.BindUtils;

public class SingleJavaTest extends JobBuilder {

    	public SingleJavaTest(String onyxEnvConfig) {
		super(onyxEnvConfig, 5, 50);
    	}

	public void configureCatalog() {
		Catalog c = job.getCatalog();
		BindUtils.addFn(c, "pass", 
				batchSize(), batchTimeout(), 
				"onyxplatform.test.PassFn", MapFns.emptyMap());
	}
}
