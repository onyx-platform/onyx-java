(ns onyx-java.utils.persistence
    (:gen-class)
    (:require [onyx-java.utils.helpers :as help]
              [onyx-java.utils.object :as obj]
              [onyx-java.wrapper.entity :as entity]
              [onyx-java.utils.edn :as edn]))

(defn make-instance [classpath]
    ;; Creates a test object as a map with one keyval of the object ref
    (assoc {} :ref (obj/create-object classpath)))

(defn add-key [m k v]
    (assoc m k v))

(defn add-entry [object-map]
    (fn [entry] (into object-map entry)))

(defn make-entry [class-pattern] (fn [spec]
    (let [classname (help/prepare-class-string spec)
          classpath (help/qualify-string class-pattern classname)
          name (help/get-map-key classname)
          object (make-instance classpath)
          baseclass (help/strip-base (str (obj/get-direct-base (:ref object))))]
          (assoc {} name
              (add-key
                  (add-key
                      (add-key object :source spec)
                  :class classname)
              :base baseclass)))))

(defn create-map [class-pattern object-map specs]
    (let [entry-maker (make-entry class-pattern)
          entry-adder (add-entry object-map)
          entries (map entry-maker specs)]
          (map entry-adder entries)))
