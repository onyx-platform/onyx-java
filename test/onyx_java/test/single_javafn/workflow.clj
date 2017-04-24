(ns onyx-java.test.single-javafn.workflow
  (:require [clojure.test :refer [deftest is]]) 
  (:import [org.onyxplatform.api.java Workflow]))

(def expected [[:in :pass]
               [:pass :out]])

(defn build-workflow []
  (let [wf (Workflow.)]
    (-> wf
        (.addEdge "in" "pass")
        (.addEdge "pass" "out"))))


(deftest valid-workflow?
  (let [wf (build-workflow) ]
    (is (= expected (.graph wf)))))

