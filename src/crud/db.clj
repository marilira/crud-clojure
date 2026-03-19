(ns crud.db)

(defonce db (atom {}))

(defn count-db []
  (count @db))

(defn get-item [id]
  (get @db id))

(defn add-item [data]
  (let [new-id (inc (count-db))]
    (swap! db assoc new-id data)))

(defn update-item [id data]
  (swap! db update id merge data))

(defn delete-item [id]
  (swap! db dissoc id))
  
(defn list-items [] 
  @db)