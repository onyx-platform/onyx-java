(ns onyx-java.wrapper.vector
    (:gen-class))


(defn get-entity-type [vector]
    (let [v-name ((val vector) :class)]
        (case v-name
            "Catalog" "Task"
            "FlowConditions" "FlowCondition"
            "Lifecycles" "Lifecycle"
            "Triggers" "Trigger"
            "Windows" "Window")))

(defn add-entity-factory [vector]
 (let [vec-type ((val vector) :class)
       vec-ref ((val vector) :ref)]
       (fn [entity] (let [object-ref ((val entity) :ref)]
        (case vec-type
            "Catalog" (.addTask (deref vec-ref) (deref object-ref))
            "FlowConditions" (.addCondition (deref vec-ref) (deref object-ref))
            "Lifecycles" (.addCall (deref vec-ref) (deref object-ref))
            "Triggers" (.addTrigger (deref vec-ref) (deref object-ref))
            "Windows" (.addWindow (deref vec-ref) (deref object-ref)))))))

(defn add-entities [vector-with-entities]
    (let [vector (first vector-with-entities)
          entities (last vector-with-entities)
          adder (add-entity-factory vector)]
        (dorun (map adder entities))))

(defn add-all-entities [vectors-with-entities]
    (dorun (map add-entities vectors-with-entities)))
