(ns crud.server
  (:require [org.httpkit.server :as server]             
            [crud.routes :as routes]))

(defonce server (atom nil))

(defn stop-server []
  (when @server
    (@server :timeout 100)
    (reset! server nil))
  (println "Server terminated"))
    
(defn -main [& _]
  (let [port 8080]
    (reset! server (server/run-server #'routes/app {:port port}))
    (println "Server started on port" port)))