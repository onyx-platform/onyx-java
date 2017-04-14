(ns onyx-java.wrapper.entity
    (:gen-class))


(defn add-parameter-factory [object-name object-map]
    ;; Creates a parameter adding factory for an object created from the
    ;; specified classname.
    (let [c object-name]
    (fn [parameter-vector] (.addParameter (deref (get-in object-map [c :ref]))
        (get parameter-vector 0) (get parameter-vector 1)))))

(defn add-parameter [object-name object-map parameter-vector]
    ;; Adds a single parameter to an entity derived object.
    ;; Parameters should be of the form [key value]
    (def add (add-parameter-factory object-name object-map))
    (add parameter-vector))

(defn add-parameters [object-name object-map parameter-vectors]
    ;; Adds an arbitrary number of parameters to an entity derived object.
    ;; Parameters should be of the form [[k1 v1] [k2 v2] [k3 v3]]
    (def add (add-parameter-factory object-name object-map))
    (dorun (map add parameter-vectors)))

(defn get-clojure-map [object-map object-name]
    ;; Converts the entity content map into its clojure corrected form
    ;; and returns the result.
    (.toCljMap (deref (get-in object-map [object-name :ref]))))

(defn get-clojure-entry [object-map]
    ;; Converts the entity content map into its clojure corrected form
    ;; and returns the result.
    (fn [object-name]
        (hash-map object-name (get-clojure-map object-map object-name))))
