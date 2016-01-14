(ns com.github.bdesham.clj-plist-test
  (:require
    [clojure.java.io :refer (file)]
    [clojure.test :refer :all]
    [clojure.string :as str]
    [com.github.bdesham.clj-plist :refer :all])
  (:import
    [org.joda.time DateTime]
    [javax.xml.bind DatatypeConverter]))

(defn- b64
  "Convert a base64 encoded string to a sequence of bytes"
  [s]
  (seq (DatatypeConverter/parseBase64Binary s)))

(deftest can-parse-example
  (let [parsed (parse-plist (file "examples/Example.plist"))]
    (is (= {"Array example"  [2 3.14159],
            "Boolean example" true,
            "Data example" (b64 "YWJjZGVmZw==")
            "Date example" (DateTime.  "1969-07-20T08:56:00.000+01:00")
            "String example" "This is just some uninteresting text"}
           (update-in parsed ["Data example"] seq)))))


(deftest can-modify-keys
  (let [parsed (parse-plist (file "examples/Example.plist")
                            {:keyword-fn
                             #(-> % str str/lower-case (str/replace #"\s+" "-") keyword)})]
    (is (= {:array-example  [2 3.14159],
            :boolean-example true,
            :data-example (b64 "YWJjZGVmZw==")
            :date-example (DateTime.  "1969-07-20T08:56:00.000+01:00")
            :string-example "This is just some uninteresting text"}
           (update-in parsed [:data-example] seq)))))
