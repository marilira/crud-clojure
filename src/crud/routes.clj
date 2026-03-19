(ns crud.routes
  (:require [crud.utils :as utils]
            [crud.handlers :as h]))

(defn app [req]
  (case (:request-method req)
    :get {:status 200
          :headers {"Content-Type" "text/plain"}
          :body (str "Todos os produtos:\n" (h/all-products))}
    
    :post (if (= (:uri req) "/product")
            (h/new-product req)
            {:status 405 :body "Método não permitido"})
    
    :put (let [uri (:uri req)]
             (if-let [id (utils/get-id uri)] 
               (h/update-product id req)
               {:status 405 :body "Método não permitido"}))
    
    :delete (let [uri (:uri req)]
                (if-let [id (utils/get-id uri)] 
                  (h/delete-product id)
                  {:status 405 :body "Método não permitido"}))
    {:status 404 :body "Não encontrado"})
    )