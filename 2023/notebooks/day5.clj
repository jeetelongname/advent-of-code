;; # What the fuck dude
(ns day5
  (:require [nextjournal.clerk :as clerk]
            [clojure.string :as str]))

(def input (slurp "input/day5.txt"))

(def mock1  "seeds: 79 14 55 13

seed-to-soil map:
50 98 2
52 50 48

soil-to-fertilizer map:
0 15 37
37 52 2
39 0 15

fertilizer-to-water map:
49 53 8
0 11 42
42 0 7
57 7 4

water-to-light map:
88 18 7
18 25 70

light-to-temperature map:
45 77 23
81 45 19
68 64 13

temperature-to-humidity map:
0 69 1
1 0 69

humidity-to-location map:
60 56 37
56 93 4")

;; I parse each section into a vector of vectors. there is more t
(defn parse
  [input]
  (into []
        (comp
         (map str/split-lines)
         (map (partial mapv #(str/split % #" ")))
         (map #(subvec % 1))
         ;; is there a better way to do this?
         (map (partial mapv
                       (partial mapv
                                #(Integer. %)))))
        (-> input
            (str/replace "seeds: " "seeds:\n")
            (str/split  #"\n\n"))))

^{::clerk/visibility {:code :show}
  ::clerk/auto-expand-results? true}
(parse mock1)
