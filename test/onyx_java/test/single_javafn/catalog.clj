(ns onyx-java.test.single-javafn.catalog
  (import [org.onyxplatform.api.java Catalog Task]
          [org.onyxplatform.api.java.utils AsyncCatalog MapFns])
  (:require [clojure.test :refer [deftest is]]))


(defn build-catalog []
  (let [cat (Catalog.)
        om ()
        t (Task. om) ]
    (-> cat
        (.addTask t)
        
        )))

(deftest valid-catalog?
  (let [cat (build-catalog)]
    (is true)))

