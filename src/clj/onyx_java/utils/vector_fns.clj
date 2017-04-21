(ns onyx-java.utils.vector-fns
  (:gen-class) 
  (:import [clojure.lang IPersistentMap PersistentVector]
           org.onyxplatform.api.java.OnyxMap))

(defn keywordize-str-array [string-array]
  (into [] (map #(keyword %) (vec string-array))))



