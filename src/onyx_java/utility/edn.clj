(ns onyx-java.utility.edn
    (:gen-class))



;; Helper functions
(defn read-spec-map [spec]
    (read-string (slurp spec)))

(defn map-to-vectors [map]
    (into [] map))

(defn keyword-to-string [keyword]
    (subs (str keyword) 1))

(defn prepare-java-entry [entry-vector]
    (assoc entry-vector 0 (keyword-to-string (first entry-vector))))

(defn prepare-java-entries [keyword-vectors]
    (vec (map prepare-java-entry keyword-vectors)))


;; Turn edn into java-safe vectors
(defn get-parameter-vectors [spec-file]
    (prepare-java-entries (map-to-vectors
        (read-spec-map spec-file))))
