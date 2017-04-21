(ns onyx-java.main
  (:gen-class) 
  (:require [clojure.java.io :as io]
    [onyx-java.utils.map-fns :as mf]
            [onyx-java.utils.async-catalog :as cat]
            [onyx-java.utils.async-lifecycles :as life])
  (:import [org.onyxplatform.api.java.utils AsyncCatalog AsyncLifecycles MapFns VectorFns]
           [org.onyxplatform.api.java OnyxMap Catalog Lifecycles EnvConfiguration PeerConfiguration]
           [clojure.lang PersistentHashMap]))


