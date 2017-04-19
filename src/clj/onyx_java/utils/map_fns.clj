(ns onyx-java.utils.map-fns
  (:gen-class) 
  (:import clojure.lang.IPersistentMap
           org.onyxplatform.api.java.OnyxMap))

(defn to-onyx-map [^IPersistentMap m]
  ;; TODO: Re-write to take more care with
  ;;       map padding to be sensitive to 
  ;;       keywords vs objs
  ;;
  (let [ent (OnyxMap.)
        ks (keys m) ]
    (reduce 
      ; Strip keywords, convert everything else to a string.
      (fn [ent k]
        (let [n (if (keyword? k) 
                  (name k)
                  (str k))
              bv (get m k "MISSING")
              v (if (keyword? bv)
                  (name bv)
                  bv) ]
          ; Side-effectful
          (.addParameter ent n v)
          ent))
      ent
      ks)
    ent))

