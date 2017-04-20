(ns onyx-java.utils.vector-fns
  (:gen-class) 
  (:import [clojure.lang IPersistentMap PersistentVector]
           org.onyxplatform.api.java.OnyxMap))

(defn keywordize-str-array [string-array]
  ; TODO fully realize the lazy seq into 
  (into [] (map #(keyword %) (vec string-array))))

