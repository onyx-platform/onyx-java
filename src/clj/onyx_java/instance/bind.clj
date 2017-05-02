(ns onyx-java.instance.bind
  (:gen-class)
  (:require [onyx-java.instance.catalog :as cat])
  (:import org.onyxplatform.api.java.instance.Loader))

(defn exists?  [c]
  (let [loader (.getContextClassLoader (Thread/currentThread))]
    (try
      (Class/forName c false loader)
      true
      (catch ClassNotFoundException cnfe false))))

(def instances (atom {}))

(defn- keyname [id]
  (keyword (str id)))

(defn instance [id fq-class-name ctr-args]
  (let [k (keyname id)]
    (if (contains? @instances k)
      (get @instances k) 
      (let [i (Loader/loadOnyxFn fq-class-name ctr-args)]
        (swap! instances assoc k i)
        i))))

(defn method [id fq-class-name ctr-args segment]
  (let [inst-ifn (instance id fq-class-name ctr-args)]
    (inst-ifn segment)))

(defn release [task]
  (let [k (keyname (cat/id task))]
    (if (contains? @instances k)
      (swap! instances dissoc k))))

(defn release-all [catalog]
  (doseq [task catalog]
    (if (cat/instance? task)
      (release task))))

