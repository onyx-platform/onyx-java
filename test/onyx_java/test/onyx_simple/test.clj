(ns onyx-java.test.onyx-simple.test
    (:gen-class)
    (:require [clojure.java.io :refer [resource]]
              [clojure.test :refer [deftest is]]
              [onyx-java.test.onyx-simple.functions])
    (:import [org.onyxplatform.api.java EnvConfiguration PeerConfiguration
              Job Catalog Task Lifecycles Workflow FlowConditions
              API TaskScheduler]
             [org.onyxplatform.api.java.utils AsyncCatalog AsyncLifecycles
              MapFns]))

(def onyx-id (java.util.UUID/randomUUID))

(defn create-task-scheduler []
    (let [task-scheduler (TaskScheduler. "onyx.task-scheduler/balanced")]
        task-scheduler))

(defn create-env-config []
    (let [om (MapFns/fromResources "onyx_simple/config/env_configuration.edn")
          env-config (EnvConfiguration. onyx-id om)]
        env-config))

(defn create-peer-config []
    (let [om (MapFns/fromResources "onyx_simple/config/peer_configuration.edn")
          peer-config (PeerConfiguration. onyx-id om)]
        peer-config))

(defn create-catalog []
    (let [catalog (Catalog.)]
        (-> catalog
            (.addTask (Task. (MapFns/fromResources "onyx_simple/tasks/task_loud.edn")))
            (.addTask (Task. (MapFns/fromResources "onyx_simple/tasks/task_mixed_case.edn")))
            (.addTask (Task. (MapFns/fromResources "onyx_simple/tasks/task_question.edn")))
            (.addTask (Task. (MapFns/fromResources "onyx_simple/tasks/task_split_by_spaces.edn")))
            (AsyncCatalog/addInput  "in" 10 50)
            (AsyncCatalog/addOutput  "question-output" 10 50)
            (AsyncCatalog/addOutput  "loud-output" 10 50)
            )))

(defn create-workflow []
    (let [workflow (Workflow.)]
    (-> workflow
        (.addEdge "in" "split-by-spaces")
        (.addEdge "split-by-spaces" "mixed-case")
        (.addEdge "mixed-case" "loud")
        (.addEdge "mixed-case" "question")
        (.addEdge "loud" "loud-output")
        (.addEdge "question" "question-output")
        )))

(defn create-lifecycles []
    (let [lifecycles (Lifecycles.)]
        (-> lifecycles
            (AsyncLifecycles/addInput "in")
            (AsyncLifecycles/addOutput "loud-output")
            (AsyncLifecycles/addOutput "question-output")
            )))

(defn create-flow-conditions []
    (let [flow-conditions (FlowConditions.)]
        flow-conditions))

(defn create-input []
    (let [input-segments (-> "onyx_simple/data/input.edn" resource slurp read-string)]
        input-segments))

(defn create-job []
    (let [task-scheduler (create-task-scheduler)
          catalog (create-catalog)
          workflow (create-workflow)
          lifecycles (create-lifecycles)
          flow-conditions (create-flow-conditions)
          job (-> (Job. task-scheduler)
              (.setCatalog catalog)
              (.setWorkflow workflow)
              (.setLifecycles lifecycles)
              (.setFlowConditions flow-conditions))]
     job))

(defn start-peer-group [peer-config]
    (API/startPeerGroup peer-config))

(defn run-job []
    (let [env-config (create-env-config)
          peer-config (create-peer-config)
          onyx-env (API/startEnv env-config)
          peer-group (API/startPeerGroup peer-config)
          peers (API/startPeers 7 peer-group)
          job (create-job)
          input-segments (create-input)]
          (try
            (AsyncLifecycles/bindInputs (.getLifecycles job) input-segments)
            (API/submitJob  peer-config job)
            (let [q-output (AsyncLifecycles/collectOutputs (.getLifecycles job) "question-output")
                  l-output (AsyncLifecycles/collectOutputs (.getLifecycles job) "loud-output")]
                  (merge q-output l-output))
          (catch Exception e (do
                               (.printStackTrace e)))
          (finally (do
                     (doseq [v-peer peers]
                       (API/shutdownPeer v-peer))
                     (API/shutdownPeerGroup peer-group)
                     (API/shutdownEnv onyx-env))))
          ))

(deftest test-job []
    (let [outputs (run-job)
          expected (-> "onyx_simple/data/output.edn" resource slurp read-string)]
    (is (= outputs expected))))
