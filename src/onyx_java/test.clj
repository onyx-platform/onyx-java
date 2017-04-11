(ns onyx-java.test
    (:gen-class)
    (:require [onyx-java.utils.helpers :as help]
              [onyx-java.wrapper.entity :as entity]
              [onyx-java.utils.edn :as edn]))

;; Used by edn required stuff
(def spec-input-pattern "test/resources/entity_conversion_test/source/{*}.edn")
(def spec-output-pattern "test/resources/entity_conversion_test/target/{*}.edn")
;; Used by entity required stuff
(def class-pattern "org.onyxplatform.api.java.{*}")
;; Used for object persistence
(def object-map {})



(defn prepare-test-object [spec-name]
    (let [classname (help/prepare-class-string spec-name)
          classpath (help/qualify-string class-pattern classname)
          object-name (help/get-map-key classname)
          spec-input (help/qualify-string spec-input-pattern spec-name)]
          (def object-map
              (entity/create-persistent-object classpath classname spec-name object-name object-map))
          (def input-vectors (edn/get-test-parameter-vectors spec-input))
          (entity/add-parameters object-name object-map input-vectors)))

(defn get-entries-by-object-type [object-type]
    (entity/get-entries-by-type object-type object-map))

(defn get-entries-by-spec [spec]
    (entity/get-entries-by-source spec object-map))

(defn get-entry-keys-by-type [type]
    (entity/get-entry-names-by-type type object-map))

(defn get-entry-keys-by-source [spec-name]
    (entity/get-entry-names-by-source spec-name object-map))

(defn get-clojure-maps [object-keys]
    (map get-clojure-map object-keys))

(defn get-clojure-map [object-key]
    (entity/get-clojure-map object-key object-map))

(defn prepare-test-objects [classnames]
    (map prepare-test-object classnames))

(defn prepare-comparison-map [spec-name]
    spec-output (help/qualify-string spec-output-pattern spec-name))
