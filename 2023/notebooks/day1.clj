;; # day 1
(ns day1
  (:require [clojure.string :as string])
  (:import [java.net URL]
           [javax.imageio ImageIO]))

;; ## inputs
(def input (string/split (slurp "input/day1.txt") #"\n"))

(def mock-part-2 (-> "two1nine
eightwothree
abcone2threexyz
xtwone3four
4nineeightseven2
zoneight234
7pqrstsixteen
eighthree
sevenine"
                     (string/split #"\n")))

;; ## Solution part 1
;; Here what I do is tranduce the output too
(defn general-solution
  [input]
  (transduce (comp (map seq)
                   (map (partial filter #(Character/isDigit %))))
             (fn
               ([result] result)
               ([acc seqc] (let [f (first seqc)
                                 l (last seqc)]
                             (+ acc (Integer. (str f l))))))
             0
             input))

(general-solution input)
;; ## Solution part 2
;;  Including some edge cases in the thing mock input, Here we are caluclating our new answer for the mock input
(+ 281 79 83)

;;  here we map all of the digit strings to the corresponding number string
(def convert
  {"one" "1"
   "two" "2"
   "three" "3"
   "four" "4"
   "five" "5"
   "six" "6"
   "seven" "7"
   "eight" "8"
   "nine" "9"
   "oneight" "18"
   "twone" "21"
   "threight" "38"
   "fiveight" "58"
   "sevenine" "79"
   "eightwo" "82"
   "eighthree" "83"
   "nineight" "98"})

;; Here we have all the examples of our overlapping digit strings
;; oneight twoone threeight fiveight sevenine eightwo eighthree nineight
;; we check them first in the regex because they are more specific
(defn find-replace-name-digit [string]
  (let [regex #"(oneight|twone|threight|fiveight|sevenine|eightwo|eighthree|nineight|one|two|three|four|five|six|seven|eight|nine)" ]
    (string/replace string regex #(convert (first %)))))

;; we can see its handling the special cases.
(map find-replace-name-digit mock-part-2)

;; and getting thr right answer
(->> mock-part-2
     (map find-replace-name-digit)
     general-solution)

;; so we can finally apply it to the input and give it to the general solution
(->> input
     (map find-replace-name-digit)
     general-solution)

;; to which we get the right answer and can finish off day 1

(ImageIO/read (URL."https://media.tenor.com/jmAAqbCkAsQAAAAi/yippee-tbh-creature.gif"))
