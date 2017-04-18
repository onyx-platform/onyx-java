(defproject onyx-java "0.9.15-SNAPSHOT"
  :license {:name "Eclipse Public License"
            :url "https://github.com/onyx-platform/onyx-java"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/core.async "0.2.371"]
                 [clj-stacktrace "0.2.8"]
                 [org.onyxplatform/onyx "0.9.15"]
                 [com.stuartsierra/component "0.2.3"]]
  :source-paths ["src/clj"]
  :java-source-paths ["src/java"]
  :javac-options ["-target" "1.8" "-source" "1.8" "-Xlint:-options"]
  :resource-paths ["test/resources"]
  :repl-options {:init-ns onyx-java.main
                 :caught clj-stacktrace.repl/pst+ }
  :profiles {:uberjar {:aot :all}
             :dev {:dependencies [[org.clojure/tools.namespace "0.2.10"]]
                   :plugins [[lein-update-dependency "0.1.2"]
                             [lein-pprint "1.1.1"]
                             [lein-set-version "0.4.1"]] }})
