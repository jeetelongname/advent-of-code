(ns twenty-twenty-two.day5
  (:require [clojure.string :as string])
  (:require [clojure.core.matrix :as matrix])
  (:import  java.lang.Character))

(def actual (slurp "input/day5.txt"))

(def example "    [D]
[N] [C]
[Z] [M] [P]
 1   2   3

move 1 from 2 to 1
move 3 from 1 to 3
move 2 from 2 to 1
move 1 from 1 to 2")

(defn stacks
  "Take in a string and return a list of stacks
  A stack is a seq where
  the push operation is `cons`
  the peek operation is `first`
  the pop operation is `rest`"
  [string]
  (-> (first (string/split string #"\n\n"))
      (string/split #"\n")
      last
      (string/split #" ")
      (->> (filter (comp not empty?))
           (map (fn [_] [])))))

(defn trim-or-cde
  ""
  [str]
  (if (seq (string/trim str))
    (re-find #"[\w]" str)
    ""))

(defn even-out
  ""
  [max-length list]
  (let [to-go (- max-length (count list))]
    (if (zero? to-go)
      list
      (recur max-length (flatten (cons list '("")))))))

(defn parse-stacks
  ""
  [string]
  (let [parse (comp (partial even-out (count (stacks string)))
                    (partial map trim-or-cde)
                    (partial map string/trim)
                    (partial map string/join)
                    (partial partition-all 4)
                    seq)]

    (-> (first (string/split string #"\n\n"))
        string/split-lines
        butlast
        (->> (map parse))
        matrix/transpose
        (->> (map (partial filter seq))))))

(defn numeric? [s]
  (if-let [s (seq s)]
    (let [s (if (= (first s) \-) (next s) s)
          s (drop-while #(Character/isDigit %) s)
          s (if (= (first s) \.) (next s) s)
          s (drop-while #(Character/isDigit %) s)]
      (empty? s))))

(defn parse-instructions
  ""
  [string]
  (let [parse (comp
               (partial map dec)
               (partial map (comp parse-long str))
               (partial filter numeric?)
               #(string/split % #" "))]
    (-> (last (string/split string #"\n\n"))
        string/split-lines
        (->> (map parse)))))

(defn move
  ""
  [stack instruction]
  (loop [[amount from to] (into [] instruction)
         acc (into [] stack)]
    (if (== -1 amount)
      acc
      (let [peek (first (get acc from)) ;; peek stack
            popped (update acc from #(into [] (rest %)))
            pushed (update popped to #(into [] (cons peek %)))]
        (recur [(dec amount) from to] pushed)))))

(defn move-group
  ""
  [stack instruction]
  (let [[idx from to] (into [] instruction)
        amount (inc idx)
        peeked (take amount (get stack from))
        popped (update stack from (partial drop amount))
        pushed (update popped to  #(into [] (flatten (cons peeked %))))]
    pushed))

(defn solve
  ""
  [fn input]
  (let [string input]
    (->> (reduce fn (mapv (partial into []) (parse-stacks string)) (parse-instructions string))
         (map first)
         (apply str))))

(comment
  ;; part 1
  (solve move actual)

  ;; part 2
  (solve move-group actual))
