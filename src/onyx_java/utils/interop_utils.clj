(ns onyx-java.utils.interop-utils
    (:gen-class))

;;these are some sketches for the beginning of interop checks
;;(required params, choice limitations, etc. not currently used.)

(defn check-choices [choices value]
    (println choices)
    (println value)
    (if (boolean (some #{:all} (flatten choices))) value
        (if (some #{value} (flatten choices)) value "CHOICE-ERROR")))

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
