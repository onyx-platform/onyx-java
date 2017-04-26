(ns onyx-java.instance.catalog 
  (:gen-class))

(defn create-method [{:keys [task-name 
                             batch-size batch-timeout   
                             fqclassname ctr-args]}]
  {:onyx/name (keyword task-name)
   :onyx/fn :onyx-java.instance.bind/method
   :onyx/type :function
   :onyx/batch-size batch-size   
   :onyx/batch-timeout batch-timeout

   ; Instance binding bootstrapping
   :java-instance/id (java.util.UUID/randomUUID)
   :java-instance/class fqclassname
   :java-instance/ctr-args ctr-args
   :onyx/params [:java-instance/id
                 :java-instance/class
                 :java-instance/ctr-args] })

