(ns onyx-java.test
    (:gen-class)
    (:require [onyx-java.utility.helpers :as help]
              [onyx-java.wrapper.entity :as entity]
              [onyx-java.utility.edn :as edn]))

;; Used by edn required stuff
(def spec-pattern "test/resources/entity_conversion_test/source/{*}.edn")
;; Used by entity required stuff
(def class-pattern "org.onyxplatform.api.java.{*}")
;; Used for object persistence
(def object-map {})



(defn prepare-object [classname]
    (let [classpath (help/qualify-string class-pattern classname)
          object-name (help/get-object-name classname)
          spec-name (help/qualify-string spec-pattern classname)]
          (def object-map (entity/create-persistent-object classpath object-name object-map))
          (def parameter-vectors (edn/get-parameter-vectors spec-name))
          (entity/add-parameters object-name object-map parameter-vectors)))

(defn get-clojure-map [classname]
    (let [object-name (help/get-object-name classname)]
    (entity/get-clojure-map object-name object-map)))

(defn prepare-objects [classnames]
    (map prepare-object classnames))
