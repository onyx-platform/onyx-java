(ns onyx-java.utils.map-fns
  (:gen-class) 
  (:import clojure.lang.IPersistentMap
           org.onyxplatform.api.java.OnyxEntity))


(defn to-entity-map [^IPersistentMap m]
  (let [ent (OnyxEntity.)
        ks (keys m) ]
    (reduce 
      (fn [k]
        
        )
      ent
      ks)))





