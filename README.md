# onyx-java

Onyx-Java provides a simple Java interface for the Onyx Platform's core API, utilities for manipulating Clojure maps directly in Java, tools to simplify use of core.async plugins, and affordances for inclusion of pure Java classes in a workflow.   <br>
<br>

## Overview 
Onyx-Java follows the Onyx Platform's core API providing Java peer's for each aspect of a workflow. I.e. Catalogs, Lifecycles, Jobs, etc. <br>
<br>
These classes provide methods to add descriptions to your Job description ensuring that they are converted into Clojure-native types when needed. Note that this approach doesn't validate semantic correctness of your assets, which are enforced at runtime.<br>
<br>
### Utilities

#### Maps

A utility class, MapFns, offers Java-esk versions of (some) Clojure map munipulation functions, like get-in, making it easier to directly manipulate Clojure map's. It also provides support for loading from resources edn files that contain simple maps making it easier to manage asset descriptions like environment and peer configuration. <br>
<br>

#### Core Async

Support for the use of core.async plugin's is provided via a pair of Java classes. AsyncCatalog and AsyncLifecycles encapsulate generating the correct catalog and lifecycle entries, as well as providing support methods to for use during job runtime to pass and collect data.<br>
<br>


#### Java Objects



## Basic Usage


## Java Objects 


## License

Copyright © 2016 Distributed Masonry

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.


#### NOTE: This repo is under active development and not ready for actual usage yet.

