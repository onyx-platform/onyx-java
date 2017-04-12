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

;;Helper functions (used by API functions)

(defn get-clojure-map-by-key [object-key]
    (entity/get-clojure-map object-key object-map))

(defn get-clojure-maps-by-key [object-keys]
    (map get-clojure-map object-keys))

(defn get-entries-by-object-type [object-type]
    (entity/get-entries-by-type object-type object-map))

(defn get-entries-by-spec [spec]
    (entity/get-entries-by-source spec object-map))

(defn get-entry-keys-by-type [type]
    (entity/get-entry-names-by-type type object-map))

(defn get-entry-keys-by-source [spec-name]
    (entity/get-entry-names-by-source spec-name object-map))

(defn prepare-test-object [spec-name]
    ;; Assembles a single test object and adds it to the persistent object-map.
    ;; spec-name represents the name of the file containing parameters used
    ;; to build the object, and must be named after the class to which the
    ;; object belongs, in the following pattern: Classname_xyz, where
    ;; Classname is always exactly the same as the class, with
    ;; an optional _xyz where xyz can represent any alphanumeric combination.
    (let [classname (help/prepare-class-string spec-name)
          classpath (help/qualify-string class-pattern classname)
          object-name (help/get-map-key classname)
          spec-input (help/qualify-string spec-input-pattern spec-name)]
          (def object-map
              (entity/create-persistent-object
                  classpath classname spec-name object-name object-map))
          (def input-vectors (edn/get-test-parameter-vectors spec-input))
          (entity/add-parameters object-name object-map input-vectors)))

(defn base-compare-entry [source-map]
    ;; Prepares a single entry for the base comparison function
    (let [s source-map
          t {}]
        (fn [key]
            (assoc t key (get-in s [key :source])))))

(defn base-compare-map [object-map]
    ;; Prepares a base comparison map for use in comparison making,
    ;; producing a new map consisting of the original keyset
    ;; and a reduced valueset distilled to only the production classname.
    (let [keys (keys object-map)
          entry-creator (base-compare-entry object-map)
          entries (map entry-creator keys)]
          (apply merge entries)))

(defn output-compare-fn [entry]
  ;; Transforms entry value into a map gathered from the EDN file in
  ;; the output-pattern dir.
  (edn/read-spec-map
      (help/qualify-string spec-output-pattern (val entry))))

(defn clojure-map-compare-fn [entry]
  ;; Transforms entry-value into its clojure/onyx representation.
  (get-clojure-map-by-key (key entry)))



;;--------------------------- API --------------------------------;;
(defn make-object-map [classname-vec]
    ;; Prepares the test map, populating it with new instances of the
  (map prepare-test-object classname-vec))


(defn make-comparison-map [object-map convert-fn]
    ;; Pass an object-map (may be any map) and a function used to transform
    ;; the value (may be any transformation function)
    ;; and this function will return a new map containing the original keys
    ;; along with the newly transformed vals.
    ;; Please note: convert-fn must take a map-entry as the only argument.
    (let [base-map (base-compare-map object-map)
          output {}
          convert convert-fn
          add-map (fn [entry] (assoc output (key entry) (convert entry)))
          entries (map add-map base-map)]
        (apply merge entries)))
