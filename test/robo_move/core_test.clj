(ns robo-move.core-test
  (:require [clojure.test :refer :all]
            [robo-move.core :refer :all]))


;; Tests to check if robot is falling from corner rules

(deftest south-corner-side-rule-test
  (testing "if y cordinate is 0 and direction is south it will not move"
    (is (= false (south-corner-side-rule  0 "SOUTH")))
    ))

(deftest west-corner-side-rule-test
  (testing "if x cordinate i.e row is 0 and direction is south it will not move"
    (is (= false (west-corner-side-rule  0 "WEST")))
    ))

(deftest east-corner-side-rule-test
  (testing "East corner"
    (is (= false (east-corner-side-rule  4 "EAST")))
    ))

(deftest north-corner-side-rule-test
  (testing "North corner"
    (is (= false (north-corner-side-rule 4 "NORTH")))
    ))
