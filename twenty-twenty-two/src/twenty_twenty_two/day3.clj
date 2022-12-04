(ns twenty-twenty-two.day3
  (:require [clojure.string :as string])
  (:require [clojure.set :as set]))

(def example "vJrwpWtwJgWrhcsFMMfFFhFp
jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
PmmdzqPrVvPwwTWBwg
wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
ttgJtRGJQctTZtZT
CrZsJsPPZsGzwwsLwLmpwMDw
")

(def actual (slurp "input/day3.txt"))

(defn parse-input
  ""
  [string]
  (string/split string #"\n"))

(defn convert
  ""
  [char]
  (let [ascii (int char)]
    (- ascii (if (> ascii 96) 96 38))))

(defn solve
  ""
  [coll]
  (reduce #(+ %1
              (-> (apply set/intersection %2)
                  first
                  convert))
          0
          coll))
(comment
  ;; part 1
  (solve (map #(map set
                    (partition (/ (count %1) 2) %1))
              (parse-input example)))

  ;; part 2
  (solve  (partition 3 (map set (parse-input actual)))))
