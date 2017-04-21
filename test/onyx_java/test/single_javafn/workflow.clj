(ns onyx-java.test.single-javafn.workflow
  (:require [clojure.test :refer [deftest is]]) 
  (:import [org.onyxplatform.api.java Workflow]))

(def expected [[:in :jfn]
               [:jfn :out]])

(defn build-workflow []
  (let [wf (Workflow.)]
    (-> wf
        (.addEdge "in" "jfn")
        (.addEdge "jfn" "out"))))


(deftest valid-workflow?
  (let [wf (build-workflow) ]
    (is (= expected (.graph wf)))))

