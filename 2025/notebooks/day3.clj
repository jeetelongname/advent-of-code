(ns day3
  (:require
   [clojure.string :as string]
   [clojure.core.reducers :as r]
   [clojure.core.matrix :as m]
   [nextjournal.clerk :as clerk]
   [helpers :as h]))

(def test-input "987654321111111
811111111111119
234234234234278
818181911112111")

;; Parsing
(defn search-space [input]
  (->> input
       (reduce-kv #(conj %1 [%3 (into [] (drop (inc %2) input))]) [])
       butlast
       (into [])))

(defn parse [input]
  (-> input
      vec))

(def parsed-test-input
  (->> test-input
       string/split-lines
       (mapv (comp search-space parse))))

;; # Part 1
(defn find-joltage-line-case [[first rest]]
  (r/reduce (fn [acc val]
              (max
               acc
               (-> (string/join [first val])
                   h/to-int)))
            0
            rest))

(defn solve [input]
  (->> input
       (r/map #(->> %
                    (r/map find-joltage-line-case)
                    (into [])
                    (apply max)))
       (into [])
       (apply +)))

(solve parsed-test-input)

(def parsed-input
  (->> (slurp "input/day3.txt")
       string/split-lines
       (r/map (comp search-space parse))
       (into [])))

(solve parsed-input)

;; # Part 2
(def parsed-test-input2
  (->> test-input
       string/split-lines
       (mapv parse)))

;; This year has been kicking my ass, Thanks again to Elken for the code
;; https://elken.github.io/aoc/src/solutions/2025/day03/ Though I did not read
;; your small essay to understand the code, I atleast did that myself. What is
;; happening here is that we first define a window, let us say we are at the
;; first case of a test input our `(count input)` => 14 and our rem is 12, this
;; gives us a search space of 2 of the inputs. we find the largest in this
;; search space and note its index. we then shrink our battery bank down to all
;; of the characters after the one we just found. We repeat this until their are
;; no more batteries to pull out. In my case I reconvert it back to a character
;; and string/join all those characters to be parsed back into a long, because I
;; can't be bothered with the maths to actually work out the result.
(defn find-joltage-line [n input]
  (loop [input (mapv h/char->int input)
         result []
         rem n]
    (if (zero? rem)
      (h/to-long (string/join result))
      (let [window (inc (- (count input) rem))
            largest (apply max (take window input))
            index (.indexOf input largest)
            new-search-space (subvec input (inc index))]
        (recur new-search-space
               (conj result (Character/forDigit largest 10))
               (dec rem))))))

(defn solve2 [inputs]
  (->> inputs
       (r/map (partial find-joltage-line 12))
       (into [])
       (apply +)))

(solve2 parsed-test-input2)
(->> (slurp "input/day3.txt")
     string/split-lines
     (r/map parse)
     (into [])
     solve2)
