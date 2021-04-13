(ns metabase.shared.models.visualization-settings-test
  (:require [clojure.set :as set]
            [clojure.test :as t]
            [metabase.mbql.normalize :as normalize]
            [metabase.shared.models.visualization-settings :as viz]))

(t/deftest parse-column-ref-strings-test
  (t/testing "Column ref strings are parsed correctly"
    (let [f-qual-nm "/databases/MY_DB/tables/MY_TBL/fields/COL1"
          f-id      42]
      (doseq [[input-str expected] [[(format "[\"ref\",[\"field\",%d,null]]" f-id) {::viz/field-id f-id}]
                                    [(format "[\"ref\",[\"field\",\"%s\",null]]" f-qual-nm)
                                     {::viz/field-qualified-name f-qual-nm}]]]
        (t/is (= expected (viz/parse-column-ref-str input-str)))))))

(t/deftest form-conversion-test
  (t/testing ":visualization_settings are correctly converted from DB to qualified form and back"
    (let [f-id                42
          target-id           19
          db-click-behavior   {:type             "link"
                               :linkType         "question"
                               :parameterMapping {}
                               :targetId         target-id}
          db-col-settings     {(format "[\"ref\",[\"field\",%d,null]]" f-id) {:click_behavior db-click-behavior}}
          db-viz-settings     {:column_settings db-col-settings}
          norm-click-behavior {::viz/click-behavior-type    ::viz/link
                               ::viz/link-type              ::viz/card
                               ::viz/link-parameter-mapping {}
                               ::viz/link-target-id         target-id}
          norm-col-settings   {(viz/column-ref-for-id f-id) {::viz/click-behavior norm-click-behavior}}
          norm-viz-settings   {::viz/column-settings norm-col-settings}]
      (doseq [[db-form norm-form] [[db-viz-settings norm-viz-settings]]]
        (let [to-norm (viz/from-db-form db-form)]
          (t/is (= norm-form to-norm))
          (let [to-db (viz/db-form to-norm)]
            (t/is (= db-form to-db))))))))

