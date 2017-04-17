(ns onyx-java.utils.helpers
    (:gen-class))

(def class-splitter-regex #"_")

(defn prepare-class-string [class-string]
    ;; Prepares a specially patterned class string (strips off _* from an edn
    ;; file so the class can be loaded using the dynamic classloader.)
    (first (clojure.string/split class-string class-splitter-regex)))

(defn qualify-class [classpath classname]
    (str classpath "." classname))

(defn get-map-key [seed]
    ;; Generate a unique identifier that still retains the seed for readability.
    (str (gensym seed)))

(defn get-unique-id []
    (java.util.UUID/UUID/randomUUID))

(defn strip-first-last [s]
    (subs s 1 (- (count s) 1)))

(defn strip-base [baseclass]
    (last (clojure.string/split (last (clojure.string/split baseclass #" ")) #"\.")))

(defn list-directory-files [directory]
    (vec (.list (clojure.java.io/file directory))))
