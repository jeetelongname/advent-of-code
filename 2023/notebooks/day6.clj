;;  # day 6
;;  The classic optimisation problem
(ns day6
  (:require [nextjournal.clerk :as clerk]
            [clojure.string :as str])
  (:import [java.net URL]
           [javax.imageio ImageIO]))

^{::clerk/visibility {:code :hide :result :hide}}
(def input (slurp "input/day6.txt"))

^{::clerk/visibility {:code :hide :result :hide}}
(def mock1 "Time:      7  15   30
Distance:  9  40  200")

;; # parsing
;; parsing is easy. break up into numbers and zip togehter
(defn parse
  [input]
  (apply (partial mapv (fn [one two] {:duration one :distance two}))
         (into []
               (comp
                (map #(str/split % #":"))
                (map rest)
                (map (partial map str/trim))
                (map (partial map #(str/split % #" ")))
                (map (partial mapv (comp
                                    ;; big ints are needed for part 2
                                    (partial mapv bigint)
                                    (partial filter (comp not str/blank?)))))
                (map first))

               (str/split-lines input))))

(clerk/example
 (parse mock1)
 (parse input))

;; ## part 1
;;
;; $speed = \frac{distance}{time}$
;;
;; $distance = speed \cdot time$
;;
;; Our hold time in analogus to our speed so we can treat it as such. Our run
;; time is $race duration - hold time$ or speed in this case. applying $distance = speed \cdot time$
;; we work out if the distance is more than the record distance.
;; Then we count up all the solutions. ez

(defn calculate [{race-duration :duration distance :distance} range-opts]
  (filter (fn [speed]
            (let [run (- race-duration speed)]
              (> (* speed run) distance)))
          (apply range race-duration range-opts)))

(defn solutions [map]
  (count (calculate map [])))

(-> mock1 parse first solutions)

;;  we then map this to all our races and apply `*` to work out our final answer
(defn part-1 [input]
  (transduce (map solutions)
             * 1 (parse input)))

(clerk/example
 (part-1 mock1)
 (part-1 input))

;; ## part 2

;; okay these numbers are too big for me to just kinda brute force it...

;; parsing is easy enough
(defn join [input-str]
  (-> input-str
      (str/replace " " "")
      parse))

(clerk/example
 (join mock1)
 (join input))

;; We cannot sadly brute force the solution anymore :( so we need be a little
;; smarter, What we can do is abuse the fact that `core.filter` is **lazy**
;;
;; If a number is inbetween the minium speed needed and the maximum speed
;; needed then it also counts because thats the way numbers work.
;; So all we need to do is calculate the min and max and then subtract to find
;; all the possible solutions. This is where the fact that `filter` is lazy comes
;; into play, we only need thr first valid solution in each case,
;; so we can just pull the first and no more maths needs to be done!
;; we can use this to find the min and then to find the max we just go backwards.
;; ez peezy
;;
;; This means we will need a reverse range to find the max
(range 100 0 -1)

(defn solutions-2 [map]
  (let [min (first (calculate map []))
        max (first (calculate map [0 -1]))]
    ;; some off by one error that I can't be bothered to fix
    (inc (- max min))))

(clerk/example
 (-> mock1
     join
     first
     solutions-2)

 (-> input
     join
     first
     solutions-2))

;; and because of the nature of these things this will also work for part 1
(defn part-1-optimised [input]
  (transduce (map solutions-2)
             * 1 input))

(clerk/example
 (-> mock1
     parse
     part-1-optimised)

 (-> input
     parse
     part-1-optimised))

(ImageIO/read (URL. "https://www.memesmonkey.com/images/memesmonkey/09/09f32a50c2f738cc52dfe6975fe38e5a.jpeg"))
