(ns marassapapupphakani.core
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            [reitit.core :as rei]
            [reitit.coercion :as reic]
            [reitit.coercion.spec :as reiss]
            [reitit.frontend :as reif]
            [reitit.frontend.controllers :as reifc]
            [reitit.frontend.easy :as reife]))

(defn ui
  []
  [:div
   [:h1 "Auto-complete"]])


(defn render
  []
  (r/render [ui]
            (js/document.querySelector "div#app")))

(rf/reg-event-db
 :initialize
 (fn [_ _]
   {}))

(defn ^:dev/after-load reload []
  (rf/clear-subscription-cache!)
  (render))

(defn init []
  (rf/dispatch-sync [:initialize])
  (render))
