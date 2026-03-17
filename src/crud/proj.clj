(ns crud.proj
  (:require [org.httpkit.server :as server]))

(defonce server (atom nil))

(defn stop-server []
  (when @server
    (@server :timeout 100)
    (reset! server nil))
  (println "Server terminated"))

(defn app [req]
  {:status 200
   :headers {"Content-Type" "text/plain"}
   :body "Hello World"})

(defn -main [& args]
  (let [port 8080]
    (reset! server (server/run-server #'app {:port port}))
    (println "Server started on port" port)))