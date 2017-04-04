(ns onyx-java.test.sample-test
    (:require   [clojure.test :refer [deftest is]]
                [org.onyxplatform.api.java :as api]))

(deftest basic-test
    ;;testing very basic testing functionality in package
    (is (= 4 (+ 2 2)))
    )

;;(deftest java-test
    ;;testing basic usage of the java package.
    ;;(let [peerConfig (api.PeerConfiguration.)]
    ;;    (is (= .testMethod )))
    ;;)
