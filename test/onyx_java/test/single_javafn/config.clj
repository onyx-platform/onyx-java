(ns onyx-java.test.single-javafn.config
  (:import [org.onyxplatform.api.java 
            EnvConfiguration PeerConfiguration]
           [org.onyxplatform.api.java.utils MapFns])
  (:require [clojure.test :refer [deftest is]]))


(defn build-env-config [onyx-id]
  (let [om (MapFns/fromResources "env-config.edn") ]
    (EnvConfiguration. onyx-id om)))

(defn build-peer-config [onyx-id]
  (let [om (MapFns/fromResources "dev-peer-config.edn")]
    (PeerConfiguration. onyx-id om)))

(deftest valid-env?
  (let [onyx-id (java.util.UUID/randomUUID)
        ec (build-env-config onyx-id) 
        expected {} ]
    (is true)))

(deftest valid-peer?
  (let [onyx-id (java.util.UUID/randomUUID)
        pc (build-peer-config onyx-id) 
        expected {} ]
    (is true)))

