(ns onyx-java.utils.timbre
  (:gen-class)
  (:require [taoensso.timbre :as timbre]))


(defn info-message [message]
    (timbre/info message))
