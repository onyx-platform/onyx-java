(ns onyx-java.utility.helpers
    (:gen-class))

(def replace-pattern "{*}")

(defn qualify-string [original qualification]
    ;; Fully qualifies a package class using the given class name.
    (clojure.string/replace original replace-pattern qualification))

(defn get-object-name [classname]
    ;; Creates a **naiive** keyname for the specified class.
    (str (clojure.string/lower-case (subs classname 0 1)) (subs classname 1)))
