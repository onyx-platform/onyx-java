(ns onyx-java.test.single-javafn.job
  (:import [org.onyxplatform.api.java 
            EnvConfiguration PeerConfiguration 
            Job Catalog Lifecycles Workflow FlowConditions])
  (:require [clojure.test :refer [deftest is]]
            [onyx-java.test.single-javafn.catalog :as c]
            [onyx-java.test.single-javafn.lifecycles :as lc]
            [onyx-java.test.single-javafn.workflow :as wf]))


; Tests a simple job that calls one
; java fn using core async plugins
;
(deftest valid-job?
  (is true)
  )

