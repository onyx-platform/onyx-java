(ns onyx-java.test
    (:gen-class)
    (:require [onyx-java.utils.test-template :as t]
              [clojure.test :refer [deftest is]]))

(def input-dir "test/resources/onyx_simple/source/")
(def output-dir "test/resources/onyx_simple/target/")

(deftest entity-coercion-test
    (let [input input-dir
          output output-dir
          master-map (t/create-master-map input)
          params (t/add-entity-params master-map input)
          coerced-map (t/coerce-entities master-map)
          expected-map (t/get-expected-entities master-map output)]
    (is (= coerced-map expected-map))))

(deftest vector-coercion-test
    (let [input input-dir
          output output-dir
          master-map (t/create-master-map input)
          params (t/add-entity-params master-map input)
          entities (t/add-vector-entities master-map)
          coerced-map (t/coerce-vectors master-map)
          expected-map (t/get-expected-vectors master-map output)]
    (is (= (t/vector-frequency coerced-map)
           (t/vector-frequency expected-map)))))

(defn workflow-coercion-test []
    (let [input input-dir
          output output-dir
          master-map (t/create-master-map input)
          edges (t/add-workflow-edges master-map input)
          coerced-map (t/coerce-workflow master-map)
          expected-map (t/get-expected-workflow master-map)]
          (is (= (t/vector-frequency coerced-map)
                 (t/vector-frequency expected-map)))))

(defn job-coercion-test []
    (let [input input-dir
          output output-dir
          master-map (t/create-master-map input)
          params (t/add-entity-params master-map input)
          entities (t/add-vector-entities master-map)
          edges (t/add-workflow-edges master-map input)
          components (t/add-job-components master-map)
          coerced-map (t/coerce-job master-map)
          expected-map (t/get-expected-job master-map)]
          (is (= (t/vector-frequency coerced-map)
                 (t/vector-frequency expected-map)))))
