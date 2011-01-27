(ns clj-plist.core
  (:import (org.joda.time DateTime)
           (org.apache.commons.codec.binary Base64))
  (:gen-class))

(require 'clojure.xml)

(defn read-file
  [filename]
  (clojure.xml/parse (java.io.File. filename)))

(defn first-content
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
  [p]
  (content (first-content p)))
