(ns twenty-twenty-two.day4
  (:require [clojure.string :only [split]]
            [clojure.set :only [subset? intersection]]))

(def example "2-4,6-8
2-3,4-5
5-7,7-9
2-8,3-7
6-6,4-6
2-6,4-8")

(def actual (slurp "input/day4.txt"))

(defn inclusive-range
  ""
  [start end]
  (range start (inc end)))

(defn either-subset?
  ""
  [set1 set2]
  (or (subset? set1 set2) (subset? set2 set1)))

(defn parse-input
  ""
  [string]
  (->> (split string #"\n")
       (map (fn [str]
              (->> (split str #",")
                   (map #(map (partial parse-long) (split %1 #"-"))))))
       (map (partial map (comp set (partial apply inclusive-range))))))

(def solve (comp count filter))

(comment
  ;; part 1
  (solve (partial apply either-subset?) (parse-input actual))

  ;; part 2
  (solve (comp seq (partial apply intersection)) (parse-input actual)))
