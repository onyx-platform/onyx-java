
(ns onyx-java.test.single-cljfn.job
  (:require [clojure.test :refer [deftest is]]
            [onyx-java.test.job :as j]
            [onyx-java.test.single-cljfn.catalog :as cat]))

(deftest clj-pass-through
  (let [catalog (cat/build-catalog) ;cat/expected
        inputs  [{:pass-through "PASSTHROUGH"}] 
        outputs (j/run-job catalog inputs) ]
    (is (= (first inputs) (first (:out outputs))))))

