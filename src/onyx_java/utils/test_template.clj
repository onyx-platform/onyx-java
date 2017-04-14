(ns onyx-java.utils.test-template
    (:gen-class)
    (:require [onyx-java.utils.persistence :as persistence]
              [onyx-java.utils.edn :as edn]
              [onyx-java.utils.filter :as filter]
              [onyx-java.wrapper.entity :as entity]
              [onyx-java.wrapper.vector :as vector]))

(def class-pattern "org.onyxplatform.api.java.{*}")


(defn create-master-map [directory]
    ;; loads every spec in the given directory as an instance based on the
    ;; associated org.onyxplatform.api.java class, provided the class exists,
    ;; and holds a reference to each in a master map.
    (let [master-map {}]
    (apply merge (persistence/create-map
            class-pattern
            master-map
            (edn/get-specs directory)))))

(defn coerce-entities [object-map]
    (let [entities (filter/filter-by-base object-map "OnyxEntity")
          names (keys entities)
          mapper (entity/get-clojure-entry entities)]
    (apply merge (map mapper names))))

(defn get-expected-entities [object-map dir]
    (let [entities (filter/filter-by-base object-map "OnyxEntity")]
        (filter/make-comparison-map entities (filter/output-compare-fn dir))))

(defn add-entity-params [object-map dir]
    (let [entities (filter/base-compare-map
            (filter/filter-by-base object-map "OnyxEntity"))
          adder (fn [entity]
                    (let [source (val entity)
                           edn (edn/get-edn-from-spec dir source)
                           vectors (edn/get-entity-params edn)]
                (entity/add-parameters (key entity) object-map vectors)))]
          (dorun (map adder entities))))

(defn add-vector-entities [object-map]
    (let [entities (filter/filter-by-base object-map "OnyxEntity")
          vectors (filter/filter-by-base object-map "OnyxVector")
          adder (fn [vector]
                (let [ent-type (vector/get-entity-type vector)
                    vector-entities (filter/filter-by-class entities ent-type)]
                    [vector vector-entities]))
          vector-entity-array (map adder vectors)]
          (vector/add-all-entities vector-entity-array)))
