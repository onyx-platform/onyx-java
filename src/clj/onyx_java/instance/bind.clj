(ns onyx-java.instance.bind
  (:gen-class)
  (:import org.onyxplatform.api.java.instance.Loader))

(defn exists?  [c]
  (let [loader (.getContextClassLoader (Thread/currentThread))]
    (try
      (Class/forName c false loader)
      true
      (catch ClassNotFoundException cnfe false))))

(def instances (atom {}))

(defn instance [id fq-class-name ctr-args]
  (let [k (keyword (str id))]
    (if (contains? @instances k)
      (get @instances k) 
      (let [i (Loader/loadOnyxMethod fq-class-name ctr-args)]
        (swap! instances assoc k i)
        i))))

(defn method [id fq-class-name ctr-args segment]
  (let [inst-ifn (instance id fq-class-name ctr-args)]
    (inst-ifn segment)))

