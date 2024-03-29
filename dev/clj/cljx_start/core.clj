(ns cljx-start.core
  (:require [compojure.core :refer :all]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.middleware.file         :as file]
            [ring.middleware.file-info    :as file-info]
            [ring.middleware.content-type :as content-type]
            [ring.middleware.json         :as json]
            [ring.util.response           :as response]
            [hiccup.page              :refer [html5 include-js include-css]]))

(defn wrap-nocache 
  "completely disable all caching on the client, helps with source maps accuracy and update to dateness"
  [handler]
  (fn [request]
    (let [response (handler request)]
    (-> response
      (assoc-in [:headers "Cache-Control"] "no-cache, no-store, must-revalidate")
      (assoc-in [:headers "Pragma"] "no-cache")
      (assoc-in [:headers "Expires"] "0")))))

(defroutes app-routes
  
  (GET "/" [] 
       (html5 
         [:head 
          [:title "toolbelt dev"]]
         [:body 
          [:div "toolbelt dev loaded"]
          (include-js
            "goog/base.js"
            "toolbelt.js")
          [:script "goog.require('toolbelt.dev');"]]))
  
  (route/resources "/")
  
  (route/not-found "Not Found"))

(def app
  (-> (handler/site app-routes)
    (file/wrap-file "out")
    (file-info/wrap-file-info)
    (content-type/wrap-content-type)
    json/wrap-json-body
    json/wrap-json-params
    json/wrap-json-response
    wrap-nocache))
  
