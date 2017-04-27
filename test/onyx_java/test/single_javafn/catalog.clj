(ns onyx-java.test.single-javafn.catalog
  (:import [org.onyxplatform.api.java Catalog Task]
          [org.onyxplatform.api.java.utils AsyncCatalog MapFns]
          [org.onyxplatform.api.java.instance CatalogUtils])
  (:require [clojure.test :refer [deftest is]]))


(defn build-catalog []
  (let [cat (Catalog.) 
        fqns "onyxplatform.test.PassMethod" 
        ctr-args {}]
    (-> cat
        (CatalogUtils/addMethod "pass" 5 50 fqns ctr-args)
        (AsyncCatalog/addInput  "in" 5 50)
        (AsyncCatalog/addOutput "out" 5 50))))

(def expected [{:onyx/name :pass, 
                :onyx/fn :onyx-java.instance.bind/method, 
                :onyx/type :function, 
                :onyx/batch-size 5 
                :onyx/batch-timeout 50, 
                :java-instance/class "onyxplatform.test.PassMethod", 
                :java-instance/ctr-args  {}, 
                ; NOTE: java-instance/id is stripped before comparison as its generated
                :onyx/params  [:java-instance/id :java-instance/class :java-instance/ctr-args] }

               {:onyx/name :in, 
                :onyx/plugin :onyx.plugin.core-async/input, 
                :onyx/medium :core.async, 
                :onyx/type :input, 
                :onyx/max-peers 1, 
                :onyx/batch-size 5   
                :onyx/batch-timeout 50 }
               
               {:onyx/name :out, 
                :onyx/plugin :onyx.plugin.core-async/output, 
                :onyx/medium :core.async, 
                :onyx/type :output, 
                :onyx/max-peers 1, 
                :onyx/batch-size 5  
                :onyx/batch-timeout 50 }])

(deftest valid-catalog?
  (let [cat (build-catalog)
        ; Strip generated IDs before comparing
        tasks (map 
                #(dissoc % :java-instance/id)
                (.tasks cat)) ]
    (is (= expected tasks))))

