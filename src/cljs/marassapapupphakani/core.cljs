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

(rf/reg-event-db
 ::initialize-db
 (fn [_ _]
   {:current-route nil}))

(rf/reg-event-fx
 ::navigate
 (fn [db [_ & route]]
   {::navigate! route}))

(rf/reg-event-db
 ::navigated
 (fn [db [_ new-match]]
   (let [old-match   (:current-route db)
         controllers (reifc/apply-controllers (:controllers old-match) new-match)]
     (assoc db :current-route
            (assoc new-match
                   :controllers
                   controllers)))))

(rf/reg-sub
 ::current-route
 (fn [db]
   (:current-route db)))

(defn home-page []
  [:div
   [:h1 "MAIN"]])


(rf/reg-fx
 ::navigate!
 (fn [route]
   (apply reife/push-state route)))

(def routes
  ["/"
   [""
    {:name      ::home
     :view      home-page
     :link-text "Home"
     :controllers
     []}]])

(defn on-navigate [new-match]
  (when new-match
    (rf/dispatch [::navigated new-match])))

(def router
  (reif/router
   routes
   {:data {:coercion reiss/coercion}}))

(defn init-routes! []
  (reife/start!
   router
   on-navigate
   {:use-fragment false}))

(defn render [{:keys [router]}]
  (let [current-route @(rf/subscribe [::current-route])]
    [:div (when current-route
            [(-> current-route
                 :data
                 :view)])]))

(defn mount-root []
  (rf/clear-subscription-cache!)
  (init-routes!)
  (r/render [render {:router router}]
            (.getElementById js/document "app")))

(defn ^:dev/after-load reload []
  (mount-root))

(defn ^:export init []
  (rf/dispatch-sync [::initialize-db])
  (mount-root))
