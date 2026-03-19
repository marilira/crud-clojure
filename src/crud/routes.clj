(ns crud.routes
  (:require [crud.utils :as utils]
            [crud.handlers :as h]))

(defn app [req]
  (let [uri (:uri req)
        id (utils/get-id uri)]
    (case (:request-method req)
      :get (cond
             (= (:uri req) "/")
             {:status 200
              :headers {"Content-Type" "text/plain"}
              :body "CRUD API in Clojure"}

             (= (:uri req) "/products")
             {:status 200
              :headers {"Content-Type" "text/plain"}
              :body (str "Todos os produtos:\n" (h/all-products))}
             
             id
             (h/get-product id)

             :else
             {:status 404
              :headers {"Content-Type" "text/plain"}
              :body "Página não encontrada"})

      :post (if (= (:uri req) "/product")
              (h/new-product req)
              {:status 405 :body "Método não permitido"})

      :put (if id
             (h/update-product id req)
             {:status 405 :body "Método não permitido"})

      :delete (if id
                (h/delete-product id)
                {:status 405 :body "Método não permitido"})
      {:status 404 :body "Não encontrado"})))