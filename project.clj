(defproject onyx-java "0.9.15-SNAPSHOT"
  :license {:name "Eclipse Public License"
            :url "https://github.com/onyx-platform/onyx-java"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/core.async "0.2.371"]
                 [clj-stacktrace "0.2.8"]
                 [org.onyxplatform/onyx "0.9.15"]
                 [com.stuartsierra/component "0.2.3"]]
  :main  ^:skip-aot onyx-java.main
  :source-paths ["src/clj" "test"]
  :java-source-paths ["src/java" "test/java"]
  :javac-options ["-target" "1.8" "-source" "1.8" "-Xlint:unchecked"]
  :resource-paths ["resources"]
;  :plugins  [[lein-virgil "0.1.6"]]
  :repl-options {:init-ns onyx-java.main
                 :caught clj-stacktrace.repl/pst+ }
  :profiles {:uberjar {:aot :all}
             :dev {:dependencies [[org.clojure/tools.namespace "0.2.11"]]
                   :plugins [[lein-update-dependency "0.1.2"]
                             [lein-pprint "1.1.1"]
                             [lein-set-version "0.4.1"]] }})
