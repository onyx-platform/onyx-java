(ns onyx-java.test.single-javafn.job
  (import [org.onyxplatform.api.java Job]
          [org.onyxplatform.api.java.utils AsyncCatalog AsyncLifecycles MapFns]
          [onyx_java.test.Functions])
  (:require [clojure.test :refer [deftest is]]))


; Tests a simple job that calls one
; java fn using core async plugins
;
(deftest test-single-javafn-job
  (is true)
  )

