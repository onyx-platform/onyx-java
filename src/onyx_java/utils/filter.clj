(ns onyx-java.utils.filter
    (:gen-class)
    (:require [onyx-java.utils.edn :as edn]
              [onyx-java.utils.helpers :as help]))

(defn filter-by-key [m ke t]
    (into {} (vec (filter (fn [[k v]] (= (get v ke) t)) m))))

(defn filter-by-source [map t]
    (let [m map
          k :source]
          (filter-by-key m k t)))

(defn filter-by-class [map t]
  (let [m map
        k :class]
        (filter-by-key m k t)))

(defn filter-by-base [map t]
    (let [m map
          k :base]
          (filter-by-key m k t)))


;;----------------------Comparison Functions-----------------------;;
(defn output-compare-fn [dir]
;; Transforms entry value into a map gathered from the EDN file in
;; the output-pattern dir.
(fn [entry]
  (edn/read-spec-map
      (edn/get-edn-from-spec dir (val entry)))))



(defn base-compare-entry [source-map]
  ;; Prepares a single entry for the base comparison function
  ;; a prepared function contains the original map key and a pared-down
  ;; value consisting only of the
  (let [s source-map
        t {}]
      (fn [key]
          (assoc t key (get-in s [key :source])))))

(defn object-compare-map [object-map]
  ;; Prepares a base comparison map for use in comparison making,
  ;; producing a new map consisting of the original keyset
  ;; and a reduced valueset distilled to only the production classname.
  (let [keys (keys object-map)
        entry-creator (base-compare-entry object-map)
        entries (map entry-creator keys)]
        (apply merge entries)))


(defn make-comparison-map [object-map convert-fn]
  ;; Pass an object-map (may be any map) and a function used to transform
  ;; the value (may be any transformation function)
  ;; and this function will return a new map containing the original keys
  ;; along with the newly transformed vals.
  ;; Please note: convert-fn must take a map-entry as the only argument.
    (let [base-map (object-compare-map object-map)
          output {}
          convert convert-fn
          add-map (fn [entry] (assoc output (key entry) (convert entry)))
          entries (map add-map base-map)]
        (apply merge entries)))
