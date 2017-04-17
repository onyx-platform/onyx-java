(ns onyx-java.test
    (:gen-class)
    (:require [onyx-java.utils.test-template :as t]
              [onyx-java.utils.object :as o]
              [clojure.test :refer [deftest is]]))

(def construct-dir "test/resources/onyx_simple/construct")
(def input-dir "test/resources/onyx_simple/source/")
(def output-dir "test/resources/onyx_simple/target/")

(deftest entity-coercion-test
    (let [construct construct-dir
          input input-dir
          output output-dir
          master-map (t/create-master-map construct)
          params (t/add-entity-params master-map input)
          coerced-map (t/coerce-entities master-map)
          expected-map (t/get-expected-entities master-map output)]
    (is (= coerced-map expected-map))))

(deftest vector-coercion-test
    (let [construct construct-dir
          input input-dir
          output output-dir
          master-map (t/create-master-map construct)
          params (t/add-entity-params master-map input)
          entities (t/add-vector-entities master-map)
          coerced-map (t/coerce-vectors master-map)
          expected-map (t/get-expected-vectors master-map output)]
    (is (= (t/vector-frequency coerced-map)
           (t/vector-frequency expected-map)))))

(deftest workflow-coercion-test
    (let [construct construct-dir
          input input-dir
          output output-dir
          master-map (t/create-master-map construct)
          edges (t/add-workflow-edges master-map input)
          coerced-map (t/coerce-workflows master-map)
          expected-map (t/get-expected-workflows master-map output)]
          (is (= (t/vector-frequency coerced-map)
                 (t/vector-frequency expected-map)))))

(defn job-coercion-test []
    (let [construct construct-dir
          input input-dir
          output output-dir
          master-map (t/create-master-map construct)
          params (t/add-entity-params master-map input)
          entities (t/add-vector-entities master-map)
          edges (t/add-workflow-edges master-map input)
          components (t/add-job-components master-map)
          coerced-map (t/coerce-job master-map)
          expected-map (t/get-expected-job master-map)]
          (is (= (t/vector-frequency coerced-map)
                 (t/vector-frequency expected-map)))))
