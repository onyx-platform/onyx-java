(ns onyx-java.test.single-javafn.catalog
  (import [org.onyxplatform.api.java Catalog]
          [org.onyxplatform.api.java.utils AsyncCatalog MapFns])
  (:require [clojure.test :refer [deftest is]]))


(defn build-catalog []
  )

(deftest valid-catalog?
  (let [cat (build-catalog)]
    (is true)))

