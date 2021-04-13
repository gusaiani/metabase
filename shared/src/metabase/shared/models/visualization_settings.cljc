(ns metabase.shared.models.visualization-settings
  (:require [cheshire.core :as json]
            [clojure.set :as set]
            [clojure.walk :as walk]
            [clojure.spec.alpha :as s]
            [medley.core :as m]))

(defn visualization-settings []
  {})

(defn column-ref-for-id [field-id]
  {::field-id field-id})

(defn column-ref-for-qualified-name [field-qualified-name]
  {::field-qualified-name field-qualified-name})

;; opposite of above

(defn parse-column-ref-str [ref-str]
  (let [[r [t id _]] ((comp vec json/parse-string) ref-str)]
    (case t "field" (cond (int? id)    {::field-id id}
                          (string? id) {::field-qualified-name id}))))

(defn- with-col-settings [settings]
  (if (contains? settings ::column-settings)
    settings
    (assoc settings ::column-settings {})))

(defn- click-action* [entity-type entity-id]
  {::click-behavior-type    ::link
   ::link-type              entity-type
   ::link-parameter-mapping {}
   ::link-target-id         entity-id})

(defn click-action [settings from-field-id to-entity-type to-entity-id]
  (-> settings
      with-col-settings
      (update-in [::column-settings] #(assoc % (column-ref-for-id from-field-id)
                                               {::click-behavior (click-action* to-entity-type to-entity-id)}))))

(def ^:private db-to-normalized-click-action-type
  {"link" ::link})

(def ^:private normalized-to-db-click-action-type
  (set/map-invert db-to-normalized-click-action-type))

(def ^:private db-to-normalized-link-type
  {"question" ::card})

(def ^:private normalized-to-db-link-type
  (set/map-invert db-to-normalized-link-type))

(defn- db-form-entry-to-normalized
  "Converts a :column_settings DB form to qualified form. Does the opposite of `db-normalized-entry-to-db-form-entry-to-normalized`."
  [m k v]
  (case k
    :click_behavior (assoc m ::click-behavior (-> v
                                                  (assoc ::click-behavior-type (db-to-normalized-click-action-type (:type v)))
                                                  (dissoc :type)
                                                  (assoc ::link-type (db-to-normalized-link-type (:linkType v)))
                                                  (dissoc :linkType)
                                                  (set/rename-keys {:parameterMapping ::link-parameter-mapping
                                                                    :targetId         ::link-target-id})))
    (assoc m k v)))

(defn from-db-form
  "Converts the DB form of visualization settings into the qualified form"
  [visualization_settings]
  (if-let [col-settings (:column_settings visualization_settings)]
    {::column-settings (->> col-settings
                            (m/map-kv (fn [k v]
                                        (let [k1 (parse-column-ref-str k)
                                              v1 (reduce-kv db-form-entry-to-normalized {} v)]
                                          [k1 v1]))))}
    {}))

(defn- normalized-entry-to-db-form
  "Converts a ::column-settings entry from qualified form to DB form. Does the opposite of
  `db-form-entry-to-normalized`."
  [m k v]
  (case k
    ::click-behavior (assoc m :click_behavior (-> v
                                                  (assoc :type (normalized-to-db-click-action-type (::click-behavior-type v)))
                                                  (dissoc ::click-behavior-type)
                                                  (assoc :linkType (normalized-to-db-link-type (::link-type v)))
                                                  (dissoc ::link-type)
                                                  (set/rename-keys {::link-parameter-mapping :parameterMapping
                                                                    ::link-target-id         :targetId})))))

(defn- db-form-column-ref [{:keys [::field-id ::field-qualified-name]}]
  (-> (cond
        (some? field-id)             ["ref" ["field" field-id nil]]
        (some? field-qualified-name) ["ref" ["field" field-qualified-name nil]])
      json/encode))

(defn- db-form-column-settings [col-settings]
  (->> col-settings
       (m/map-kv (fn [k v]
                   [(db-form-column-ref k) (reduce-kv normalized-entry-to-db-form {} v)]))))

(defn db-form [settings]
  (if-let [col-settings (::column-settings settings)]
    (if (empty? col-settings)
      {}
      {:column_settings (db-form-column-settings col-settings)})
    {}))

