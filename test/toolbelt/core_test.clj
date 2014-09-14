(ns toolbelt.core-test
  (:require [clojure.test :refer :all]
            [toolbelt.core :refer :all]))

(deftest flip-test
  (testing "flip makes remove work with its first params being the sequence"
    (let [s [1 2 3]
          flipped-remove (flip remove)]
      (is (= '(1 3)
             (flipped-remove s #(= 2 %)))))))
