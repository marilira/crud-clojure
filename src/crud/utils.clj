(ns crud.utils)

(defn get-id [uri] 
     (when-let [id (second (re-matches #"/product/(\d+)" uri))]
      (Integer/parseInt id)))