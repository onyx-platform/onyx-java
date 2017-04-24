(ns onyx-java.test.job
  (:import [org.onyxplatform.api.java 
            API OnyxNames TaskScheduler
            EnvConfiguration PeerConfiguration 
            Job Catalog Lifecycles Workflow FlowConditions]
           [org.onyxplatform.api.java.utils AsyncLifecycles])
  (:require [clojure.pprint :refer [pprint]]
            [onyx-java.utils.async-lifecycles :as clj-lc]
            [onyx.api :as onyx]
            [onyx-java.test.config :as conf]
            [onyx-java.test.lifecycles :as lc]
            [onyx-java.test.workflow :as wf]))



; Tests a simple job that calls one fn using core async plugins
;
(defn run-clj-job [catalog inputs] 
  (let [onyx-id (java.util.UUID/randomUUID)

        env-conf (conf/env-expected onyx-id)
        _ (println "Starting Onyx Env")
        onyx-env (onyx/start-env env-conf)
        _ (println "Started")

        peer-conf (conf/peer-expected onyx-id)
        _ (println "Starting Peer Group")
        peer-group (onyx/start-peer-group peer-conf)
        _ (println "Started")
        _ (println "Starting Peers (1)")
        peers  (onyx/start-peers 3 peer-group)
        _ (println "Started")

        scheduler :onyx.task-scheduler/balanced
        wf wf/expected 
        lc lc/expected  
        fc []  

        job {:task-scheduler scheduler
             :workflow wf
             :catalog catalog
             :lifecycles lc
             :flow-conditions fc } ]
    (println "JOB: ===============")
    (pprint job)
    (try
      ; Bind inputs
      (clj-lc/bind-inputs! lc inputs)
      
      ; Start Job
      (onyx/submit-job peer-conf job)

      ; Collect Output and return it
      (clj-lc/collect-outputs! lc [:out])

    (catch Exception e (do 
                         (.printStackTrace e))) 
    (finally (do
               (println "Stopping Peers")
               (doseq [v-peer peers]
                 (API/shutdownPeer v-peer)) 
               (println "Stopped")
               (println "Stopping Peer Group")
               (API/shutdownPeerGroup peer-group)
               (println "Stopped")
               (println "Stopping Onyx Env")
               (API/shutdownEnv onyx-env)
               (println "Shutdown Complete")
               )))))



; Tests a simple job that calls one
; static java fn using core async plugins
;
(defn run-job [catalog inputs] 
  (let [onyx-id (java.util.UUID/randomUUID)

        env-conf (conf/build-env-config onyx-id)
        _ (println "Starting Onyx Env")
        onyx-env (API/startEnv env-conf)
        _ (println "Started")

        peer-conf (conf/build-peer-config onyx-id)
        _ (println "Starting Peer Group")
        peer-group (API/startPeerGroup peer-conf)
        _ (println "Started")
        _ (println "Starting Peers (1)")
        peers  (API/startPeers 3 peer-group)
        _ (println "Started")

        scheduler (TaskScheduler. OnyxNames/BalancedTaskSchedule)
        wf (wf/build-workflow)
        lc (lc/build-lifecycles) 
        fc (FlowConditions.) ; No flow conditions

        job (-> (Job. scheduler)  
              (.setWorkflow wf)
              (.setCatalog catalog)
              (.setLifecycles lc)
              (.setFlowConditions fc))]
    (println "JOB: ===============")
    (pprint (.toArray job))
    (try
      ; Bind inputs
      (AsyncLifecycles/bindInputs lc inputs)
      
      ; Start Job
      (API/submitJob peer-conf job)

      ; Collect Output and return it
      (AsyncLifecycles/collectOutputs lc "out") 

    (catch Exception e (do 
                         (.printStackTrace e))) 
    (finally (do
               (println "Stopping Peers")
               (doseq [v-peer peers]
                 (API/shutdownPeer v-peer)) 
               (println "Stopped")
               (println "Stopping Peer Group")
               (API/shutdownPeerGroup peer-group)
               (println "Stopped")
               (println "Stopping Onyx Env")
               (API/shutdownEnv onyx-env)
               (println "Shutdown Complete")
               )))))



