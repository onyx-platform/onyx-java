# onyx-java

Onyx Java provides a simple Java interface for the Onyx Platform's core API, utilities for maniplating Clojure maps directly in Java, tools to simplify use of core.async plugins, and affordances for inclusion of pure Java classes in a workflow.   

 Overview 
The Onyx-Java API follows the Onyx Platform's core API providing Java peer's for each aspect. I.e. Catalogs, Lifecycles, Jobs, etc.
These classes provide methods to add declarations to your Job description ensuring that they are converted into Clojure-native types when needed.  Note that this approach doesn't enforce semantic correctness of your declarations, which are enforced at runtime.

 Basic Usage


 Pure Java Objects in a Worflow


 License

Copyright © 2016 Distributed Masonry

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.


NOTE: This repo is under active development and not ready for actual usage yet.

