(ns onyx-java.utils.object
    (:gen-class))

;; These vars should be defined where they are being used.
    ;; (def class-pattern "org.onyxplatform.api.java.{class}")
    ;; (def replace-classname-pattern "{class}")
    ;; (def object-map {})


;; Class factory creation
(defn class-factory [classpath & types]
    ;; Uses a classname and an arbitrary number of (optional) arguments
    ;; to define a factory which is capable of creating classes.
  (let [args (map #(with-meta (symbol (str "x" %2)) {:tag %1}) types (range))]
    (eval `(fn [~@args] (new ~(symbol classpath) ~@args)))))

;; Object factory creation
(defn object-factory [classpath]
    ;; Uses the class-factory to create an object factory for a class
    ;; using the class's public, default, no-argument constructor.
    (let [object-factory (class-factory classpath)]
    (object-factory)))

;; Atomic object construction
(defn make-object! [classpath]
    ;; Uses the object-factory previously defined to create a persistent
    ;; object (persistence using clojure thread safe atom behavior)
    (atom (object-factory classpath)))
