(ns onyx-java.entity-test
    (:gen-class)
    (:require [onyx-java.utils.object :as obj]
              [clojure.test :refer [deftest is]]))

(deftest coerce-task
(let [expected {
            :onyx/name :in
            :onyx/plugin :onyx.plugin.core-async/input
            :onyx/type :input
            :onyx/medium :core.async
            :onyx/max-peers 1
            :onyx/batch-timeout 10
            :onyx/batch-size 50
            :onyx/doc "Reads segments from a core.async channel"
                }
    obj (obj/create-class-object! "org.onyxplatform.api.java.Task")]
 (.addParameter @obj "onyx/name" "in")
 (.addParameter @obj "onyx/plugin" "onyx.plugin.core-async/input")
 (.addParameter @obj "onyx/type" "input")
 (.addParameter @obj "onyx/medium" "core.async")
 (.addParameter @obj "onyx/max-peers" "1")
 (.addParameter @obj "onyx/batch-timeout" "10")
 (.addParameter @obj "onyx/batch-size" "50")
 (.addParameter @obj "onyx/doc" "Reads segments from a core.async channel")
 (is (= (.toCljMap @obj) expected))))

(deftest coerce-lifecycle
(let [expected {
             :lifecycle/task :loud-output
             :lifecycle/calls :onyx-simple.onyx.lifecycle/out-calls
             :core.async/id "lifecycle12345"
             :lifecycle/doc "Lifecycle for writing to a core.async chan"
                }
    obj (obj/create-class-object! "org.onyxplatform.api.java.Lifecycle")]
  (.addParameter @obj "lifecycle/task" "loud-output")
  (.addParameter @obj "lifecycle/calls" "onyx-simple.onyx.lifecycle/out-calls")
  (.addParameter @obj "core.async/id" "lifecycle12345")
  (.addParameter @obj "lifecycle/doc" "Lifecycle for writing to a core.async chan")
  (is (= (.toCljMap @obj) expected))))

(deftest coerce-peer-configuration
(let [expected {
                :onyx/tenancy-id "asdglsdkhasd23523"
                :zookeeper/address "127.0.0.1:2188"
                :onyx.peer/job-scheduler :onyx.job-scheduler/greedy
                :onyx.messaging/impl :aeron
                :onyx.messaging/peer-port 40200
                :onyx.messaging/bind-addr "localhost"
                }
    obj (obj/create-class-object! "org.onyxplatform.api.java.PeerConfiguration")]
(.addParameter @obj "onyx/tenancy-id" "asdglsdkhasd23523")
(.addParameter @obj "zookeeper/address" "127.0.0.1:2188")
(.addParameter @obj "onyx.peer/job-scheduler" "onyx.job-scheduler/greedy")
(.addParameter @obj "onyx.messaging/impl" "aeron")
(.addParameter @obj "onyx.messaging/peer-port" "40200")
(.addParameter @obj "onyx.messaging/bind-addr" "localhost")
(is (= (.toCljMap @obj) expected))))

(deftest coerce-env-configuration
(let [expected {
                :onyx/tenancy-id "asdglsdkhasd23523"
                :zookeeper/address "127.0.0.1:2188"
                :zookeeper/server? true
                :zookeeper.server/port 2188
                }
    obj (obj/create-class-object! "org.onyxplatform.api.java.EnvConfiguration")]
(.addParameter @obj "onyx/tenancy-id" "asdglsdkhasd23523")
(.addParameter @obj "zookeeper/address" "127.0.0.1:2188")
(.addParameter @obj "zookeeper/server?" "true")
(.addParameter @obj "zookeeper.server/port" "2188")
(is (= (.toCljMap @obj) expected))))

(deftest coerce-flow-condition
(let [expected {
           :flow/from :test-from
           :flow/doc "Flowcondition doc test"
          }
    obj (obj/create-class-object! "org.onyxplatform.api.java.FlowCondition")]
(.addParameter @obj "flow/from" "test-from")
(.addParameter @obj "flow/doc" "Flowcondition doc test")
(is (= (.toCljMap @obj) expected))))

(deftest coerce-trigger
(let [expected {
           :trigger/window-id :test-window-id
           :trigger/refinement :onyx-simple.onyx.refinement/trigger-refinement
           :trigger/on :timer
           :trigger/doc "Trigger doc test"
          }

obj (obj/create-class-object! "org.onyxplatform.api.java.Trigger")]
(.addParameter @obj "trigger/window-id" "test-window-id")
(.addParameter @obj "trigger/refinement" "onyx-simple.onyx.refinement/trigger-refinement")
(.addParameter @obj "trigger/on" "timer")
(.addParameter @obj "trigger/doc" "Trigger doc test")
(is (= (.toCljMap @obj) expected))))

(deftest coerce-window
(let [expected {
           :window/id :test-window-id
           :window/task :test-task
           :window/type :global
          }

obj (obj/create-class-object! "org.onyxplatform.api.java.Window")]
(.addParameter @obj "window/id" "test-window-id")
(.addParameter @obj "window/task" "test-task")
(.addParameter @obj "window/type" "global")
(is (= (.toCljMap @obj) expected))))
