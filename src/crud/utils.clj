(ns crud.utils)

(def new-id (atom 0))
(defn next-id []
  (swap! new-id inc))

(defn get-id [uri] 
     (when-let [id (second (re-matches #"/product/(\d+)" uri))]
      (Integer/parseInt id)))