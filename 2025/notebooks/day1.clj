(ns day1
  (:require
   [nextjournal.clerk :as clerk]
   [clojure.string :as string]
   [clojure.core.reducers :as r]
   [helpers :as h]))

(def test-input "L68
L30
R48
L5
R60
L55
L1
L99
R14
L82")

;; as strings get cast to a collection of chars we can use that property to make
;; parsing easier we grab the first char of the input string and convert it into
;; an operation, as this is a number line left is subtraction and right is
;; addition
;; We take the rest of the input, convert it back into a string and convert it to an int
;; We return this as a map.
(defn parse [input]
  {:op (get {\L - \R +}
            (first input))
   :num (-> input
            rest
            string/join
            h/to-int)})

(def parsed-test-input (into [] (r/map parse (string/split-lines test-input))))

(clerk/table parsed-test-input)

;; # Part 1
;;
;;We begin by applying 1 roation, the function tracks both the current
;; location and the amount of zeros we apply the operation to the loc and the
;; number of steps, take a mod of 100 and if it equals zero we up the count, we
;; return the new loc either way
(defn apply-rotation [{loc :loc zeros :zeros} {op :op num :num}]
  (let [new-loc (mod (apply op [loc num]) 100)]
    {:loc new-loc :zeros (if (zero? new-loc)
                           (inc zeros)
                           zeros)}))

;; by thinking of a solution one step at a time we are able to trivially reduce
;; over the entire parsed input
(defn solve [input]
  (reduce apply-rotation {:loc 50 :zeros 0} input))

;; we then extract the count and we are done.
(:zeros (solve parsed-test-input))

;; apply that to the full input like so and we get the correct answer
(->> (slurp "input/day1.txt")
     string/split-lines
     (r/map parse)              ; parallel map
     (into [])
     solve
     :zeros)

;; # part 2
;; 
;; part two has sent me for a loop as we now need to track the cycles and the
;; crossings my first solution was to take the location of the dial (loc) and
;; the shift n, sum them together and find the quotient of that. This worked for
;; simple test cases but not for the entire input, I am unsure why
;;
;; My second solution was to traverse the number line manually, for each
;; operation i defined a left and a right if we came to zero we would add a
;; click and reset, as each instruction only moved in one direction this should
;; be fool proof, I tried it and it did not work again
;;
;; finally, at this point putting in 7 wrong attempts and overall having my
;; imposter syndrome flare up, and me thinking the problem may be deeper I
;; decided to lift a known working solution from
;; https://elken.github.io/aoc/src/solutions/2025/day01/. To my dismay it worked
;; and I robbed myself of figuring it out myself. My vauge understanding of the
;; solution is that it just tracks passes and not the actual location as that
;; does not matter. to keep things in bound we subtract 100 every time the
;; operation switches. We then work out the new loc, and figure out how many
;; times the location crosses the boundery by taking the quotient
;;
;; Again I do not like this solution, not because its bad, not because its slow
;; but because its not mine. I do not understand it, I have spent way to long on
;; this problem and I am not really that much better for it. The lesson here is
;; that no matter how hard the solution is, its better not to solve it than to
;; let someone else make a black box for you.
(defn apply-rotation2 [{:keys [loc zeros op]} {new-op :op num :num}]
  (let [loc (if (= op new-op) loc (- 100 loc))
        loc (+ (mod loc 100) num)
        new-zeros (quot loc 100)]
    {:loc loc :zeros (+ zeros new-zeros) :op new-op}))

(apply-rotation2 {:loc 50 :zeros 0 :op +} (parse "R1000"))

(defn solve2 [input]
  (reduce apply-rotation2 {:loc 50 :zeros 0 :op -} input))

(solve2 parsed-test-input)

(->> (slurp "input/day1.txt")
     string/split-lines
     (r/map parse)
     (into [])
     solve2
     :zeros)

;; # Redemption
;; The fact that I have not been able to solve it myself is eating
;; at me a little so I decided to take another crack at it, I have decided to
;; try the second solution again.

(defn left-click [loc]
  (if (= 0 loc)
    [true 99]
    [false (dec loc)]))

(defn right-click [loc]
  (if (= loc 99)
    [true 0]
    [false (inc loc)]))

(defn left [loc n]
  (reduce (fn [{:keys [count loc]} _]
            (let [[passed? loc] (left-click loc)]
              {:count (if passed?
                        (inc count)
                        count)
               :loc loc}))
          {:count 0 :loc loc}
          (range n)))

(left 0 1)

(defn right [loc n]
  (reduce (fn [{:keys [count loc]} _]
            (let [[passed? loc] (right-click loc)]
              {:count (if passed?
                        (inc count)
                        count)
               :loc loc}))
          {:count 0 :loc loc}
          (range n)))

(right 99 1)
(right 99 1000)

(defn parse2 [input]
  {:op (get {\L left, \R right} (first input))
   :num    (-> input
               rest
               string/join
               h/to-int)})

(defn apply-rotation-redeemed [{:keys [count loc]} {:keys [op num]}]
  (println count loc op num)
  (let [{new-count :count new-loc :loc} (op loc num)]
    {:count (+ count new-count) :loc new-loc}))

(defn solve-redeemed [input]
  (reduce apply-rotation-redeemed
          {:count 0, :loc 50}
          input))

(->> test-input
     string/split-lines
     (r/map parse2)
     (into [])
     solve-redeemed
     :count)

(->> (slurp "input/day1.txt")
     string/split-lines
     (r/map parse2)
     (into [])
     solve-redeemed
     :count)
