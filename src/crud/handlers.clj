(ns crud.handlers
  (:require [clojure.data.json :as json]
            [crud.utils :as utils]
            [crud.db :as db]))

(defn new-product [req]
  (let [content (slurp (:body req)) 
        data (json/read-str content :key-fn keyword)
        new-id (utils/next-id)] 
    (db/upsert-item (assoc data :id new-id))
    {:status 201
     :headers {"Content-Type" "application/json"}
     :body (json/write-str data)})
  )
  
(defn all-products []
  (json/write-str (db/list-items)))

(defn update-product [id req]
  (let [content (slurp (:body req))
        data (json/read-str content :key-fn keyword)]
    (if (some #{id} (map first (db/list-items)))
      (do
        (db/upsert-item (assoc data :id id))
        {:status 200
         :headers {"Content-Type" "application/json"}
         :body (json/write-str (db/get-item id))})
    {:status 404
       :headers {"Content-Type" "application/json"}
       :body (json/write-str {:message "Product not found"})})))

(defn delete-product [id]
  (if (contains? (db/list-items) id)
    (do
      (db/retract-item id)
      {:status 200
       :headers {"Content-Type" "application/json"}
       :body (json/write-str {:message (str "Product " id " deleted")})})
    {:status 404
       :headers {"Content-Type" "application/json"}
       :body (json/write-str {:message "Product not found"})}))

(defn get-product [id]
  (let [data (db/get-item id)]
   (if (empty? data)
     {:status 404
      :headers {"Content-Type" "application/json"}
      :body (json/write-str {:message "Product not found"})}
     {:status 200
      :headers {"Content-Type" "application/json"}
      :body (json/write-str data)}) 
  )
  )