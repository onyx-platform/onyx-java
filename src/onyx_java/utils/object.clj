(ns onyx-java.utils.object
    (:gen-class))


;; Class factory creation
(defn class-factory [classpath & types]
    ;; Uses a classname and an arbitrary number of (optional) arguments
    ;; to define a factory which is capable of creating classes.
  (let [args (map #(with-meta (symbol (str "x" %2)) {:tag %1}) types (range))]
    (eval `(fn [~@args] (new ~(symbol classpath) ~@args)))))


;; Object factory creation
(defn get-object-maker [classpath type-string]
    (if (= (count type-string) 0)
        (class-factory classpath)
        (class-factory classpath type-string)))

(defn create-class-object [class-map]
    (let [classpath (class-map :path)
          types (get-in class-map [:params :types])
          objects (get-in class-map [:params :objects])
          type-string (clojure.string/join " " types)
          object-maker (get-object-maker classpath type-string)]
        (if (= (count type-string) 0)
            (object-maker)
            ;please note - we need to alter the following (first objects) if
            ;we want this function to work with constructors with more than a
            ;single argument. i'm shelving this for now due to it working for
            ;our purposes.
            (object-maker (first objects)))))

(defn create-enum-object [class-map]
    (let [classpath (class-map :path)
          type (first (first (class-map :params)))]
          ((eval `(fn [] (. ~(symbol classpath) ~(symbol type)))))))


;; Atomic object construction
(defn create-object! [class-map]
    (let [obj (case (class-map :type)
                "class" (create-class-object class-map)
                "enum" (create-enum-object class-map))]
    (atom obj)))

(defn create-object [class-map]
    (let [obj (case (class-map :type)
                "class" (create-class-object class-map)
                "enum" (create-enum-object class-map))]
    obj))

;; Ancestry tracing

(defn get-direct-base [atom]
    ;; returns the immediate superclass or interface of the passed
    ;; object (object stored as atom)
    (last (bases (type (deref atom)))))

(defn get-all-supers [atom]
    ;; returns all superclasses and interfaces of the passed
    ;; object (object stored as atom)
    (supers (type (deref atom))))
