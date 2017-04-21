(ns onyx-java.utils.map-fns
  (:gen-class) 
  (:require [clojure.java.io :refer [resource]])
  (:import [clojure.lang IPersistentMap PersistentVector]
           org.onyxplatform.api.java.OnyxMap))

(defn full-keyword-str [kw]
  "Returns the full string representation
   of a keyword that is fully qualified.
   Passes through if not a keyword"
  (if-not (keyword? kw)
    kw 
    (if (nil? (namespace kw))
      (name kw)
      (str (namespace kw) "/" (name kw)))))

(defn to-onyx-map [^IPersistentMap m]
  (let [ent (OnyxMap.)
        ks (keys m) ]
    (reduce 
      ; Strip keywords, pass everything else 
      ; through untouched.
      ;
      ; Note: take care with qualified keywords
      ;       as keyword doesn't include the 
      ;       namespace
      ;
      (fn [ent k]
        (let [n (full-keyword-str k)
              bv (get m k "MISSING")
              kwv? (keyword? bv)
              v (full-keyword-str bv) ]
          ; Side-effectful
          (if kwv?
            (.addKeywordParameter ent n v)
            (.addObjectParameter ent n v))
          ent))
      ent
      ks)
    ent))

(defn edn-from-resources [rsrc-path]
  (to-onyx-map (-> rsrc-path resource slurp read-string)))

