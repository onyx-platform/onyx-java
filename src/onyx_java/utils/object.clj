(ns onyx-java.utils.object
    (:gen-class))


;; Class factory creation
(defn class-factory [classpath & types]
    ;; Uses a classname and an arbitrary number of (optional) arguments
    ;; to define a factory which is capable of creating classes.
  (let [args (map #(with-meta (symbol (str "x" %2)) {:tag %1}) types (range))]
    (eval `(fn [~@args] (new ~(symbol classpath) ~@args)))))


;; Object factory creation
(defn create-class [class-map]
    (let [classpath (class-map :path)
          class-factory (class-factory classpath)]
        (class-factory)))

(defn create-enum [class-map]
    (let [classpath (class-map :path)
          type (first (first (class-map :params)))]
          (eval `(fn [] (. ~(symbol classpath) ~(symbol type))))))


;; Atomic object construction
(defn create-object! [class-map]
    (let [obj (case (class-map :type)
                "class" (create-class class-map)
                "enum" (create-enum class-map))]
    (atom obj)))

;; Ancestry tracing

(defn get-direct-base [atom]
    ;; returns the immediate superclass or interface of the passed
    ;; object (object stored as atom)
    (last (bases (type (deref atom)))))

(defn get-all-supers [atom]
    ;; returns all superclasses and interfaces of the passed
    ;; object (object stored as atom)
    (supers (type (deref atom))))
