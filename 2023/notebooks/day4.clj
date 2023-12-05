;; # Day 4
;; This problem looks much better compared to the problem yesterday.
(ns day4
  (:require [clojure.string :as str]
            [nextjournal.clerk :as clerk]
            [clojure.set :as set])
  (:import [java.net URL]
           [javax.imageio ImageIO]))

;; ## input
(def mock-1  "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19
Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1
Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83
Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36
Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11")

(def input (slurp "input/day4.txt"))

;; ## parsing
;; now this mess of a parser does look knarly, thats because it is.
;; - we start down at **1**, spliting the input into lines.
;; - we then move to **2** where we do the line based operations
;; - at this point we have a vector of vectors each containing 2 strings.
;;   we want a vector of vectors, each containing 2 sets of numbers
;; - we need to move a little deep hence the mapping of a map function.
;; - we move to **3** where we trim the string, split it on space,
;; - we now have a list of strings, we filter out the empty, convert to numbers and push each into a set.
;;
;; ez squeezy
(defn parse [input]
  (into []
        (comp
         ;; 2
         (map  #(str/split % #":"))
         (map last)
         (map  #(str/split % #"\|"))
         (map (partial mapv (comp
                             (partial into #{})
                             (partial map #(Integer. %))
                             (partial filter (comp not empty?))
                             #(str/split % #" ")
                             str/trim)))) ;; 3
        ;; 1
        (str/split-lines input)))

;; it works
(parse mock-1)

;; ## part 1
;; solving then becomes easy squeezy.
;; - for each pair of sets we find the intersection
;; - filter out all the empty sets
;; - count up all of the numbers
;; - raise it to the power of $2^{n-1}$ where n is the count
;;
;; ez squeezy
(defn solve-p1
  [input]
  (transduce (comp (map (partial apply set/intersection))
                   (filter (comp not empty?))
                   (map (comp #(Math/pow 2 (- % 1)) count)))
             + 0 input))

(-> mock-1
    parse
    solve-p1)

(-> input
    parse
    solve-p1)

;; ## part 2
;; part 2 threw me for a loop, there was little to share so I
;; basically started from scratch. The only common parts of the solution was
;; finidng the intersection and counting, there is no point it making that its
;; own function
;;
;; ### one "turn"
;;
;; I am doing one round to get a feel before I have
;; too loop over the entire thing
(def card-values (map (comp count
                            (partial apply set/intersection))
                      (parse mock-1)))

(def card-amounts-start (repeat ((comp count parse) mock-1) 1))

;; this will get added to the total
(first card-amounts-start)

;; this would complete one turn. we would then move onto the next. in theory
(let [[toinc rest] (split-at (first card-values) (rest card-amounts-start))]
  (concat (map (partial + (* (first card-amounts-start) 1)) toinc) rest))

;; ### final solution
;; if we were to turn this into a loop, we would get something like this
(defn part-2 [input]
  (let [parsed (parse input)]
    (loop [[first-value & rest-value] (map (comp count (partial apply set/intersection))
                                           parsed)
           [amount & rest-amounts] (repeat (count parsed) 1)
           total 0]
      (if (not first-value)
        total
        (recur rest-value
               (let [[toinc rest] (split-at first-value rest-amounts)]
                 (concat (map (partial + (* amount 1)) toinc) rest))
               (+ total amount))))))

;; this works.
(part-2 mock-1)

(part-2 input)

;; and we get our final answer

(ImageIO/read (URL. "http://m.quickmeme.com/img/09/09932b1e776a6fbb01341c051a8adf602f664a915f17feb1c6796e1b0e020026.jpg"))
