(ns day1
  (:require
   [clojure.string  :as string]))

(defn parse-file
  "takes in the string in the puzzle form
  returns a list of list of numbers"
  [string]
  (->> (string/split string #"\n\n")
       (map #(string/split % #"\n"))
       (map (partial map read-string))
       (map (partial apply +))))

(comment
  ;; part 1
  (reduce #(max %1 %2)
          (map (partial apply +)
               (parse-file (slurp "input/day1.txt"))))

  (->> (slurp "input/day1.txt")
       parse-file
       (reduce #(max %1 %2)))

  ;; part 2
  (apply +
         (take 3
               (reverse (sort
                         (map (partial apply +)
                              (parse-file (slurp "input/day1.txt")))))))

  (->> (slurp "input/day1.txt")
       parse-file
       sort
       reverse
       (take 3)
       (apply +)))
