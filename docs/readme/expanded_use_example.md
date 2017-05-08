# Expanded Use Example

This section contains a more complete description of the underlying system, attempting
to provide the user with an idea on how the system can be used.
<br><br>
All tests in the package are run using leiningen, in the test/onyx_java/test directory.
<br><br>
This directory contains a namespace called pure_java.clj - all of the tests in this namespace
do nothing but wrap behavior contained in the test/java/onyxplatform/test directory (the onyxplatform.test package).
<br><br>
Opening the clj file shows several unit tests, covering all behaviors in the org.onyxplatform.api.java.API class.
Each test follows the same essential pattern - set up, run, and tear down.
<br><br>
One of the tests here is called single-java-test:
<br><br>
```
(deftest single-java-test
    (let [testObject (SingleJavaTest. "onyx-env.edn")
          inputs [{:pass-through "PASSTHROUGH"}]
          expected {:out [{:pass-through "PASSTHROUGH"} :done]}
          outputs (.runJobCollectOutputs testObject [{:pass-through "PASSTHROUGH"}])]
        (.shutdown testObject)
        (is (= (first inputs) (first (:out outputs))))))
```
<br>
In this test, we set up the SingleJavaTest object based on the onyxplatform.test.SingleJavaTest class:
<br><br>

```
package onyxplatform.test;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import clojure.lang.IPersistentMap;

import org.onyxplatform.api.java.Catalog;
import org.onyxplatform.api.java.Task;
import org.onyxplatform.api.java.utils.MapFns;

import org.onyxplatform.api.java.instance.BindUtils;

/**
 * SingleJavaTest tests a Job running with a single Java function, built
 * using the dynamic class loader. All basic behavior is set up using the
 * JobBuilder base class, while the pure Java object instance function is
 * added within this method itself.
 */
public class SingleJavaTest extends JobBuilder {

    /**
     * Constructs a simple Job test that can run a pure java function, loaded
     * from an EDN file which is passed as the only parameter.
     * @param  onyxEnvConfig path to the EDN file specifying how to set up the job
     */
	public SingleJavaTest(String onyxEnvConfig) {
	super(onyxEnvConfig, 5, 50);
	}

    /**
     * Adds an Object instance of the test function to the Job catalog
     */
	public void configureCatalog() {
		Catalog c = job.getCatalog();
		BindUtils.addFn(c, "pass",
				batchSize(), batchTimeout(),
				"onyxplatform.test.PassFn", MapFns.emptyMap());
	}
}
```

<br>
This class constructor takes a string path to an EDN file, which is a map that contains environment set-up conditions.
The contents of the EDN file look like this:
<br><br>

```
{
    :generateTenancyId true
    :peerEdn "dev-peer-config.edn"
    :envEdn "env-config.edn"
    :virtualPeerCount 3
    :taskScheduler "onyx.task-scheduler/balanced"
}
```

<br>
Note that the structure of the set-up is just a map containing keys used by the environment set-up, and the values they should be set to.
<br><br>
This is true in all cases except for the peerEdn and envEdn keywords, which point to their own EDN set-up maps; these maps are in turn unpacked by
a set-up method in <em>OnyxEnv</em>, contained in the extended base class <em>JobBuilder</em>. Note above that the <em>SingleJavaTest</em> class calls the <em>JobBuilder</em> super constructor.
This super constructor creates a new <em>OnyxEnv</em> object associated with the <em>JobBuilder</em>, as well as a new <em>Job</em>:
<br><br>

```
/**
 * Constructs a JobBuilder using an EDN configuration, a batchSize, and
 * a batchTimeout.
 * @param  onyxEnvConfig path to the EDN file containing the job set up spec
 * @param  batchSize     integer representing the number of segments tasks should consume at once
 * @param  batchTimeout  integer representing the maximum time (ms) a task should wait before beginning
 */
public JobBuilder(String onyxEnvConfig, int batchSize, int batchTimeout) {

    onyxEnv = new OnyxEnv("onyx-env.edn", true);

    this.batchSize = new Integer(batchSize);
    this.batchTimeout = new Integer(batchTimeout);
    job = createBaseJob();
}

/**
 * Adds asynchronous input/output channels and the appropriate test edges
 * for test functions
 * @return the created Job
 */
public Job createBaseJob() {

// Tests have a simple 1-fn core async backed
// workflow that share all bootstrapping with
// other tests. Generates all job entries excepting
// the actual fn catalog entity.
//


job = new Job(onyxEnv.taskScheduler());

job.addWorkflowEdge("in", "pass");
job.addWorkflowEdge("pass", "out");

Catalog c = job.getCatalog();
AsyncCatalog.addInput(c, "in", batchSize, batchTimeout);
AsyncCatalog.addOutput(c, "out", batchSize, batchTimeout);

Lifecycles lc = job.getLifecycles();
AsyncLifecycles.addInput(lc, "in");
AsyncLifecycles.addOutput(lc, "out");

return job;
}
```

