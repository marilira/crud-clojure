(ns crud.handlers
  (:require [clojure.data.json :as json]
            [crud.db :as db]))

(defn new-product [req]
  (let [content (slurp (:body req)) 
        data (json/read-str content :key-fn keyword)] 
    (db/add-item data) 
    {:status 201
     :headers {"Content-Type" "application/json"}
     :body (json/write-str data)})
  )
  
(defn all-products []
  (json/write-str (db/list-items)))

(defn update-product [id req]
  (let [content (slurp (:body req))
        data (json/read-str content :key-fn keyword)]
    (if (contains? (db/list-items) id)
      (do
        (db/update-item id data)
        {:status 200
         :headers {"Content-Type" "application/json"}
         :body (json/write-str (get (db/list-items) id))})
    {:status 404
       :headers {"Content-Type" "application/json"}
       :body (json/write-str {:message "Produto não existe"})})))

(defn delete-product [id]
  (if (contains? (db/list-items) id)
    (do
      (db/delete-item id)
      {:status 200
       :headers {"Content-Type" "application/json"}
       :body (json/write-str {:message (str "Produto " id " foi removido")})})
    {:status 404
       :headers {"Content-Type" "application/json"}
       :body (json/write-str {:message "Produto não existe"})}))

(defn get-product [id]
  (let [data (db/get-item id)]
   (if (empty? data)
     {:status 404
      :headers {"Content-Type" "application/json"}
      :body (json/write-str {:message "Produto não existe"})}
     {:status 200
      :headers {"Content-Type" "application/json"}
      :body (json/write-str data)}) 
  )
  )