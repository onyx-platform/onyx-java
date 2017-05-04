# Onyx-Java

Onyx-Java is designed to allow users who write Java code to use the Onyx Platform.
<br>
The package provides the following features:
<ul>
<li>A simple Java interface for the Onyx Platform core API</li>
<li>Utilities for manipulating Clojure maps directly in Java</li>
<li>Tools to simplify use of core.async plugins</li>
<li>Affordances for inclusion of pure Java classes in a workflow</li>
</ul>
<br>

## Overview
Onyx-Java mirrors the Onyx Platform core API by providing a Java equivalent for each component of an Onyx workflow. This means the parts of Onyx such as Catalogs, Lifecycles, Jobs, Tasks, and so on, each have a new equivalent Java class.
<br><br>
These classes provide methods for adding entries to components, such as adding tasks to catalogs, edges to workflows, catalogs to jobs, etc.
<br><br>
<strong>Please note:
<br>
This approach does not validate semantic correctness of entries.
<br>
This means that it is up to the user to refer to the Onyx Platform documentation to ensure that  Onyx-Java must refer to the Onyx Platform documentation to ensure they are passing parameters that are expected by the Onyx Platform as this enforcement is left to the Platform itself to take care of at runtime.</strong>
<br><br>
### Utilities

Onyx-Java provides the following utilities for high-level package consumption:

#### Onyx Environment

The Onyx Platform is powerful, but using it can be complex - the platform allows for multiple configuration options, needs to make several calls during spin up and shutdown, and requires that persistent peer and environment metadata is available to core API calls, such as job execution.
<br>
Onyx-Java provides a utility class called *OnyxEnv* to simplify management of the environment and provide proxying methods to encapsulate this complexity. It provides convenience functions for setting up, controlling, and using the Onyx environment and the jobs that the environment contains.

#### Maps

The native language of the Onyx Platform is Clojure, and the ubiquitous data structure that it uses for all communication is an implementation of the IPersistentMap. While Java can manipulate these maps directly, it is often easier or preferable to use tools that mirror the natural way Onyx is used to using for argument manipulation.
<br>
Onyx-Java provides this ability in the utility class MapFns, which offers pure Java versions of useful Clojure map manipulation functions, such as get, getIn, assoc, dissoc, and others, making it easier to directly manipulate the arguments used by Onyx.
<br>
MapFns also provides support for loading edn files that contain map specifications, allowing the environment, peer configuration, and task specifications to be configured rather than coded. This promotes highly patterned design, allows high reuse, and guarantees efficiency.
<br><br>

#### Core Async
The Onyx Platform provides excellent support for asynchronous task execution via a core-async plugin. Core-async jobs in Onyx are common and stereotyped, and setting them up includes lots of reused boilerplate code.
<br>
Onyx-Java provides the ability to setup these common core-async jobs up automatically via the utility classes AsyncCatalog and AsyncLifecycles. These two classes encapsulate generating the correct catalog and lifecycle entries for setting up, using, and collecting the resulting output from async channels.
<br><br>

#### Java Objects

Support for use of pure Java objects in a workflow is provided via an abstract base class, OnyxFn, along with a catalog generation tool BindUtils. <br>
<br>
*OnyxFn* is an abstract base class with a constructor that takes a Clojure map, and an abstract method that consumes the Clojure map segment. *BindUtils* provides a means to create an instance-aware catalog entry that calls your bootstrapped derived instance at runtime.<br>
<br>

## Usage

This section is non-exhaustive but lays out the general approach giving a feel for use of the API in practice. Full examples are provided in test.<br>
<br>

### Basic

The use of Onyx-Java's core API, essentially, follows the same basic steps that construction of a pure Clojure Onyx Platform workflow does with minor differences due to onyx-java's approach.  <br>
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
