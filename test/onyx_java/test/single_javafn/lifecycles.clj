(ns onyx-java.test.single-javafn.lifecycles
  (:require [clojure.test :refer [deftest is]]) 
  (:import [org.onyxplatform.api.java Lifecycles]
           [org.onyxplatform.api.java.utils AsyncLifecycles MapFns]))

(defn build-lifecycles []
  
  )


(deftest valid-lifecycles?
  (let [cat (build-lifecycles)]
    (is true)))

