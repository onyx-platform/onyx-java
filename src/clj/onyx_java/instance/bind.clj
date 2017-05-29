(ns onyx-java.instance.bind
  (:gen-class)
  (:require [onyx-java.instance.catalog :as cat])
  (:import [org.onyxplatform.api.java.instance BindUtils Loader OnyxFn]))

(def instances (atom {}))
(def loaders (atom {}))

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
      (if (contains? @instances k)
        (get @instances k) 
        (let [loader (Loader.)
              i (BindUtils/loadFn loader fq-class-name ctr-args)]
          (swap! loaders assoc k loader)
          (swap! instances assoc k i)
          i)))))
  
(defn method [id fq-class-name ctr-args segment]
  (let [inst-ifn (instance id fq-class-name ctr-args)]
    (inst-ifn segment)))

(defn release [task]
  (let [k (task-id task)]
    (if (contains? @instances k)
      (let [i (instance k)]
        (swap! loaders dissoc k)    
        (swap! instances dissoc k)))))

(defn release-all [catalog]
  (doseq [task catalog]
    (if (cat/instance? task)
      (release task))))

