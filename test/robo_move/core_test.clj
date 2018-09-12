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

;; Tests to change robot state

(deftest move-robot-east-direction
  (testing "moving in east direction"
    (def east-robot-state {:row 1 :col 0 :direction "EAST"})
    (is (= 
         east-robot-state
         (change-robot-position {:row 0 :col 0 :direction "EAST"} )))))

(deftest move-robot-west-direction
  (testing "moving in west direction"
    (def west-robot-state {:row 3 :col 4 :direction "WEST"})
    (is (= 
         west-robot-state
         (change-robot-position {:row 4 :col 4 :direction "WEST"} )))))

(deftest move-robot-north-direction
  (testing "moving in north direction"
    (def north-robot-state {:row 0 :col 1 :direction "NORTH"})
    (is (= 
         north-robot-state
         (change-robot-position {:row 0 :col 0 :direction "NORTH"} )))))

(deftest move-robot-south-direction
  (testing "moving in south direction"
    (def south-robot-state {:row 4 :col 3 :direction "SOUTH"})
    (is (= 
         south-robot-state
         (change-robot-position {:row 4 :col 4 :direction "SOUTH"} )))))
