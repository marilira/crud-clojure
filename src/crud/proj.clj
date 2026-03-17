(ns crud.proj
  (:require [org.httpkit.server :as server]))

(defn app [req]
  {:status 200
   :headers {"Content-Type" "text/plain"}
   :body "Hello World"})

(defn -main [& args]
  (let [port 8080]
    (server/run-server app {:port port})
    (println "Server started on port" port)))