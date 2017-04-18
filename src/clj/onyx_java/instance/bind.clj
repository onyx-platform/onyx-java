(ns onyx-java.instance.bind
  (:gen-class) ; NS needs to be gen'ed for Onyx to find
  #_(:require [onyx-java.instance.onyx-instance :as i])
  )

; Functionality:
;
;  - Memory management of instances
;
;  - Dynamically loads instances at runtime
;    for use of arbitrary methods on instances
;
;  - Performs the keyword to vanilla string 
;    conversion into a java HashMap
;
; Answered questions
;
;  - We need an abstract class to constrain the constructor 
;  - Gen-class for object construction
;
; Open questions
;
;  - proxy for method dispatch?
;

; Do we want to store more than just the 
; instance?
;
; like class ns?
;
; Don't think so as the the lifecycle
; additions to the segment pass all 
; this along. 
; 
(def instances (atom {}))

; Can we define the base class via gen-class?
; (looks like only if its concrete. However, it
; really doesn't need to *do* anything?)
;
; Hmmmmmmmmmmmmm
;
; Would be nice to have easy affordances for
; segment io.  Don't wanna force clients
; into having to over-ride methods. 
;
; Might make more sense to have 
; the abstract base class access
; translator's defined here instead?
;
; Really its mainly going to be java 
; classes that will be using our
; convertion libs
;
(defn new-instance [id crtr-args]
  ; gen-class goes here
  ;
  ; Should add helper fns that are technically
  ; offered by the ns or squirrel them away 
  ; in the object instance?
  ;
  ; Can you make methods be class-static this way?
  ;
  (let [i ()]
    
    ))

(defn- instance [{:keys [instance-id instance-ctr-args] :as segment}]
  
  )


(defn method [segment]
  ; Class is pulled via instance-id
  ; 
  (let [i ()]
    
    )
  )




