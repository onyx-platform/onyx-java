(ns onyx-java.test.single-javafn.job
  (:require [clojure.test :refer [deftest is]]
            [onyx-java.test.job :as j]
            [onyx-java.test.single-javafn.catalog :as cat]))

(deftest java-pass-through
  (let [catalog (cat/build-catalog) 
        inputs  [{:pass-through "PASSTHROUGH"}] 
        outputs (j/run-job catalog inputs) ]
    (is (= (first inputs) (first (:out outputs))))))

