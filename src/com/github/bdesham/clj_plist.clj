(ns com.github.bdesham.clj-plist
  (:import (org.apache.commons.codec.binary Base64)
           (org.joda.time DateTime))
  (:require clojure.xml))

(def ^:private ^:dynamic *keyword-fn*
  identity)

(defn- first-content
  [c]
  (first (c :content)))

(defmulti content :tag)

(defmethod content :array
  [c]
  (apply vector (map content (c :content))))

(defmethod content :data
  [c]
  (.decode (Base64.) (first-content c)))

(defmethod content :date
  [c]
  (DateTime. (first-content c)))

(defmethod content :dict
  [c]
  (apply hash-map (map content (c :content))))

(defmethod content :false
  [c]
  false)

(defmethod content :integer
  [c]
  (Long. (first-content c)))

(defmethod content :key
  [c]
  (*keyword-fn* (first-content c)))

(defmethod content :real
  [c]
  (Double. (first-content c)))

(defmethod content :string
  [c]
  (first-content c))

(defmethod content :true
  [c]
  true)

(defn parse-plist
  ([source]
   (content (first-content (clojure.xml/parse source))))
  ([source {:keys [keyword-fn]}]
   (binding [*keyword-fn* keyword-fn]
     (parse-plist source))))
