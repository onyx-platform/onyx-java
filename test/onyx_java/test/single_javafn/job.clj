(ns onyx-java.test.single-javafn.job
  (:import [org.onyxplatform.api.java 
            API OnyxNames TaskScheduler
            EnvConfiguration PeerConfiguration 
            Job Catalog Lifecycles Workflow FlowConditions]
           [onyx_java.test.Functions]
           )
  (:require [clojure.test :refer [deftest is]]
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
        onyx-env () ; (API/startEnv env-conf)

        peer-conf (conf/build-peer-config onyx-id)
        peer-group () ; (API/startPeerGroup peer-config)
        peers () ; (API/startPeers 1 peer-group)

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
        inputs [{}] ]
    (try
      ; Test that the Env, peer group, and peer(s) have successfully fired up
      ; Bind inputs
      ; Start Job
      ; Collect Output
      (is true)
    (catch Exception e (do )) 
    (finally (do
               #_(doseq [v-peer peers]
                 (API/shutdownPeer v-peer)) 
               #_(API/shutdownPeerGroup peer-group)
               #_ (API/shutdownEnv onyx-env)
               )))))


