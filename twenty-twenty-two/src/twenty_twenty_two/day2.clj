(ns twenty-twenty-two.day2
  (:require [clojure.string :as string])
  (:require [clojure.core.match :only [match]]))

(def example "A Y
B X
C Z")
(def all-perms "A X
A Y
A Z
B X
B Y
B Z
C X
C Y
C Z")

;; A X rock + draw
;; A Y paper + win
;; A Z scissors + lose
;;
;; B X rock + lose
;; B Y paper + draw
;; B Z scissors + win
;;
;; C X rock + win
;; C Y paper + lose
;; C Z scissors + draw

(def actual (slurp "input/day2.txt"))

(def rock 1)
(def paper 2)
(def scissors 3)

(def win 6)
(def draw 3)
(def lose 0)

(defn parse-input
  ""
  [string]
  (vector (map  #(string/split %1 #" ") (string/split string #"\n"))))

;; (defn play
;;   ""
;;   [pair]
;;   (let [shape (last pair)
;;         outcome (cond
;;                   (apply = pair) draw
;;                   (and (apply > pair) (not (== (first pair) scissors))) lose
;;                   (and (apply > pair) (== (last pair) paper)) lose
;;                   :else win)]
;;     (+ shape outcome)))

(defn play-part1
  ""
  [pair]
  (match pair
    ["A" "X"] (+ rock draw)
    ["A" "Y"] (+ paper win)
    ["A" "Z"] (+ scissors lose)

    ["B" "X"] (+ rock lose)
    ["B" "Y"] (+ paper draw)
    ["B" "Z"] (+ scissors win)

    ["C" "X"] (+ rock win)
    ["C" "Y"] (+ paper lose)
    ["C" "Z"] (+ scissors draw)))

(defn play-part2
  ""
  [pair]
  (match pair
               ;; rock
    ["A" "X"] (+ scissors lose)
    ["A" "Y"] (+ rock  draw)
    ["A" "Z"] (+ paper win)

               ;; paper
    ["B" "X"] (+ rock lose)
    ["B" "Y"] (+ paper draw)
    ["B" "Z"] (+ scissors win)

               ;; scissors
    ["C" "X"] (+ paper lose)
    ["C" "Y"] (+ scissors draw)
    ["C" "Z"] (+ rock win)))

(comment
  ;; part 1
  (reduce #(+ %1 (play-part1 %2)) 0 (parse-input actual))
  (reduce #(+ %1 (play-part2 %2)) 0 (parse-input actual)))
