# onyx-java

Onyx-Java provides a simple Java interface for the Onyx Platform's core API, utilities for manipulating Clojure maps directly in Java, tools to simplify use of core.async plugins, and affordances for inclusion of pure Java classes in a workflow.   <br>
<br>

## Overview
Onyx-Java follows the Onyx Platform's core API providing Java peer's for each aspect of a workflow. I.e. Catalogs, Lifecycles, Jobs, etc. <br>
<br>
These classes provide methods to add entries to your workflow description ensuring that they are converted into Clojure-native types when needed. Note that this approach doesn't validate semantic correctness of your entries, which are enforced at runtime.<br>
<br>
### Utilities

#### Maps

A utility class, MapFns, offers Java-esk versions of (some) Clojure map munipulation functions, like get-in, making it easier to directly manipulate Clojure map's. It also provides support for loading edn files of simple maps from resources making it easier to manage entries like environment and peer configuration. <br>
<br>

#### Core Async

Support for the use of core.async plugins are provided via a pair of Java classes. AsyncCatalog and AsyncLifecycles encapsulate generating the correct catalog and lifecycle entries, as well as providing support methods for use during job runtime to pass and collect data.<br>
<br>

#### Java Objects

Support for use of pure Java objects in a workflow is provided via an abstract base class, OnyxMethod, along with a catalog generation tool CatalogUtils. <br>
<br>
*OnyxMethod* is an abstract base class with a constructor that takes a Clojure map, and an abstract method that consumes the Clojure map segment. *CatalogUtils* provides a means to create an instance-aware catalog entry that calls your bootrapped derived instance at runtime.<br>
<br>

## Usage

This section is non-exhaustive but lays out the general approach giving a feel for use of the API in practice. Full examples are provided in test.<br>
<br>

### Basic

Basic usage of this package is demonstrated in the two classes related to Java testing, SingleFnTest.java
and SingleJavaTest.java. The defaultSetup method in SingleFnTest lays out individual steps line by line.
The executeJob method in SingleFnTest lays out starting an environment, running a job, and returning
results, line by line. The ShutdownEnv step describes line by line how to cleanly exit the environment.
The runJob method lays out the highest level of job execution, dictating how to handle exceptions.
<br>
Note that in the example SingleFnTest and SingleJavaTest are split into separate classes so that
other types of functions (such as pure clojure functions) can be run using the same base class -
the behavior in SingleFnTest never changes, no matter what language the tasks are written in.
The SingleJavaTest package contains logic specifically dealing with Tasks written in Java - particularly,
the CatalogUtils call in createCatalog is specific to Java tasks.
<br>
This represents an important delineation of the capabilities provided by OnyxJava - it wraps Onyx,
allowing for it to be used from Java, provides  for running tasks based on Java objects (described in the next section), without preventing the ability to create tasks written in other languages.
<br>

### Java Objects

Inclusion of Java task objects in a workflow requires that you provide a concrete subclass of OnyxMethod:<br>
<br>

```
package onyxplatform.test;

import clojure.lang.IPersistentMap;

import org.onyxplatform.api.java.instance.OnyxMethod;

public class PassMethod extends OnyxMethod {

        public PassMethod(IPersistentMap m) {
                super(m);
        }

        public Object consumeSegment(IPersistentMap m) {
                return m;
        }
}
```

<br>
Then, using the fully qualified name of your class and any constructor parameters, you use CatalogUtils to generate a matching catalog entry:<br>
<br>

```
	Catalog catalog = new Catalog();

	String taskName = "pass";
	String fullyQualifiedName = "onyxplatform.test.PassMethod";
	IPersitentMap ctrArgs = MapFns.emptyMap();

	int batchSize = 5;
	int batchTimeout = 50;

	CatalogUtils.addMethod(catalog, batchSize, batchTimeout, fullyQualifiedName, ctrArgs);
```

<br>
This will add the appropriate entry to bind task processing to a specific instance of your class that is loaded and called at job runtime.<br>
<br>

#### NOTE: There are parallel clojure namespaces and functions that provide affordances for clojure-based workflows.


## License

Copyright © 2016 Distributed Masonry

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.


#### NOTE: This repo is under active development and not ready for actual usage yet.
