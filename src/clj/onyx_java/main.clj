(ns onyx-java.main
  (:gen-class) 
  (:require [clojure.java.io :as io]
            [onyx-java.instance.bind :as b]
            )
  (:import [org.onyxplatform.api.java.utils 
            AsyncCatalog AsyncLifecycles MapFns VectorFns]
           [org.onyxplatform.api.java.instance Loader CatalogUtils]
           [org.onyxplatform.api.java 
            OnyxNames TaskScheduler OnyxMap Catalog 
            Lifecycles EnvConfiguration PeerConfiguration]
           [clojure.lang PersistentHashMap]))


