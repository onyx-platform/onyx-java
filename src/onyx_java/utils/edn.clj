(ns onyx-java.utils.edn
    (:gen-class)
    (:require [onyx-java.utils.helpers :as help]))



;; Helper functions
(defn read-spec-map [spec]
    (read-string (slurp spec)))

(defn map-to-vectors [map]
    (into [] map))

(defn keyword-to-string [keyword]
    (subs (str keyword) 0))

(defn prepare-java-entry [entry-vector]
    (assoc entry-vector 0 (keyword-to-string (first entry-vector))))

(defn prepare-java-entries [keyword-vectors]
    (vec (map prepare-java-entry keyword-vectors)))


;; Turn edn into java-safe vectors
(defn get-entity-params [spec-file]
    (prepare-java-entries (map-to-vectors
        (read-spec-map spec-file))))

(defn get-specs-from-edn [filenames]
    (let [replace-fn (fn [fname] (clojure.string/replace fname ".edn" ""))]
    (map replace-fn filenames)))

(defn get-edn-from-spec [directory filename]
    (clojure.string/join [directory filename ".edn"]))

(defn get-specs [directory] (
    let [filenames (help/list-directory-files directory)]
    (vec (get-specs-from-edn filenames))))
