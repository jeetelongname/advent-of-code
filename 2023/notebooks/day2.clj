;; # Day 2
(ns day2
  (:require [clojure.string :as str]
            [nextjournal.clerk :as clerk])
  (:import [java.net URL]
           [javax.imageio ImageIO]))

(def input (-> (slurp "input/day2.txt")
               (str/split-lines)))

;; ## Part 1
;; ### Tokenise the string
;;
;; Instead of doing things in one go and giving myself a brain anurism. I first
;; take time to split the string up before I actually turn it into maps.
(defn tokenise
  "Tokenise one game input line into a map"
  [string]
  (let [[gameid gamestring] (str/split string #":")]
    {:gameid (-> (str/split gameid #" ")
                 last
                 Integer.)
     :games (into []
                  (comp (map #(str/split % #","))
                        (map (partial mapv #(-> %
                                                str/trim
                                                (str/split #" ")))))
                  (str/split gamestring #";"))}))

(tokenise (first input))

;; ### Parsing it together
;;
;; Most of the job was done by the tokeniser but we are still not at something
;; usable. I parse these tokens into maps, merging them with a map of zeros to
;; make sure they are all the same size
(defn parse-game
  "parse an indiviual game into a map of numbers"
  [[number colour]]
  {(case colour
     "red" :red
     "blue" :blue
     "green" :green)
   (Integer. number)})

(defn parse
  "take a tokenised map, parse it into a new map"
  [tokenmap]
  (let [{gameid :gameid games :games} tokenmap]
    {:id gameid
     :games (into []
                  (comp
                   (map (partial map parse-game))
                   (map (partial apply merge {:red 0 :blue 0 :green 0})))
                  games)}))

;; This turns it into a datastructure I can use pretty easily
(-> input
    first
    tokenise
    parse)

;; ## solving
;; The general process of solving the problem is the same.
(defn number-cruncher
  "Crunch the numbers taking in a solve function"
  [solver]
  (transduce (comp (map tokenise)
                   (map parse))
             solver
             0 input))

;; ## part 1
;; reduce a true value, checking against the problem criterion.
;; if the criteron is met we add the id to the accumulated value.
;; otherwise we skip
(defn solvep1
  "take a game map and reduce the id value."
  ([res] res)
  ([acc {id :id games :games}]
   (if (reduce #(and %1
                     (<= (:red %2) 12)
                     (<= (:green %2) 13)
                     (<= (:blue %2) 14))
               true games)
     (+ acc id)
     acc)))

(number-cruncher solvep1)

;; ## part 2
;; part 2 we need to find the maximum number of each cube for each
;; colour. we destructure, our accumulated map and our current map find the max
;; of each value to construct our new map
;; work out the power and add it to our accumulator
(defn solvep2
  "take a game map and return the power "
  ([res] res)
  ([acc {games :games}]
   (let [minmap (reduce (fn [{accred :red accgreen :green accblue :blue}
                             {valred :red valgreen :green valblue :blue}]
                          {:red (max accred valred)
                           :green (max accgreen valgreen)
                           :blue (max accblue valblue)})
                        {:red 0 :green 0 :blue 0}
                        games)
         power (apply * (vals minmap))]
     (+ acc power))))

(number-cruncher solvep2)

;; done
(ImageIO/read (URL."https://i.pinimg.com/originals/58/9d/e9/589de9574ee0252bc2db4b59fccadac6.jpg"))
