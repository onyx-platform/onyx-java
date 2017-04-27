(ns onyx-java.test.pure-java
  (:gen-class)
  (:import [onyxplatform.test SingleJavaTest SingleCljTest])
  (:require [clojure.test :refer [deftest is]]))

(deftest single-java-test
    (let [testObject (SingleJavaTest. "single-java-test.edn")
          inputs [{:pass-through "PASSTHROUGH"}]
          expected {:out [{:pass-through "PASSTHROUGH"} :done]}
          outputs (.run testObject [{:pass-through "PASSTHROUGH"}])]
        (is (= (first inputs) (first (:out outputs))))))

(deftest single-clj-test
    (let [testObject (SingleCljTest. "single-clj-test.edn")
          inputs [{:pass-through "PASSTHROUGH"}]
          expected {:out [{:pass-through "PASSTHROUGH"} :done]}
          outputs (.run testObject [{:pass-through "PASSTHROUGH"}])]
        (is (= (first inputs) (first (:out outputs))))))

(deftest kill-test
    (let [testObject (SingleJavaTest. "single-java-test.edn")
          inputs [{:pass-through "PASSTHROUGH"}]
          expected true
          outputs (.kill testObject [{:pass-through "PASSTHROUGH"}])]
        (is (= expected outputs))))

(deftest await-test
    (let [testObject (SingleJavaTest. "single-java-test.edn")
          inputs [{:pass-through "PASSTHROUGH"}]
          expected "Success!"
          outputs (.await testObject [{:pass-through "PASSTHROUGH"}])]
        (is (= expected outputs))))
