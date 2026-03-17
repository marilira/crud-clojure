(ns crud.proj
  (:require [org.httpkit.server :as server]
            [ring.util.codec :as codec]))

(defonce server (atom nil))

(defn stop-server []
  (when @server
    (@server :timeout 100)
    (reset! server nil))
  (println "Server terminated"))

(defonce db (atom {}))

(defn product-handler [req]
  (let [qs (:query-string req)
        params (codec/form-decode qs)
        new-id (inc (count @db))]
    (swap! db assoc new-id params)
    {:status 201
     :headers {"Content-Type" "application/json"}
     :body (str "Novo produto " new-id " - " params)})
  )

(defn app [req]
  (case (:request-method req)
    :get {:status 200
          :headers {"Content-Type" "text/plain"}
          :body "Hello World"}
    :post (if (= (:uri req) "/product")
            (product-handler req)
            {:status 405 :body "Método não permitido"})
    {:status 404 :body "Não encontrado"}
    ))

(defn -main [& args]
  (let [port 8080]
    (reset! server (server/run-server #'app {:port port}))
    (println "Server started on port" port)))