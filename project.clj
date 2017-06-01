(defproject org.onyxplatform/onyx-java "0.1.0-SNAPSHOT"
  :license {:name "Eclipse Public License"
            :url "https://github.com/onyx-platform/onyx-java"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/core.async "0.2.371"]
                 [clj-stacktrace "0.2.8"]
                 [org.onyxplatform/onyx "0.9.15"]
                 [com.stuartsierra/component "0.2.3"]
                 [com.taoensso/timbre "4.10.0"]]
  :source-paths ["src/clj" "test"]
  :java-source-paths ["src/java" "test/java"]
  :javac-options ["-target" "1.8" "-source" "1.8" "-Xlint:unchecked"]
  :resource-paths ["resources" "test/resources"]
  :repl-options {:init-ns onyx-java.main
                 :caught clj-stacktrace.repl/pst+ }
  :profiles {:uberjar {:aot :all}
             :dev {:dependencies [[org.clojure/tools.namespace "0.2.11"]]
                   :plugins [[lein-update-dependency "0.1.2"]
                             [lein-pprint "1.1.1"]
                             [lein-set-version "0.4.1"]
                             [lein-javadoc "0.3.0"]]
                    :javadoc-opts {:package-names
                                    ["org.onyxplatform.api.java.instance"
                                     "org.onyxplatform.api.java.utils"
                                     "org.onyxplatform.api.java"
                                     "onyxplatform.test"]
                                    :output-dir "docs"}}})
