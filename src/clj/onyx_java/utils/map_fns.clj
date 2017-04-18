(ns onyx-java.utils.map-fns
  (:gen-class) 
  (:import clojure.lang.IPersistentMap
           org.onyxplatform.api.java.OnyxEntity))

(defn to-entity-map [^IPersistentMap m]
  (let [ent (OnyxEntity.)
        ks (keys m) ]
    (reduce 
      (fn [ent k]
        (let [n (name k)
              v (get m k) ]
          ; Side-effectful
          (.addParameter ent n v)))
      ent
      ks)
    ent))

