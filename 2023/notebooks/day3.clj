;; # Day 3
;; What the fuck dude
(ns day3
  (:require [clojure.string :as str]
            ;; [nextjournal.clerk.viewer :as v]
            [helpers :refer :all]))

;; some kind of custom viewer would be nice here...
;;
;; Okay more desheveled me here, I have got it. the way I managed to fix one
;; issue when tokenising is to add another empty element to the end of each
;; line. its scuffed but it works
(def input (-> (slurp "input/day3.txt")
               (str/split-lines)
               (->> (mapv (comp #(conj % \.) vec)))))

;; I don't do this for the mock input becuase there are no elements at the end
(def mock-input-1 (-> "467..114..
...*......
..35..633.
......#...
617*......
.....+.58.
..592.....
......755.
...$.*....
.664.598.."
                      (str/split-lines)
                      (->> (mapv vec))))

;; parse-row takes in a row, finds all the numbers on it and then notes down the
;; start and end. This is why I needed to add an extra character at the end of
;; each line. I reach the end of a number when I reach a symbol thats not a
;; digit. but when I reach the end of the line I skip the block, never reaching
;; the first cond clause and finishing off the last number
(defn parse-row
  [char-row row]
  (loop [[curr & rest] char-row point 0 numstr "" num? false found []]
    (if curr
      (cond
        (and (not (Character/isDigit curr))
             num?) (recur rest (inc point) "" false
                          (conj found {:num (Integer. numstr)
                                       :row row
                                       :start (- point (count numstr))
                                       :end (dec point)}))
        (Character/isDigit curr) (recur rest
                                        (inc point)
                                        (str numstr curr)
                                        true found)
        :else (recur rest (inc point) numstr num? found))
      found)))

(parse-row (first mock-input-1) 0)

;; Originally I wanted to go over each coord and check if there was a digit
;; there that was a flop so I take a parsed row and see if its a so called
;; window. I treat the three rows a number could be found on as separate
(defn find-number [[row col] input finisher]
  (let [valid-row-before (filter (fn [{start :start end :end}]
                                   (or
                                    (<= (dec col) start (inc col))
                                    (<= (dec col) end (inc col))))
                                 (parse-row (get input (dec row)) (dec row)))
        valid-row-after (filter (fn [{start :start end :end}]
                                  (or
                                   (<= (dec col) start (inc col))
                                   (<= (dec col) end (inc col)))) (parse-row (get input (inc row)) (inc row)))
        valid-cur-row  (filter (fn [{start :start end :end}]
                                 (or (= end (dec col))
                                     (= start (inc col))))
                               (parse-row (get input row) row))]
    (finisher (concat valid-row-before valid-cur-row valid-row-after))))

;; the general solution goes through each symbol, if special? flags it we then
;; go and find the numbers, add it to our accumulated value once we reach the
;; end we return the accumulator. the general solution takes a finisher function
;; as well which we pass to find number because this is a hacked together hodge
;; podge of a solution
(defn general-solution
  [input special? finisher]
  (loop [col 0 row 0 acc 0]
    (if-let [char (get-in input [row col])]
      (recur (inc col) row (if (special? char)
                             (apply + acc (find-number [row col] input finisher))
                             acc))
      (if (= row (count input))
        acc
        (recur 0 (inc row) acc)))))

;; ## part 1
;; We are interested in all characters that are not numbers nor full stops
(defn special?
  "special character?"
  [char]
  (not (or (= \. char)
           (Character/isDigit char))))

(general-solution mock-input-1 special? (partial mapv :num))
(general-solution input special? (partial mapv :num))

;; ## part 2
(def stars? (partial = \*))

(general-solution mock-input-1 stars? (fn [lst]
                                        [(if (= (count lst) 2)
                                           (apply * (mapv :num lst))
                                           0)]))
(general-solution input stars? (fn [lst]
                                 [(if (= (count lst) 2)
                                    (apply * (mapv :num lst))
                                    0)]))
