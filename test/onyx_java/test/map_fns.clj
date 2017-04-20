(ns onyx-java.test.map-fns
  (:import org.onyxplatform.api.java.OnyxMap)
  (:require [clojure.test :refer [deftest is]]
            [onyx-java.utils.map-fns :as mf]))

(deftest to-onyx-map 
  (let [expected {:a "A" :b 5 :c (java.util.UUID/randomUUID) :d :D } 
        o (mf/to-onyx-map expected) ]
    (is (= (.toMap o) expected))))



