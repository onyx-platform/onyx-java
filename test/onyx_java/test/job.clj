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

; Tests a job that uses core async plugins
;
(defn run-job [catalog inputs] 
  (let [onyx-id (java.util.UUID/randomUUID)

        _ (println "Starting Onyx Env")
        env-conf (conf/build-env-config onyx-id)
        onyx-env (API/startEnv env-conf)
        _ (println "Started")

        _ (println "Starting Peer Group")
        peer-conf (conf/build-peer-config onyx-id)
        peer-group (API/startPeerGroup peer-conf)
        _ (println "Started." )

        _ (println "Starting Peers (3)")
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
               (.setFlowConditions fc)) ]
    (println "JOB: ===============")
    (flush)
    (pprint (.toArray job))
    (flush)
    (println " ===============")
    (try
      ; Bind inputs
      (AsyncLifecycles/bindInputs lc inputs)
      
      ; Start Job
      (API/submitJob  peer-conf job)

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
               (println "Shutdown Complete"))))))

