(ns onyx-java.test.single-cljfn.job
  (:require [clojure.test :refer [deftest is]]
            [onyx-java.test.job :as j]
            [onyx-java.test.single-cljfn.catalog :as cat]))

(deftest clj-pass-through
  (let [catalog cat/expected
        inputs {:in [{:pass-through "PASSTHROUGH"}]} 
        outputs (j/run-clj-job catalog inputs) ]
    (is (= (first (:in inputs)) (first (:out outputs))))))

#_(deftest java-pass-through 
  (let [catalog (cat/build-catalog)
        inputs [{:pass-through "PASSTHROUGH"}] 
        outputs (j/run-job catalog inputs) ]
    (is (= inputs outputs))))

