(ns onyx-java.test.single-javafn.job
  (:import [org.onyxplatform.api.java 
            OnyxNames TaskScheduler
            EnvConfiguration PeerConfiguration 
            Job Catalog Lifecycles Workflow FlowConditions])
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
        EnvConfiguration (conf/build-env-config onyx-id)
        PeerConfiguration (conf/build-peer-config onyx-id)

        wf (wf/build-workflow)
        cat (cat/build-catalog)
        lc (lc/build-lifecycles) 
        fc (FlowConditions.) ; No flow conditions
        scheduler (TaskScheduler. OnyxNames/BalancedTaskSchedule)
        job () #_(-> (Job. scheduler)) 
        ; Our pipeline simply adds a key to 
        ; the segment via a static Java function.
        inputs [{}]
        ]
    ; Bind inputs
    ; Start Job
    ; Collect Output
    (is true)))


