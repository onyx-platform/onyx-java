(ns onyx-java.utils.async-lifecycles
  (:gen-class)
  (:require [clojure.core.async :refer [chan dropping-buffer sliding-buffer >!!]]
            [onyx.plugin.core-async :refer [take-segments!]]))

;; Defs --------------------------------
;;

(def input-channel-capacity 10000)
(def output-channel-capacity (inc input-channel-capacity))


;; Core Async-based lifecycles -------------------
;;
;; A pair of functions to get channels from a memoized reference.
;; We memoize it by requested ID because we want to get a reference
;; to read and write segments, and also so that peers can locate a reference
;; when they use the channel in the job.
;;

(defn channel-id-for [lifecycles task-name]
  (:core.async/id
   (->> lifecycles
        (filter #(= task-name (:lifecycle/task %)))
        (first))))

  ;; Inputs ------------------
  ;;

(def get-input-channel
  (memoize
   (fn [id]
     (chan input-channel-capacity))))

(defn bind-inputs! [lifecycles mapping]
  (doseq [[task segments] mapping]
    (let [in-ch (get-input-channel (channel-id-for lifecycles task))]
      (doseq [segment segments]
        (>!! in-ch segment))
      (>!! in-ch :done))))

(defn inject-in-ch [event lifecycle]
  {:core.async/chan (get-input-channel (:core.async/id lifecycle))})

(def in-calls
  {:lifecycle/before-task-start inject-in-ch})

(defn in-lifecycles [task-name]
  [{:lifecycle/task (keyword task-name) 
    :core.async/id (java.util.UUID/randomUUID)
    :lifecycle/calls :onyx-java.utils.async-lifecycles/in-calls}
   {:lifecycle/task (keyword task-name) 
    :lifecycle/calls :onyx.plugin.core-async/reader-calls}])


  ;; Outputs ----------------
  ;;

(def get-output-channel
  (memoize
   (fn [id]
     (chan (sliding-buffer output-channel-capacity)))))

(defn collect-outputs! [lifecycles output-tasks]
  (->> output-tasks
       (map #(get-output-channel (channel-id-for lifecycles %)))
       (map take-segments!)
       (zipmap output-tasks)))

(defn inject-out-ch [event lifecycle]
  {:core.async/chan (get-output-channel (:core.async/id lifecycle))})

(def out-calls
  {:lifecycle/before-task-start inject-out-ch})

(defn out-lifecycles [task-name]
  [{:lifecycle/task (keyword task-name)
    :lifecycle/calls :onyx-java.utils.async-lifecycles/out-calls
    :core.async/id (java.util.UUID/randomUUID) }
   {:lifecycle/task (keyword task-name)
    :lifecycle/calls :onyx.plugin.core-async/writer-calls}])


  ;; Drop Channel ------------
  ;;

(def get-drop-channel
  (memoize
   (fn [id]
     ; Drops async on the floor. 
     ; Used with side-effect-ful
     ; jobs.
     (chan (dropping-buffer 1)))))

(defn inject-drop-ch [event lifecycle]
  {:core.async/chan (get-drop-channel (:core.async/id lifecycle))})

(def drop-calls
  {:lifecycle/before-task-start inject-drop-ch})

(defn drop-lifecycles [task-name]
  [{:lifecycle/task (keyword task-name)
    :lifecycle/calls :onyx-java.utils.async-lifecycles/drop-calls
    :core.async/id (java.util.UUID/randomUUID)}
   {:lifecycle/task (keyword task-name)
    :lifecycle/calls :onyx.plugin.core-async/writer-calls}])


