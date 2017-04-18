(ns onyx-java.wrapper.scheduler
    (:gen-class))


(defn get-clojure-string [scheduler]
    (.toCljString (deref scheduler)))

(defn get-clojure-entry [object-map]
    (fn [name] (let [scheduler (get-in object-map [name :ref])]
        (hash-map name (get-clojure-string scheduler)))))
