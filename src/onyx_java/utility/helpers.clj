(ns onyx-java.utility.helpers
    (:gen-class))

(def replace-pattern "{*}")
(def class-splitter-regex #"_")

(defn prepare-class-string [class-string]
    ;; Prepares a specially patterned class string (strips off _* from an edn
    ;; file so the class can be loaded using the dynamic classloader.)
    (first (clojure.string/split class-string class-splitter-regex)))

(defn qualify-string [original qualification]
    ;; Fully qualifies a package class using the given class name.
    (clojure.string/replace original replace-pattern qualification))

(defn get-map-key [seed]
    ;; Generate a unique identifier that still retains the seed for readability.
    (str (gensym seed)))

(defn get-unique-id []
    (java.util.UUID/UUID/randomUUID))

(defn update-key-value [keymap key value]
    )
