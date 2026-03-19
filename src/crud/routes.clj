(ns crud.routes
  (:require [crud.utils :as utils]
            [clojure.data.json :as json]
            [crud.handlers :as h]))

(defn app [req]
  (let [uri (:uri req)
        id (utils/get-id uri)]
    (case (:request-method req)
      :get (cond
             (= (:uri req) "/")
             {:status 200
              :headers {"Content-Type" "application/json"}
              :body (json/write-str {:message "CRUD API in Clojure"})}

             (= (:uri req) "/products")
             {:status 200
              :headers {"Content-Type" "application/json"}
              :body (h/all-products)}
             
             id
             (h/get-product id)

             :else
             {:status 404 :headers {"Content-Type" "application/json"} :body (json/write-str {:message "Página não encontrada"})})

      :put (if (= (:uri req) "/product")
              (h/new-product req)
              {:status 405 :headers {"Content-Type" "application/json"} :body (json/write-str {:message "Método não permitido"})})

      :patch (if id
             (h/update-product id req)
             {:status 405 :headers {"Content-Type" "application/json"} :body (json/write-str {:message "Método não permitido"})})

      :delete (if id
                (h/delete-product id)
                {:status 405 :headers {"Content-Type" "application/json"} :body (json/write-str {:message "Método não permitido"})})
      {:status 404 :headers {"Content-Type" "application/json"} :body (json/write-str {:message "Não encontrado"})})))