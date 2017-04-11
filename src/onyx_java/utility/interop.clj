(ns onyx-java.utility.interop
  (:gen-class :name onyx-java.utility.interop
              :methods [^:static [write_batch [clojure.lang.IPersistentMap] clojure.lang.IPersistentMap]
                        ^:static [read_batch [clojure.lang.IPersistentMap] clojure.lang.IPersistentMap]])
  (:require [onyx.information-model :refer [model]]))

(defn -write_batch
  [event]
  ((resolve 'onyx.peer.function/write-batch) event))

(defn -read_batch
  [event]
  ((resolve 'onyx.peer.function/read-batch) event))

(gen-interface
  :name onyx-java.IPipeline
  :methods [[writeBatch [clojure.lang.IPersistentMap] clojure.lang.IPersistentMap]])

(defn boolean-string [x]
    (if (= (type x) java.lang.String) (read-string x) x))

(def casts
  {:boolean (fn [x] (boolean (boolean-string x)))
   :integer (fn [x] (Integer/parseInt (re-find #"\A-?\d+" x)))
   :string (fn [x] (str x))
   :any (fn [x] x)
   :keyword (fn [x] (keyword x))
   :vector (fn [x] (vec x))})

(defn check-choices [choices value]
    (if (boolean (some #{:all} (flatten choices))) value
        (if (some #{value} choices)
            value
            "CHOICE-ERROR"
            )))

(defn get-required-keymap [section]
    (let [required-keys (vec (keys (filter (fn [[k v]] (and (-> v :optional? not)
                                    (not (contains? v :deprecated-version))))
                                    (get-in model [section :model]))))]
     (apply merge (map #(hash-map % nil) required-keys))))

(defn update-required-keymap [required-keymap keyword]
    (if (boolean (some #{keyword} (keys required-keymap)))
        (assoc required-keymap keyword true)
        required-keymap))

(defn required-keywords [required existing]
    (let [missing (clojure.set/difference (set (keys required)) (set (keys existing)))]
    (if (= 0 (count missing))
        nil
        missing)))

(defn cast-types [section m]
  (let [section* (keyword section)
        required-keymap (get-required-keymap section*)
        new-map (reduce-kv
                    (fn [m* k v]
                        (let [k* (keyword k)
                        type (get-in model [section* :model k* :type])
                        choices (get-in model [section* :model k* :choices] [:all])
                        v* (check-choices choices ((get casts type identity) v))]
                        (assoc m* k* v*)))
                    {}
                    m)]
    (def missing (required-keywords required-keymap new-map))
    (if (not missing)
        new-map
        (apply merge (map #(hash-map % "KEY-ERROR") missing)))
    ))

(defn coerce-workflow [workflow]
  (mapv #(mapv (fn [v] (keyword v)) %) workflow))

(defn coerce-catalog [catalog]
  (mapv #(cast-types :catalog-entry %) catalog))

(defn coerce-lifecycles [lifecycles]
  (mapv #(cast-types :lifecycle-entry %) lifecycles))

(defn coerce-flow-conditions [fcs]
  (mapv #(cast-types :flow-conditions-entry %) fcs))

(defn coerce-windows [windows]
  (mapv #(cast-types :window-entry %) windows))

(defn coerce-trigger [trigger]
  (mapv #(cast-types :trigger-entry %) trigger))
