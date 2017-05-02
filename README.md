# onyx-java

Onyx-java provides a simple Java interface for the Onyx Platform's core API, utilities for manipulating Clojure maps directly in Java, tools to simplify use of core.async plugins, and affordances for inclusion of pure Java classes in a workflow.   <br>
<br>

## Overview
Onyx-java follows the Onyx Platform's core API providing Java peer's for each aspect of a workflow. I.e. Catalogs, Lifecycles, Jobs, etc. <br>
<br>
These classes provide methods to add entries to your workflow description ensuring that they are converted into Clojure-native types when needed. Note that this approach doesn't validate semantic correctness of your entries, which are enforced at runtime.<br>
<br>
### Utilities

#### Onyx Environment 

Bootstrapping and use of the Onyx Platform involves multiple configuration dimensions which require carefully ordering calls during spin up and shutdown. Additionally, all core API calls require that this metadata be readily available. The management class *OnyxEnv* is provided to simplify management of the environment and well as providing proxying methods to encapsulate this complexity.

#### Maps

A utility class, MapFns, offers Java-esk versions of (some) Clojure map munipulation functions, like get-in, making it easier to directly manipulate Clojure map's. It also provides support for loading edn files of simple maps from resources making it easier to manage entries like environment and peer configuration. <br>
<br>

#### Core Async

Support for the use of core.async plugins are provided via a pair of Java classes. AsyncCatalog and AsyncLifecycles encapsulate generating the correct catalog and lifecycle entries, as well as providing support methods for use during job runtime to pass and collect data.<br>
<br>

#### Java Objects

Support for use of pure Java objects in a workflow is provided via an abstract base class, OnyxFn, along with a catalog generation tool BindUtils. <br>
<br>
*OnyxFn* is an abstract base class with a constructor that takes a Clojure map, and an abstract method that consumes the Clojure map segment. *BindUtils* provides a means to create an instance-aware catalog entry that calls your bootrapped derived instance at runtime.<br>
<br>

## Usage

This section is non-exhaustive but lays out the general approach giving a feel for use of the API in practice. Full examples are provided in test.<br>
<br>

### Basic

The use of onyx-java's core API, essentially, follows the same basic steps that construction of a pure Clojure Onyx Platform workflow does with minor differences due to onyx-java's approach.  <br>
<br>
The following example highlights basic usage. For more detail of API usage see *link-to-in-test-readme*. <br>

```
import clojure.lang.IPersistentMap;
import clojure.lang.PersistentVector;

import org.onyxplatform.api.java.OnyxEnv;
import org.onyxplatform.api.java.OnyxMap;
import org.onyxplatform.api.java.OnyxVector;
import org.onyxplatform.api.java.Job;
import org.onyxplatform.api.java.Catalog;
import org.onyxplatform.api.java.Lifecycles;

import org.onyxplatform.api.java.utils.AsyncCatalog;
import org.onyxplatform.api.java.utils.AsyncLifecycles;

import org.onyxplatform.api.java.instance.BindUtils;


// Configure and start the Onyx Platform runtime.
//
OnyxEnv onyxEnv = new OnyxEnv("onyx-env.edn", true);

// Constuct a simple single-function job that uses
// a pure Java object for segment processing backed
// by core.async.
//

Job job = new Job(onyxEnv.taskScheduler());

job.addWorkflowEdge("in", "pass");
job.addWorkflowEdge("pass", "out");

Catalog c = job.getCatalog();
int batchSize = 5;
int batchTimeout = 50;

// Add core.async plugin catalog entries
AsyncCatalog.addInput(c, "in", batchSize, batchTimeout);
AsyncCatalog.addOutput(c, "out", batchSize, batchTimeout);

// Use the fully-qualified onyxplatform.test.PassFn class
//
BindUtils.addFn(c, "pass", batchSize, batchTimeout,
                   "onyxplatform.test.PassFn", MapFns.emptyMap());

// Add accompanying core.async plugin lifecycles
Lifecycles lc = job.getLifecycles();
AsyncLifecycles.addInput(lc, "in");
AsyncLifecycles.addOutput(lc, "out");

OnyxMap m = new OnyxMap().addKeywordParameter("test-input", "TEST");
OnyxVector inputs = new OnyxVector().addElement(m);

IPersistentMap job-meta = onyxEnv.submitAsyncJob(job, inputs);
PersistentVector output = AsyncLifecycles.collectOutputs(job, "out");

// Release the cached instance
BindUtils.releaseInstances(job);
```


### Java Objects

Inclusion of Java task objects in a workflow requires that you provide a concrete subclass of *OnyxFn*:<br>
<br>

```
package onyxplatform.test;

import clojure.lang.IPersistentMap;

import org.onyxplatform.api.java.instance.OnyxFn;

public class PassFn extends OnyxFn {

        public PassFn(IPersistentMap m) {
                super(m);
        }

        public Object consumeSegment(IPersistentMap m) {
                return m;
        }
}
```

<br>
Then, using the fully qualified name of your class and any constructor parameters, you use *BindUtils* to generate a matching catalog entry:<br>
<br>

```
import org.onyxplatform.api.java.instance.BindUtils;

Catalog catalog = new Catalog();

String taskName = "pass";
String fullyQualifiedName = "onyxplatform.test.PassFn";
IPersistentMap ctrArgs = MapFns.emptyMap();

int batchSize = 5;
int batchTimeout = 50;

BindUtils.addFn(catalog, batchSize, batchTimeout, fullyQualifiedName, ctrArgs);
```

<br>
This will add the appropriate entry to bind task processing to a specific instance of your class that is loaded and called at job runtime.<br>
<br>
You are responsible for instance management as your instances are cached in memory. This is accomplished via the release static methods provided by *BindUtils*:<br>
<br>

```
BindUtils.releaseInstances(job);
```

#### **NOTE**

There are parallel clojure namespaces and functions that provide affordances for clojure-based workflows. <br>
<br>


## License

Copyright © 2016 Distributed Masonry

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.<br>
<br>
