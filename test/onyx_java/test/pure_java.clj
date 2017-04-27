(ns onyx-java.test.pure-java
  (:gen-class)
  (:import onyxplatform.test.SingleJavaTest)
  (:require [clojure.test :refer [deftest is]]))

(deftest testing
    (let [j (SingleJavaTest. "java-test-setup.edn")
          inputs [{:pass-through "PASSTHROUGH"}]
          expected {:out [{:pass-through "PASSTHROUGH"} :done]}
          outputs (.runTest j [{:pass-through "PASSTHROUGH"}])]
        (is (= (first inputs) (first (:out outputs))))))
