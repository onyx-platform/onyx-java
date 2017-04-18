(ns onyx-java.utils.map-fns
  (:gen-class) 
  (:import clojure.lang.IPersistentMap
           org.onyxplatform.api.java.OnyxEntity))

(defn to-entity-map [^IPersistentMap m]
  (let [ent (OnyxEntity.)
        ks (keys m) ]
    (reduce 
      ; Strip keywords, convert everything else to a string.
      (fn [ent k]
        (let [n (if (keyword? k) 
                  (name k)
                  (str k))
              rv (get m k)
              v (if (keyword? rv)
                  (name rv)
                  (str rv)) ]
          ; Side-effectful
          (.addParameter ent n v)))
      ent
      ks)
    ent))

