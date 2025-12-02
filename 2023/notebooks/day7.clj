;; # Day 7
(ns day7
  (:require [nextjournal.clerk :as clerk]
            [clojure.string :as str]))

^{::clerk/visibility {:code :hide :result :hide}}
(def input (slurp "input/day7.txt"))

^{::clerk/visibility {:code :hide :result :hide}}
(def mock1 "32T3K 765
T55J5 684
KK677 28
KTJJT 220
QQQJA 483")

;; ## parsing
(defn parse [input]
  (into []
        (comp (map #(str/split % #" "))
              (map (fn [[hand bid]]
                     {:hand (vec hand) :bid (Integer. bid)})))
        (-> input
            (str/split-lines))))

^{::clerk/auto-expand-results? true}
(parse mock1)
(parse input)

(def cards
  {\A 1
   \K 2
   \Q 3
   \T 4
   \9 5
   \8 6
   \7 7
   \5 8
   \3 9
   \2 10})

(defn count-element [el seq]
  (count
   (filter (partial = el) seq)))

(defn calculate [line]
  (conj line
        {:card-val (-> line :hand first cards)}
        {:hand-type
         (let [unique-cards (-> line :hand set)
               hand (:hand line)
               amount (count unique-cards)
               first-element-amount (count-element (first hand) hand)]
           (cond (= amount 1) :5ofakind
                 (and  (= amount 2)
                       (or (= first-element-amount 1)
                           (= first-element-amount 4))) :4ofakind
                 (and (= amount 2)
                      (or (= first-element-amount 3)
                          (= first-element-amount 2))) :full-house
                 (and (= amount 3)
                      (or (= first-element-amount 3)
                          (= first-element-amount 1))) :3ofakind
                 (= amount 3) :twopair
                 (= amount 4) :onepair
                 (= amount 5) :highcard))}))

(-> mock1 parse first calculate)

^{::clerk/auto-expand-results? true}
(mapv calculate (parse mock1))
;; My sorting strat,
;; I work out what hand it is, these all get a value, 1 for the weakest, 7 for the strongest.
;; this is not enough though. I may need to do two passes, one to sort the hands, then the next to sort the cards in the hand.
;; ```clojure
;; (sort-by (fn [el]
;;            (+ )))
;; ```
