(ns robo-move.core
  (:gen-class))

(declare start)

(def valid-commands-without-args [ "MOVE" "LEFT" "RIGHT" "REPORT"])

(def valid-command-with-args ["PLACE"])

(def valid-dirctions ["NORTH" "SOUTH" "EAST" "WEST"])

;;;;
;; TO-DO create boad programatically  
;;;;
(def board [
  {:row 0 :col 0} {:row 0 :col 1} {:row 0 :col 2} {:row 0 :col 3} {:row 0 :col 4}
  {:row 1 :col 0} {:row 1 :col 1} {:row 1 :col 2} {:row 1 :col 3} {:row 1 :col 4}
  {:row 2 :col 0} {:row 2 :col 1} {:row 2 :col 2} {:row 2 :col 3} {:row 2 :col 4}
  {:row 3 :col 0} {:row 3 :col 1} {:row 3 :col 2} {:row 3 :col 3} {:row 3 :col 4}
  {:row 4 :col 0} {:row 4 :col 1} {:row 4 :col 2} {:row 4 :col 3} {:row 4 :col 4}
  ])

;;;;
;; All these functions takes input from command line 
;; Validats input is valid, mostly they are not pure
;;;;

(defn user-input
  "User input from command line"
  ([] (user-input ""))
  ([default]
     (let [input (clojure.string/trim (read-line))]
       (if (empty? input)
         default
         (clojure.string/upper-case input)))))

(defn in? 
  "python's in like functionality"
  [coll elm]  
  (some #(= elm %) coll))

(defn seperate-place-args
  "Function seperates place arguments seperated by comma"
  [place-args]
  (let [[[row col direction]]  
        (map #(clojure.string/split % #"," ) place-args)]
          [row col direction]))

(defn convert-str-to-int
  [number-string]
  (try (Integer/parseInt number-string)
    (catch Exception e nil)))

(defn row-col-not-exists-in-board
  [row col]
  (= () (filter #(= % {:row row :col col }) board))
)

(defn check-place-args
  "Convert row, col of string into int and check they are into board"
  [row col direction]
  (let [int-row (convert-str-to-int row) 
        int-col (convert-str-to-int col)]
    (def in-valid-cordinates (row-col-not-exists-in-board int-row int-col))
    (if (or (= nil int-row) (= nil int-col) (= nil (in? valid-dirctions direction)) (= true in-valid-cordinates))
      nil
      [int-row int-col direction] 
    )))

(defn valid-place-args
  [place-command place-args]
  (let [[row col direction] 
        (seperate-place-args place-args)]
    (let [[int-row int-col direction] (check-place-args row col direction)] 
      (if (= nil int-row)
        nil
        [place-command int-row int-col direction]))))

(defn valid-place-command
  [place-command place-args]
  (if (in? valid-command-with-args place-command)
      (valid-place-args place-command place-args)
      nil))

(defn valid-command
  [command]
  (let [[first & rest] (clojure.string/split command #" ")]
    (if (in? valid-commands-without-args first)
      [command] 
      (valid-place-command first rest))))

(defn prompt
  []
  (println "Place a command")
  (let [command (user-input)]
    command))

(defn invalid-msg
  [robot-state]
  (print "Invalid command, please try valid commands \n")
  (start robot-state))


(def initial-robot-state { :row -1 :col -1 :direction "" })

(defn place-robot 
  [robot-state row col direction]
  (-> robot-state
      (assoc-in [:row] row)
      (assoc-in [:col] col)
      (assoc-in [:direction] direction)
      ))

(defn report-robot-state
  [robot-state]
  (let [{row :row col :col direction :direction} robot-state]
    (if (= -1 row)
      (println "Place robot first")
      (println row,col,direction)
      ))
  robot-state)
;;;;
;; Functions that determines rules of robot, board is 2 dimensional space
;; Contract is robot can't be fall from edge
;;;;


(def board-dimensions {:rows 4 :cols 4})
(def south-side-col 0)
(def east-side-row (get board-dimensions :rows))
(def west-side-row 0)
(def north-side-col (get board-dimensions :cols))

(defn south-corner-side-rule
  [col direction]
  (if (and (= col south-side-col) (= direction "SOUTH"))
    false
    true
    ))

(defn west-corner-side-rule
  [row direction]
  (if (and (= row west-side-row) (= direction "WEST"))
    false
    true
    ))

(defn east-corner-side-rule
  [row direction]
  (if (and (= row east-side-row) (= direction "EAST"))
    false
    true
    ))

(defn north-corner-side-rule
  [col direction]
  (if (and (= col north-side-col) (= direction "NORTH"))
    false
    true
    ))

(defn can-robot-move?
  [robot-state]
  "Can avoid false = condition but I prefer explicit condition in this case"
  (let [{row :row col :col direction :direction} robot-state]
    (if (or 
         (= false (west-corner-side-rule row direction)) 
         (= false (south-corner-side-rule col direction)) 
         (= false (north-corner-side-rule col direction)) 
         (= false (east-corner-side-rule row direction)))
      false
      true)))

(defn is-robot-on-table?
  [robot-state]
  (let [{row :row col :col direction :direction} robot-state]
    (if (= -1 row)
      false
      true)))

(defn change-robot-position 
  [robot-state]
  (let [{row :row col :col direction :direction} robot-state]
    (case direction
      "NORTH" (update-in robot-state [:col] inc)
      "SOUTH" (update-in robot-state [:col] dec)
      "EAST" (update-in robot-state [:row] inc)
      "WEST" (update-in robot-state [:row] dec)
    )))

(defn move-robot
  "Check the contract if robot can move and then get a new state of robot"
  [robot-state]
  (if (and 
       (is-robot-on-table? robot-state) 
       (can-robot-move? robot-state))
    (change-robot-position robot-state)
    robot-state))

(defn change-direction-to-left
  [robot-state]
  (let [{row :row col :col direction :direction} robot-state]
    (case direction
      "NORTH" (assoc-in robot-state [:direction] "WEST")
      "SOUTH" (assoc-in robot-state [:direction] "EAST")
      "EAST" (assoc-in robot-state [:direction] "NORTH")
      "WEST" (assoc-in robot-state [:direction] "SOUTH")
    )))

(defn change-direction-to-right
  [robot-state]
  (let [{row :row col :col direction :direction} robot-state]
    (case direction
      "NORTH" (assoc-in robot-state [:direction] "EAST")
      "SOUTH" (assoc-in robot-state [:direction] "WEST")
      "EAST" (assoc-in robot-state [:direction] "SOUTH")
      "WEST" (assoc-in robot-state [:direction] "NORTH")
    )))

(defn process-commands
  [robot-state command row col direction]
  (case command
    "PLACE" (place-robot robot-state row col direction)
    "REPORT" (report-robot-state robot-state)
    "MOVE" (move-robot robot-state)
    "LEFT" (change-direction-to-left robot-state)
    "RIGHT" (change-direction-to-right robot-state)
    ))

(defn valid-command-processing
  [command robot-state [row col direction]]
  (def new-robot-state (process-commands robot-state command row col direction))
  (start new-robot-state))

(defn start
  [robot-state]
  (let [[command & args]  (valid-command (prompt))]
    (if (= nil command)
      (invalid-msg robot-state)
      (valid-command-processing command robot-state args)
      )))

(defn -main
  "start of program"
  [& args]
  (start initial-robot-state))
