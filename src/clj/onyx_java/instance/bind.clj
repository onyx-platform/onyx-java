(ns onyx-java.instance.bind
  (:gen-class)
  #_(:import java.lang.reflect.Constructor))

(defn exists?  [c]
  (let [loader (.getContextClassLoader (Thread/currentThread))]
    (try
      (Class/forName c false loader)
      true
      (catch ClassNotFoundException cnfe false))))


(defn new-instance [fq-class-name ctr-args]
  (if-not (exists? fq-class-name)
    nil ; Throw ClassNotFoundException?
    (let [fn-clazz  (Class/forName fq-class-name) 
          ; Create IPersistentMap ctr
          ipm-ctr-array (into-array  [clojure.lang.IPersistentMap])
          inst-ctr (.getConstructor fn-clazz ipm-ctr-array) 
          inst-args () ; into array so its [Ljava.lang.Object
          ]
      (.newInstance inst-ctr inst-args))))

(def instances (atom {}))

(defn instance [id fq-class-name ctr-args]
  (let [k (keyword (str id))]
    (if (contains? @instances k)
      (get @instances k)
      (let [i (new-instance fq-class-name ctr-args)]
        (swap! instances assoc k i)
        i))))

(defn method [id fq-class-name ctr-args segment]
  (let [inst-ifn (instance id fq-class-name ctr-args)]
    (inst-ifn segment)))

