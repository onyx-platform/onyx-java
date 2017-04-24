(ns onyx-java.test.config
  (:import [org.onyxplatform.api.java 
            EnvConfiguration PeerConfiguration]
           [org.onyxplatform.api.java.utils MapFns])
  (:require [clojure.test :refer [deftest is]]
            [clojure.java.io :refer [resource]]))

(defn build-env-config [onyx-id]
  (let [om (MapFns/fromResources "env-config.edn") ]
    (EnvConfiguration. onyx-id om)))

(defn build-peer-config [onyx-id]
  (let [om (MapFns/fromResources "dev-peer-config.edn")]
    (PeerConfiguration. onyx-id om)))

(defn env-expected [onyx-id] 
  (assoc  (-> "env-config.edn" resource slurp read-string) :onyx/tenancy-id onyx-id))

(deftest valid-env?
  (let [onyx-id (java.util.UUID/randomUUID)
        ec (build-env-config onyx-id) ]
    (is (= (env-expected onyx-id) (.toMap ec)))))

(defn peer-expected [onyx-id]
  (assoc (-> "dev-peer-config.edn" resource slurp read-string) :onyx/tenancy-id onyx-id))

(deftest valid-peer?
  (let [onyx-id (java.util.UUID/randomUUID)
        pc (build-peer-config onyx-id) ]
    (is (= (peer-expected onyx-id) (.toMap pc)))))

