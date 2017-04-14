(ns onyx-java.wrapper.vector
    (:gen-class))


(defn get-entity-type [vector]
    (let [v-name (get (val vector) :class)]
        (case v-name
            "Catalog" "Task"
            "FlowConditions" "FlowCondition"
            "Lifecycles" "Lifecycle"
            "Triggers" "Trigger"
            "Windows" "Window")))


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
