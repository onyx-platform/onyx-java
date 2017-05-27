(ns onyx-java.instance.bind
  (:gen-class)
  (:require [onyx-java.instance.catalog :as cat])
  (:import [org.onyxplatform.api.java.instance BindUtils OnyxFn]))

(defn exists?  [c]
  (let [loader (.getContextClassLoader (Thread/currentThread))]
    (try
      (Class/forName c false loader)
      true
      (catch ClassNotFoundException cnfe false))))

(def instances (atom {}))

(defn keyname [id]
  (keyword (str id)))

(defn task-id [task]
  (keyname (cat/id task)))

(defn instance 
  ([id]
   (if-not (contains? @instances id)
     nil 
     (get @instances id)))
  ([id fq-class-name ctr-args]
    (let [k (keyname id)]
      (println "bind> loading class=" fq-class-name)
      (if (contains? @instances k)
        (get @instances k) 
        (let [i (BindUtils/loadOnyxFn fq-class-name ctr-args)]
          (println "bind> instance=" i)
          (swap! instances assoc k i)
          i)))))
  
(defn method [id fq-class-name ctr-args segment]
  (let [inst-ifn (instance id fq-class-name ctr-args)]
    (inst-ifn segment)))

(defn release [task]
  (let [k (task-id task)]
    (if (contains? @instances k)
      (let [i (instance k)]
        (.releaseClassLoader i)
        (swap! instances dissoc k)))))

(defn release-all [catalog]
  (doseq [task catalog]
    (if (cat/instance? task)
      (release task))))

