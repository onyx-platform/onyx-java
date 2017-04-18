(ns onyx-java.utils.object
    (:gen-class))


;; Class factory creation
(defn class-factory [classpath & types]
    ;; Uses a classname and an arbitrary number of (optional) arguments
    ;; to define a factory which is capable of creating classes.
  (let [args (map #(with-meta (symbol (str "x" %2)) {:tag %1}) types (range))]
    (eval `(fn [~@args] (new ~(symbol classpath) ~@args)))))


;; Atomic object construction
(defn create-class-object! [classpath]
    (let [creator (class-factory classpath)
          obj (creator)]
    (atom obj)))
