(ns twenty-twenty-two.core
  (:require [clojure.string :as str]))

(defn parse-file
  ""
  [string]
  (map (fn [lst] (map #(read-string %) lst))
       (map  #(str/split % #"\n")
             (str/split string #"\n\n"))))
(comment
  ;; part 1
  (reduce #(max %1 %2)
          (map (partial apply +)
               (parse-file (slurp "input/day1.txt"))))

  (->> (slurp "input/day1.txt")
       parse-file
       (map (partial apply +))
       (reduce #(max %1 %2)))

  ;; part 2
  (apply +
         (take 3
               (reverse (sort
                         (map (partial apply +)
                              (parse-file (slurp "input/day1.txt")))))))

  (->> (slurp "input/day1.txt")
       parse-file
       (map (partial apply +))
       sort
       reverse
       (take 3)
       (apply +)))
