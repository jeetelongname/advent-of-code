(ns twenty-twenty-two.day1
  (:require [clojure.string  :as string]))

(defn parse-file
  "takes in the string in the puzzle form
  returns a list of list of numbers"
  [string]
  (->> (string/split string #"\n\n")
       (map #(string/split % #"\n"))
       (map (partial map parse-long))
       (map (partial apply +))))

(comment
  ;;; part 1
  ;; Stream solution
  (reduce #(max %1 %2)
          (map (partial apply +)
               (parse-file (slurp "input/day1.txt"))))

  ;; Optimised solution
  (->> (slurp "input/day1.txt")
       parse-file
       (reduce max))

  ;;; part 2
  ;; Stream solution
  (apply +
         (take 3
               (reverse (sort
                         (map (partial apply +)
                              (parse-file (slurp "input/day1.txt")))))))

  ;; optimised solution
  (->> (slurp "input/day1.txt")
       parse-file
       (sort >)
       (take 3)
       (apply +)))
