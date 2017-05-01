package onyxplatform.test;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import clojure.lang.IPersistentMap;

import org.onyxplatform.api.java.Task;
import org.onyxplatform.api.java.utils.MapFns;

public class SingleCljTest extends JobBuilder {

	static {
		// Loads clj-dependent libs
        	IFn require = Clojure.var(OnyxNames.CORE, OnyxNames.Require);
        	require.invoke(Clojure.read((String) MapFns.get(setupMap, "namespace")));
	}

    	public SingleCljTest(String onyxEnvConfig) {
		super(onyxEnvConfig);
    	}

	public void configureCatalog() {
		OnyxMap c = MapFns.fromResource("catalog-single-clj.edn");
		Task t = new Task(c);
		job.addCatalogTask(t);
	}
}
