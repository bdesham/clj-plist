(ns com.github.bdesham.clj-plist
  (:import (org.apache.commons.codec.binary Base64)
           (org.joda.time DateTime))
  (:require clojure.xml))

(defn- first-content
  [c]
  (first (c :content)))

(defmulti content (fn [c] (c :tag)))

(defmethod content :array
  [c]
  (apply vector (for [item (c :content)] (content item))))

(defmethod content :data
  [c]
  (.decode (Base64.) (first-content c)))

(defmethod content :date
  [c]
  (DateTime. (first-content c)))

(defmethod content :dict
  [c]
  (apply hash-map (for [item (c :content)] (content item))))

(defmethod content :false
  [c]
  false)

(defmethod content :integer
  [c]
  (Long. (first-content c)))

(defmethod content :key
  [c]
  (first-content c))

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
  [source]
  (content (first-content (clojure.xml/parse source))))
