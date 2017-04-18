(ns onyx-java.utils.persistence
    (:gen-class)
    (:require [onyx-java.utils.helpers :as help]
              [onyx-java.utils.object :as obj]
              [onyx-java.wrapper.entity :as entity]
              [onyx-java.utils.edn :as edn]))

(defn make-instance-map! [class-map]
    ;; Creates a test object as a map with one keyval of the object ref
    (assoc {} :ref (obj/create-object! class-map)))

(defn add-entry [object-map]
    (fn [entry] (into object-map entry)))

(defn get-object-name [data-map classname]
    (if (contains? data-map :name)
        (data-map :name)
        (help/get-map-key classname)))

(defn get-class-type [data-map]
    (if (contains? data-map :type)
        (data-map :type) "class"))

(defn assemble-params [param-vecs]
    (let [class-mapper
            (fn [param]
                (str ((last param) :namespace) '. (first param)))
          obj-mapper
            (fn [param]
                (let [obj-map (last param)
                    obj-map (assoc obj-map
                    :path (str ((last param) :namespace) '. (first param)))]
                (obj/create-object obj-map)))
          classes (vec (map class-mapper param-vecs))
          objects (vec (map obj-mapper param-vecs))]
        {:types classes :objects objects}))

(defn get-constructor-params [data-map]
    (case (data-map :type)
        "enum" (data-map :params)
        (if (contains? data-map :params)
            (assemble-params (data-map :params)) nil)))

(defn parse-spec [spec-vec]
    (let [spec-map {}
          specpath (first spec-vec)
          specname (last spec-vec)
          data (edn/read-spec-map specpath)
          classname (help/prepare-class-string specname)
          classpath (help/qualify-class (get data :namespace) classname)
          classparams (get-constructor-params data)
          objectname (get-object-name data classname)
          classtype (get-class-type data)]
        (-> spec-map
            (assoc :class classname)
            (assoc :path classpath)
            (assoc :type classtype)
            (assoc :source specname)
            (assoc :name objectname)
            (assoc :params classparams))))

(defn make-entry [spec-vec]
    (let [spec-map (parse-spec spec-vec)
          object-map (make-instance-map! spec-map)
          baseclass (help/strip-base (str (obj/get-direct-base (:ref object-map))))
          name (spec-map :name)
          object-map (-> object-map
                        (assoc :class (spec-map :class))
                        (assoc :path (spec-map :path))
                        (assoc :type (spec-map :type))
                        (assoc :source (spec-map :source))
                        (assoc :base baseclass))]
          (assoc {} name object-map)))

(defn create-map [object-map spec-vecs]
    (let [entries (map make-entry spec-vecs)
          entry-adder (add-entry object-map)]
          (map entry-adder entries)))
