(ns onyx-java.utils.timbre
  (:gen-class)
  (:require [taoensso.timbre :as timbre]))


(defn info-message [message]
    (timbre/info message))

(defn debug-message [message]
    (timbre/debug message))

(defn warn-message [message]
    (timbre/warn message))

(defn trace-message [message]
    (timbre/trace message))

(defn error-message [message]
    (timbre/error message))
