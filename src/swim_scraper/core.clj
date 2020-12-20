(ns swim-scraper.core
  (:gen-class)
  (:require   [etaoin.api :refer :all]
              [etaoin.keys :as k]
              [java-time :as jtime]
              [java-time.format :as format]
              [config.core :refer [env]]))

(def javascript "const nodeIterator = document.createNodeIterator(
    // Node to use as root
     arguments[0],
  
    // Only consider nodes that are elements
    NodeFilter.SHOW_ELEMENT,
  
    // Object containing the function to use for the acceptNode method
    // of the NodeFilter
    {
      acceptNode: function(node) {
        // allow all nodes
          return NodeFilter.FILTER_ACCEPT
      }
    },
    false
  );
  
  let node;                
        
  while ((node = nodeIterator.nextNode())) {
    if(node.tagName === 'A'){
        return node;
    }
  }")

(defn next-day-string []
  (as-> (jtime/local-date) today
    (jtime/plus today (jtime/days 1))
    (format/format "EEE MMM dd, yyyy" today)
    (clojure.string/upper-case today)))

(defn find-appointment [element driver]
  (->>
   (get-element-text-el driver element)
   (#(and (clojure.string/includes? % (:swim-time env))
          (clojure.string/includes? % (:swim-activity env))))))

(defn query-class-register-button [driver]
  (->>  (query-all driver {:css "table.ListItem"})
        (filter
         #(find-appointment % driver))
        (first)
        (el->ref)
        (js-execute driver javascript)
        (vals)
        (first)
        ))

(defn sign-up []
  (with-chrome-headless {} chrome-driver
     (with-wait-timeout 60
       (doto chrome-driver
         (go  (:swim-url env))
         (wait-visible  {:id :txtLogin})
         (fill-multi  {:txtLogin    (:swim-name env)
                       :txtPassword (:swim-pass env)})
         (click :divLoginButton)
         (wait-visible {:id :spnActivity})
         (click :spnActivity)
         (wait-visible {:id :btnNext})
         (click :btnNext)
         (wait-has-text {:id :lblHeader} (next-day-string))
         (click-el (query-class-register-button chrome-driver))
         (wait-visible {:id :divPopupGotIt})
         (click :divPopupGotIt)))))

(defn -main
  [& args]
  (sign-up))
