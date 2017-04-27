(ns onyx-java.test.pure-java
  (:gen-class)
  (:import onyxplatform.test.SingleJavaTest)
  (:require [clojure.test :refer [deftest is]]))

(deftest single-java-test
    (let [j (SingleJavaTest. "java-test-setup.edn")
          inputs [{:pass-through "PASSTHROUGH"}]
          expected {:out [{:pass-through "PASSTHROUGH"} :done]}
          outputs (.runJob j [{:pass-through "PASSTHROUGH"}])]
        (is (= (first inputs) (first (:out outputs))))))
