(defproject swim-scraper "0.1.0-SNAPSHOT"
  :description "An application for automatically registering for a swim lane on a website that shall remain anonymous"
  :url ""
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[ org.clojure/clojure "1.10.1"]
                 [etaoin "0.4.0"]
                 [clojure.java-time "0.3.2"]
                 [yogthos/config "1.1.7"]
                 ]
  :main ^:skip-aot swim-scraper.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true" "-Dconfig=config.edn"]}})
