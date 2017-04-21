(ns onyx-java.test.single-javafn.lifecycles
  (:require [clojure.test :refer [deftest is]]
            [onyx-java.test.single-javafn.catalog :as c]) 
  (:import [org.onyxplatform.api.java Catalog Lifecycles]
           [org.onyxplatform.api.java.utils AsyncLifecycles MapFns]))

(defn build-lifecycles [^Catalog cat]
  "Adds input and output async catalog entries to 
  the 1-task catalog.
  
  Returns the Lifecyle object containing just the 
  2 async lifecycle entries"
  )


(deftest valid-lifecycles?
  (let [ ]
    (is true)))

