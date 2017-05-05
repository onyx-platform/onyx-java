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

## Build Status

 [![CircleCI](https://circleci.com/gh/RBerkheimer/onyx-java.svg?style=svg)](https://circleci.com/gh/RBerkheimer/onyx-java)

## Overview
Onyx-Java mirrors the Onyx Platform core API by providing a Java equivalent for each component of an Onyx workflow. This means the parts of Onyx such as Catalogs, Lifecycles, Jobs, Tasks, and so on, each have a new equivalent Java class.
<br><br>
These classes provide methods for adding entries to components, such as adding tasks to catalogs, edges to workflows, catalogs to jobs, etc.
<br><br>
<strong>
Please note:
</strong>
<br>
Onyx-Java does not validate semantic correctness of entries - parameter and type checking enforcement is left to Onyx Platform itself, which takes place at runtime.
<br>
This means that it is up to the user to refer to the Onyx Platform information model documentation to ensure that they are passing the expected parameters of types expected by the Onyx Platform.
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
Onyx-Java provides this ability in the utility class *MapFns*, which offers pure Java versions of useful Clojure map manipulation functions, such as get, getIn, assoc, dissoc, and others, making it easier to directly manipulate the arguments used by Onyx.
<br>
MapFns also provides support for loading edn files that contain map specifications, allowing the environment, peer configuration, and task specifications to be configured rather than coded. This promotes highly patterned design, allows high reuse, and guarantees efficiency.
<br><br>

#### Core Async

The Onyx Platform provides excellent support for asynchronous task execution via a core-async plugin.
<br>
Core-async jobs in Onyx are common and stereotyped, and setting them up generally includes rewriting boilerplate code.
<br><br>
Onyx-Java provides the ability to set up these common core-async jobs up automatically, via the utility classes *AsyncCatalog* and *AsyncLifecycles*. These two classes encapsulate generating the correct catalog and lifecycle entries for setting up, using, and collecting the resulting output from async channels. This utility provides a way for users to avoid writing boilerplate code in this common scenario.
<br><br>

#### Java Objects

Onyx-Java allows pure Java objects to be added to workflows and jobs as tasks by providing a simple abstract base class for user classes to extend and a utility class to add objects created by those user classes to a catalog.
<br><br>
The abstract base class *OnyxFn* takes a Clojure map as the single argument and provides an abstract method that consumes the map. This abstract method is overridden by the user class.
<br>
Object based on the user class can then be added to the job catalog using the *BindUtils*, which will create a new instance of the class at runtime. BindUtils also provides methods for releasing the instances once they are no longer needed.
<br><br>

## Package Use

Onyx-Java is designed to be used in a way that mirrors as closely as possible the traditional use of Onyx Platform while being implemented in a way that reflects Java convention. This makes it easy for Java users to quickly feel comfortable using the package without obfuscating the power of the Onyx Platform itself.
<br>
Just as in Onyx Platform, users have the responsibility to set up jobs and job components, initialize environment and peer configurations, and control job execution;
however, unlike Onyx Platform, Onyx-Java users can do this using an API written entirely in Java that can be implemented in a conventional Java way.
<br><br>

### Basic

This section outlines out a general approach for using Onyx-Java by providing a working bare-bones implementation.
<br>
More detailed examples can be found in the test documentation [here](docs/readme/expanded_use_example.md).
<br><br>

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

Inclusion of Java task objects in a workflow requires that you provide a concrete subclass of *OnyxFn*:
<br><br>

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
Then, using the fully qualified name of your class and any constructor parameters, you use *BindUtils* to generate a matching catalog entry:
<br><br>

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
This will add the appropriate entry to bind task processing to a specific instance of your class that is loaded and called at job runtime.
<br><br>
You are responsible for instance management as your instances are cached in memory. This is accomplished via the release static methods provided by *BindUtils*:
<br><br>

```
BindUtils.releaseInstances(job);
```

#### **NOTE**

There are parallel clojure namespaces and functions that provide affordances for clojure-based workflows;
<br>
i.e., Onyx-Java does not prohibit users from writing the system in Java while running tasks that are written in Clojure.
<br><br>


## License

Copyright © 2016 Distributed Masonry

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.<br>
<br>
