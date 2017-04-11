(ns onyx-java.wrapper.entity
    (:gen-class)
    (:require [onyx-java.utils.object :as o]))


;; Wrapped behaviors

(defn create-object [classpath]
    ;; Creates a new object
    (o/make-object! classpath))

(defn create-persistent-object [classpath classname spec-name object-name object-map]
    ;; Creates an object that hangs around by adding it to the passed
    ;; object map. Currently this does fully qualified conversions
    ;; (i.e, this function auto converts 'Entity' to 'fully.qualified.Entity')
    (assoc object-map object-name {:ref (create-object classpath)
                                   :type classname
                                   :source spec-name}))

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
    (map add parameter-vectors))

(defn get-clojure-map [object-name object-map]
    ;; Converts the entity content map into its clojure corrected form
    ;; and returns the result.
    (.toCljMap (deref (get-in object-map [object-name :ref]))))


;; Helper functions

(defn get-entries-by-type [object-type object-map]
    (vec (filter (fn [[k v]] (= (get v :type) object-type)) object-map)))

(defn get-entry-names-by-type [object-type object-map]
    (vec (map key (get-entries-by-type object-type))))

(defn get-entry-values-by-type [object-type object-map]
    (vec (map val (get-entries-by-type object-type))))

(defn get-entries-by-source [spec-name object-map]
    (vec (filter (fn [[k v]] (= (get v :source) spec-name)) object-map)))

(defn get-entry-names-by-source [source object-map]
    (vec (map key (get-entries-by-source source object-map))))

(defn get-entry-values-by-source [source object-map]
    (vec (map val (get-entries-by-source source object-map))))
