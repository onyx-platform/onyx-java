package onyxplatform.test;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import clojure.lang.IPersistentMap;

import org.onyxplatform.api.java.OnyxNames;
import org.onyxplatform.api.java.OnyxMap;
import org.onyxplatform.api.java.Task;
import org.onyxplatform.api.java.utils.MapFns;

public class SingleCljTest extends JobBuilder implements OnyxNames {

    	public SingleCljTest(String onyxEnvConfig) {
		super(onyxEnvConfig, 5, 50);

		// Loads clj-dependencies used in this job
        	IFn require = Clojure.var(OnyxNames.CORE, OnyxNames.Require);
        	require.invoke(Clojure.read("onyx-java.test.single-cljfn.functions"));
    	}

	public void configureCatalog() {
		OnyxMap c = MapFns.fromResources("catalog-single-clj.edn");
		Task t = new Task(c);
		job.addCatalogTask(t);
	}
}
