(ns onyx-java.test.single-javafn.catalog
  (import [org.onyxplatform.api.java Catalog Task]
          [org.onyxplatform.api.java.utils AsyncCatalog MapFns])
  (:require [clojure.test :refer [deftest is]]))


(defn build-catalog []
  (let [cat (Catalog.)
        om (MapFns/fromResources "catalog-simple.edn")
        t (Task. om) ]
    (-> cat
        (.addTask t)
        (AsyncCatalog/addInput  "in" 5 50)
        (AsyncCatalog/addOutput "out" 5 50))))

(def expected [{:onyx/name :pass, 
                :onyx/fn :org.onyxplatform.api.java.instance.PassMethod,
                :onyx/type :function, 
                :onyx/batch-size 5   
                :onyx/batch-timeout 50}

               {:onyx/name :in, 
                :onyx/plugin :onyx.plugin.core-async/input, 
                :onyx/medium :core.async, 
                :onyx/type :input, 
                :onyx/max-peers 1, 
                :onyx/batch-size 5   
                :onyx/batch-timeout 50, 
                :onyx/doc "Reads segments from a core.async channel"}
               
               {:onyx/name :out, 
                :onyx/plugin :onyx.plugin.core-async/output, 
                :onyx/medium :core.async, 
                :onyx/type :output, 
                :onyx/max-peers 1, 
                :onyx/batch-size 5  
                :onyx/batch-timeout 50, 
                :onyx/doc "Writes segments to a core.async channel"}])

(deftest valid-catalog?
  (let [cat (build-catalog)]
    (is (= expected (.tasks cat)))))