<br>
Our *JobBuilder* class is a convenient aggregator class that is not part of the standard Onyx-Java package - but
it does make it obvious how a user could implement all of their Onyx-Java code in a centralized way.
<br><br>
In our example *JobBuilder*, the *Job* is assembled with automatically added Async inputs and outputs.
As seen above, the workflow edges are added, inputs are added, and outputs are added very easily using the proper API and helper classes and methods.
<br>
Our *JobBuilder* class provides an abstract method that needs to be overridden:
<br><br>

```
/**
 * Abstract method must be extended by extending subclass to add other
 * catalog entries to the JobBuilder catalog
 */
public abstract void configureCatalog();
```

<br>
This method is overridden by our *SingleJavaTest* class seen above, using the *BindUtils* utility class
to add a new catalog entry (which is based on our fully qualified pure Java example class, called "PassFn"):
<br><br>

```
package onyxplatform.test;

import clojure.lang.IPersistentMap;

import org.onyxplatform.api.java.instance.OnyxFn;

/**
 * PassFn is a simple test class extending OnyxFn which is used to test
 * a pure java object instance task in an Onyx Job.
 */
public class PassFn extends OnyxFn {

	/**
	 * Constructor overriding and calling the super constructor of OnyxFn.
	 * This must exist for every user Class which uses OnyxFn.
	 * @param  m       An IPersistentMap of constructor arguments
	 */
	public PassFn(IPersistentMap m) {
		super(m);
	}

	/**
	 * Extended abstract method from OnyxFn required by every subclass of OnyxFn.
	 * In this case, consumeSegment simply returns the map that's passed in -
	 * in the general use case, this method can do anything with the segment,
	 * as long as it always returns an IPersistentMap or PersistentVector of
	 * IPersistentMaps.
	 * @param  m             The IPersistentMap containing the input segment
	 * @return     an IPersistentMap or PersistentVector of IPersistentMaps containing method output
	 */
	public Object consumeSegment(IPersistentMap m) {
		return m;
	}
}
```

<br><br>
Notice that this test class extends *OnyxFn*, which is required when using pure Java objects with Onyx-Java.
Also notice the other requirements for user classes used as tasks -
<ul>
<li>The overridden constructor, which calls the *OnyxFn* super constructor</li>
<li>The constructor argument, which must be an IPersistentMap containing constructor arguments</li>
<li>The consumeSegment method, which overrides an abstract method from *OnyxFn*</li>
<li>consumeSegment must take an IPersistentMap and return either a PersistentVector of IPersistentMaps or a single IPersistentMap</li>
</ul>
<br>
In this simple case, consumeSegment simply passes through the input - in practice, this method can do literally anything with the input segments,
as long as it conforms to the input/output requirements. I.e., it can call other classes, create new/unused data, etc.
<br><br>
Going back to the single-java-test clojure test function, we see that the inputs (input segments) are passed as a PersistentVector. This is how *JobBuilder*
consumes input segments in its two job running methods. Both of these are similar - in this example, we are using the method that runs the job and collects outputs:
<br><br>

```
/**
 * Runs a job and collects the outputs, returning them inside an IPersistentMap.
 * @param  inputs    A PersistentVector of input segments to use for running the Job
 * @return          An IPersistentMap containing the outputs produced by the job
 */
public IPersistentMap runJobCollectOutputs(PersistentVector inputs) {
    IPersistentMap jmeta = runJob(inputs);
    return AsyncLifecycles.collectOutputs(job.getLifecycles(), "out");
}
```

<br>
Notice that in the 'expected' parameter that we are comparing these outputs, we are getting an IPersistentMap with an 'out' key.
This is because we are collecting the outputs associated with a channel that is supposed to be collecting outputs for the 'out' task.
The value for this task key contains two entries in a PersistentVector - the inputs which we expected to pass through, and an additional ':done'
keyword. This keyword is produced by the Onyx Platform itself, and is called a sentinel value. It is simply there to inform that all segments have
been processed and returned. It will always be present on a properly processed and gathered output, and thus should always be accounted for
when doing applications programming with Onyx-Java.
<br><br>
Finally in our single-java-test test method, note that we call a shutdown method on our *JobBuilder* class. This class simply shuts down the *OnyxEnv* object
associated with the *JobBuilder*, and should always be used when jobs are finished. In *JobBuilder* it looks like:
<br><br>

```
/**
 * Completely shuts down the environment associated with the JobBuilder
 */
public void shutdown() {
    onyxEnv.stopEnv();
}
```

<br>
Notice that in our onyxplatform.test package, we have a similar class to *SingleJavaTest*, called *SingleCljTest*, which also
extends our *JobBuilder* convenience class. this class demonstrates how to add and use a pure clojure function in an Onyx workflow using Onyx-Java.


<br><br>


## License

Copyright © 2016 Distributed Masonry

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.<br>
<br>
