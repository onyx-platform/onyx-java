;;THIS IS CURRENTLY UNDER DEVELOPMENT, ALTHOUGH ANY INSTRUCTIONS PUT HERE WILL WORK.
;;ULTIMATELY EVERYTHING WILL BE MOVED TO THE PROPER DIRECTORY AND ALL FUNCTIONALITY
;;WILL BE PROPERLY SORTED.
;;use: cd into the main directory. run 'lein repl'. the default namespace should
;currently be onyx-java.test/
;;useage: for a specific class named "Entity" with a edn spec named "Entity.edn",
;;run (prepare-object "Entity").
;;to get the clojure map, run (get-clojure-map "Entity")
;;all entities can be loaded simultaneously and will be held as atoms in
;;a map for further use and investigation.
;;as an example: try copying/pasting the following (in lein repl):
;; -------
;; (prepare-object "PeerConfiguration")
;; (get-clojure-map "PeerConfiguration")
;; -----
;; you should see the onyx corrected version of the PeerConfiguration model.
;; when we actually test these entities, we will be testing against another
;; module pulled and assembled directly from the onyx model, so if the model
;; changes, it is what will allow knowledge that tests have broken.

(ns onyx-java.test
    (:gen-class))

;;Following deal with Entry Prep
(def spec-pattern "test/resources/{edn}.edn")

(def replace-spec-pattern "{edn}")

(defn get-qualified-spec [classname]
    (clojure.string/replace spec-pattern replace-spec-pattern classname))

(defn read-spec-map [spec]
    (read-string (slurp spec)))

(defn map-to-vectors [map]
    (into [] map))

(defn keyword-to-string [keyword]
    (subs (str keyword) 1))

(defn prepare-java-entry [entry-vector]
    (assoc entry-vector 0 (keyword-to-string (first entry-vector))))

(defn prepare-java-entries [keyword-vectors]
    (vec (map prepare-java-entry keyword-vectors)))

(defn get-parameter-vectors [classname]
    (prepare-java-entries (map-to-vectors
        (read-spec-map (get-qualified-spec classname)))))

;;following deal with Java Interop
(def class-pattern "org.onyxplatform.api.java.{class}")

(def replace-classname-pattern "{class}")

(defn get-classpath [classname]
    (clojure.string/replace class-pattern replace-classname-pattern classname))

(defn meta-factory [classname & types]
  (let [args (map #(with-meta (symbol (str "x" %2)) {:tag %1}) types (range))]
    (eval `(fn [~@args] (new ~(symbol classname) ~@args)))))

(defn get-class [classpath]
    (let [class-factory (meta-factory classpath)]
    (class-factory)))

(defn get-object! [classpath]
    (atom (get-class classpath)))

(defn get-var [classname]
    (str (clojure.string/lower-case (subs classname 0 1)) (subs classname 1)))

(def object-map {})

(defn add-object [classname]
    (def object-map (assoc object-map (get-var classname)
                    (get-object! (get-classpath classname)))))

(defn add-parameter-factory [classname]
    (let [c (get-var classname)]
    (fn [parameter-vector] (.addParameter (deref (get object-map c))
        (get parameter-vector 0) (get parameter-vector 1)))))


(defn get-clojure-map [classname]
    (.toCljMap (deref (get object-map (get-var classname)))))

(defn prepare-object [classname]
    (add-object classname)
    (def add-parameter (add-parameter-factory classname))
    (def parameter-vectors (get-parameter-vectors classname))
    (map add-parameter parameter-vectors))
