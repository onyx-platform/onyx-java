(ns onyx-java.wrapper.workflow
    (:gen-class))

(defn add-edge-factory [object-name object-map]
    (let [c object-name]
    (fn [parameter-vector] (.addEdge (deref (get-in object-map [c :ref]))
        (get parameter-vector 0) (get parameter-vector 1)))))

(defn add-edge [object-name object-map parameter-vector]
    (let [add (add-edge-factory object-name object-map)]
        (add parameter-vector)))

(defn add-edges [workflow-name object-map edges]
    (let [add (add-edge-factory workflow-name object-map)]
    (dorun (map add edges))))

(defn get-clojure-graph [workflow]
    (.toCljGraph (deref workflow)))

(defn get-clojure-entry [object-map]
    (fn [name] (let [workflow (get-in object-map [name :ref])]
        (hash-map name (get-clojure-graph workflow)))))
