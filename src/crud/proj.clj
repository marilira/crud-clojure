(ns crud.proj
  (:require [org.httpkit.server :as server]
            [clojure.data.json :as json]))

(defonce server (atom nil))

(defn stop-server []
  (when @server
    (@server :timeout 100)
    (reset! server nil))
  (println "Server terminated"))

(defonce db (atom {}))

(defn new-product [req]
  (let [content (slurp (:body req)) ; converte pra str
        data (json/read-str content :key-fn keyword) ; converte pra map com :keywords
        new-id (inc (count @db))]
    (swap! db assoc new-id data) 
    {:status 201
     :headers {"Content-Type" "text/plain"}
     :body (str data)})
  )
  
(defn all-products []
  (apply str
         (for [[id data] @db]
         (str "Produto " id ": " data "\n")))
  )

(defn update-product [id req]
  (let [content (slurp (:body req))
        data (json/read-str content :key-fn keyword)]
    (swap! db update id merge data)
    {:status 200
     :headers {"Content-Type" "text/plain"}
     :body (str (get @db id))})
  )

(defn get-id [uri] 
     (when-let [id (second (re-matches #"/product/(\d+)" uri))]
      (Integer/parseInt id)))
    
(defn app [req]
  (case (:request-method req)
    :get {:status 200
          :headers {"Content-Type" "text/plain"}
          :body (str "Todos os produtos:\n" (all-products))}
    
    :post (if (= (:uri req) "/product")
            (new-product req)
            {:status 405 :body "Método não permitido"})
    
    :put (let [uri (:uri req)]
           (if-let [id (get-id uri)] 
               (update-product id req)
             {:status 405 :body "Método não permitido"}))
  {:status 404 :body "Não encontrado"}))

(defn -main [& args]
  (let [port 8080]
    (reset! server (server/run-server #'app {:port port}))
    (println "Server started on port" port)))