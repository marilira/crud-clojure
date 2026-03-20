(ns crud.db
  (:require [datomic.client.api :as d]))

(def client (d/client {:server-type :datomic-local
                       :storage-dir :mem
                       :system "dev"}))

(d/create-database client {:db-name "products"})
(def conn (d/connect client {:db-name "products"}))

(def products-schema [{:db/ident :product/id
                       :db/unique :db.unique/identity
                       :db/valueType :db.type/long
                       :db/cardinality :db.cardinality/one
                       :db/doc "id of the product"}
                      {:db/ident :product/name
                       :db/valueType :db.type/string
                       :db/cardinality :db.cardinality/one
                       :db/doc "name of the product"}
                      {:db/ident :product/price
                       :db/valueType :db.type/double
                       :db/cardinality :db.cardinality/one
                       :db/doc "price of the product"}
                      ])
(d/transact conn {:tx-data products-schema})

(defn list-items []
  (d/q '[:find ?id ?name ?price
         :where
         [?e :product/id ?id]
         [?e :product/name ?name]
         [?e :product/price ?price]]
       (d/db conn)))

(defn get-item 
  "Retrieves the current name and price of a product"
  [id]
  (d/q '[:find ?name ?price
         :in $ ?id
         :where
         [?e :product/id ?id]
         [?e :product/name ?name]
         [?e :product/price ?price]]
       (d/db conn) id))

(defn upsert-item 
  "Update or insert one product"
  [{:keys [id name price]}]
  (d/transact conn
              {:tx-data [{:product/id id
                          :product/name name
                          :product/price price}]}))

(defn retract-item
  "Retract all the fields based on the :product/id"
  [id]
  (d/transact conn {:tx-data
                      [[:db/retract [:product/id id] :product/id]
                       [:db/retract [:product/id id] :product/name]
                       [:db/retract [:product/id id] :product/price]]}))