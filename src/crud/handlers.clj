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
    (db/update-item id data)
    {:status 200
     :headers {"Content-Type" "text/plain"}
     :body (str (get (db/list-items) id))})
  )

(defn delete-product [id]
  (db/delete-item id)
  {:status 200
     :headers {"Content-Type" "text/plain"}
     :body (str "Produto " id " foi removido")}
  )
