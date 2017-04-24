(ns onyx-java.test.lifecycles
  (:require [clojure.test :refer [deftest is]]
            [onyx-java.test.single-javafn.catalog :as c]) 
  (:import [org.onyxplatform.api.java Catalog Lifecycles]
           [org.onyxplatform.api.java.utils AsyncLifecycles MapFns]))

(defn build-lifecycles []
  "Returns the Lifecyle object containing just the 
  2 async lifecycle entries"
  (let [lc (Lifecycles.)]
    (-> lc
        (AsyncLifecycles/addInput  "in")
        (AsyncLifecycles/addOutput "out"))))

(def expected [{:lifecycle/task :in
                :lifecycle/calls :onyx-java.utils.async-lifecycles/in-calls}
               {:lifecycle/task :in
                :lifecycle/calls :onyx.plugin.core-async/reader-calls}
               {:lifecycle/task :out
                :lifecycle/calls :onyx-java.utils.async-lifecycles/out-calls}
               {:lifecycle/task :out
                :lifecycle/calls :onyx.plugin.core-async/writer-calls}])

; Lifecycles generate unique uuid's under the :core.async/id key
; ensure the key exists, is of type UUID then compare the remainder
; 
(deftest valid-lifecycles?
  (let [lyfe (build-lifecycles)
        ; Convert to a count. There should be 2
        ids-valid? (= 2 (apply + (map 
                                   #(if (and (contains? % :core.async/id) 
                                             (= java.util.UUID (type (:core.async/id %))))
                                      1
                                      0) 
                                   (.cycles lyfe)))) 
        stripped (map
                   #(dissoc % :core.async/id)
                   (.cycles lyfe)) ]
    (is (and ids-valid? (= expected stripped)))))

