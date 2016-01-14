(defproject com.github.bdesham/clj-plist "0.10.0"
            :description "Library to parse property list (.plist) files"
            :url "http://github.com/bdesham/clj-plist"
            :license {:name "Eclipse Public License"
                      :url "http://www.eclipse.org/legal/epl-v10.html"
                      :comments "Same as Clojure"}
            :dependencies [[org.clojure/clojure "1.5.0"]
                           [joda-time "1.6"]
                           [commons-codec/commons-codec "1.4"]]
            :plugins [[lein-test-out "0.3.1" :exclusions [org.clojure/clojure]]])
