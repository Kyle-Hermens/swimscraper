(ns swim-scraper.core
  (:gen-class)
  (:require   [etaoin.api :refer :all]
              [etaoin.keys :as k]
              [java-time :as jtime]
              [java-time.format :as format]
              [config.core :refer [env]]
              [clojure.string :as s]
              ))

(defn  get-element-containing-text [driver query text]
  (as-> driver val
    (query-all val query)
    (filter #(clojure.string/includes? (get-element-text-el driver %) text) val)
    (first val)))



(defn get-row-for-time-slot [driver]
  (->> (query-all driver {:css "#classSchedule-mainTable tr"})
       (filter (fn [element] (s/includes? (get-element-text-el driver element) (:swim-time env))))
       (filter (fn [element] (s/includes? (get-element-text-el driver element) (:swim-activity env))))
       (first)
       ))

(defn click-signup-button [driver]
  (as-> (get-row-for-time-slot driver) val
    (children driver val {:css "input.SignupButton"})
    (first val)
    (click-el driver val)))




(defn sign-up []
  (with-firefox-headless {} driver
  (with-wait-timeout 60
    (doto driver
      (go (:swim-url env))
      (wait-visible {:id :su1UserName})
      (fill-multi {:su1UserName (:swim-name env)
                   :su1Password (:swim-pass env)})
      (click :btnSu1Login)
      (wait 3) ;without this wait, the page load kills a web request which causes an exception.
      (wait-visible {:class :tab-table})
      (click-el (get-element-containing-text driver {:css "td > a"} (:swim-location env)))
      (click :day-tog-c)
      (click :day-arrow-r)
      (click-signup-button)
      (click :SubmitEnroll2)
      )
    
    )
  )

)






(defn -main
  [& args]
  (sign-up))
