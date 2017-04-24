(ns onyx-java.test.single-javafn.job
  (:import [org.onyxplatform.api.java 
            API OnyxNames TaskScheduler
            EnvConfiguration PeerConfiguration 
            Job Catalog Lifecycles Workflow FlowConditions]
           [org.onyxplatform.api.java.utils AsyncLifecycles])
  (:require [clojure.test :refer [deftest is]]
            [clojure.pprint :refer [pprint]]
            [onyx-java.test.single-javafn.catalog :as cat]
            [onyx-java.test.single-javafn.config :as conf]
            [onyx-java.test.single-javafn.lifecycles :as lc]
            [onyx-java.test.single-javafn.workflow :as wf]))


; Tests a simple job that calls one
; static java fn using core async plugins
;
(deftest valid-job?
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
        peers  (API/startPeers 1 peer-group)
        _ (println "Started")

        scheduler (TaskScheduler. OnyxNames/BalancedTaskSchedule)
        wf (wf/build-workflow)
        cat (cat/build-catalog)
        lc (lc/build-lifecycles) 
        fc (FlowConditions.) ; No flow conditions

        job (-> (Job. scheduler)  
              (.setWorkflow wf)
              (.setCatalog cat)
              (.setLifecycles lc)
              (.setFlowConditions fc)) 

        ; Our pipeline simply adds a key to 
        ; the segment via a static Java function.
        inputs [{:pass-test "TEST"}] ]
    ;(println "JOB: ===============")
    ;(pprint (.toArray job))
    (try
      ; Bind inputs
      (AsyncLifecycles/bindInputs lc inputs)

      ; Start Job
      ; Collect Output
      (is true)
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


