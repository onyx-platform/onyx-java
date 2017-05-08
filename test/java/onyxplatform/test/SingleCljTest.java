package onyxplatform.test;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import clojure.lang.IPersistentMap;

import org.onyxplatform.api.java.OnyxNames;
import org.onyxplatform.api.java.OnyxMap;
import org.onyxplatform.api.java.Task;
import org.onyxplatform.api.java.utils.MapFns;

/**
 * SingleCljTest tests a Job running with a single clojure function.
 * All basic behavior is set up using the JobBuilder base class, while the
 * clojure method to be used is bound to the namespace within the constructor.
 * Note that any clojure function used in an onyx job task must be loaded
 * in the way demonstrated in this class.
 */
public class SingleCljTest extends JobBuilder implements OnyxNames {

    /**
     * Constructs the basic SingleCljTest class, binding the namespace containing
     * our test clojure function to the Job namespace.
     * @param  onyxEnvConfig A string representing the path to the EDN file containing the set up spec
     */
	public SingleCljTest(String onyxEnvConfig) {
	super(onyxEnvConfig, 5, 50);

	// Loads clj-dependencies used in this job
    	IFn require = Clojure.var(OnyxNames.CORE, OnyxNames.Require);
    	require.invoke(Clojure.read("onyx-java.test.single-cljfn.functions"));
	}

    /**
     * Adds the clojure test function to the job workflow
     */
	public void configureCatalog() {
		OnyxMap c = MapFns.fromResources("catalog-single-clj.edn");
		Task t = new Task(c);
		job.addCatalogTask(t);
	}
}
