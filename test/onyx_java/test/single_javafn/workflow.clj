(ns onyx-java.test.single-javafn.workflow
  (:require [clojure.test :refer [deftest is]]) 
  (:import [org.onyxplatform.api.java Workflow]))


(defn build-workflow []
  
  )


(deftest valid-workflow?
  (let [wf (build-workflow)]
    (is true)))

