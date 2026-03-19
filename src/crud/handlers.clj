(ns crud.handlers
  (:require [clojure.data.json :as json]
            [crud.db :as db]))

(defn new-product [req]
  (let [content (slurp (:body req)) ; converte pra str
        data (json/read-str content :key-fn keyword)] ; converte pra map com :keywords
    (db/add-item data) 
    {:status 201
     :headers {"Content-Type" "text/plain"}
     :body (str data)})
  )
  
(defn all-products []
  (apply str
         (for [[id data] (db/list-items)]
              (str "Produto " id ": " data "\n")))
  )

(defn update-product [id req]
  (let [content (slurp (:body req))
        data (json/read-str content :key-fn keyword)]
    (if (contains? (db/list-items) id)
      (do
        (db/update-item id data)
        {:status 200
         :headers {"Content-Type" "text/plain"}
         :body (str (get (db/list-items) id))})
    {:status 404
       :headers {"Content-Type" "text/plain"}
       :body "Produto não existe"})))

(defn delete-product [id]
  (if (contains? (db/list-items) id)
    (do
      (db/delete-item id)
      {:status 200
       :headers {"Content-Type" "text/plain"}
       :body (str "Produto " id " foi removido")})
    {:status 404
       :headers {"Content-Type" "text/plain"}
       :body "Produto não existe"}))

(defn get-product [id]
  (let [data (db/get-item id)]
   (if (empty? data)
     {:status 404
      :headers {"Content-Type" "text/plain"}
      :body "Produto não existe"}
     {:status 200
      :headers {"Content-Type" "text/plain"}
      :body (str "Visualizando Produto " id ":\n" data)}) 
  )
  )