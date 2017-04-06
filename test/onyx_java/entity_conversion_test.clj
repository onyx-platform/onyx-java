(ns onyx-java.entity-conversion-test
    (:require   [clojure.test :refer [deftest is]]
                [org.onyxplatform.api.java :as onyx-java]))

(def spec-pattern "test/resources/{edn}.edn")

(def replace-pattern "{edn}")

(defn read-spec-map [spec]
    (read-string
        (slurp clojure.string/replace spec-pattern replace-pattern spec)))

(defn map-to-vector [map]
    (into [] map))

(defn entity-to-atom [])

(defn add-entity-parameter [])

(defn coerce-object-to-onyx-map [])

(defn compare-onyx-map-to-expected-map [])

(defn java-to-onyx-conversion-test [])
