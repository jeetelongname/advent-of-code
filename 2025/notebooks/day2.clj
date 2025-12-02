(ns day2
  (:require [clojure.string :as string]
            [helpers :as h]
            [clojure.core.reducers :as r]
            [nextjournal.clerk :as clerk])
  (:import [java.net URL]
           [javax.imageio ImageIO]))

(def test-input "11-22,95-115,998-1012,1188511880-1188511890,222220-222224,1698522-1698528,446443-446449,38593856-38593862,565653-565659,824824821-824824827,2121212118-2121212124")

(def input (slurp "input/day2.txt"))

;; # Parsing
;; 
;; Parsing takes in an entry string, finds all the digits in it, maps them to
;; a Long and converts them to an end inclusive range.
;;
;; in my previous solution which worked for everything else, I split on the `-`
;; 
;; ```clojure
;; (string/split input #"-")
;;```
;; 
;; this worked for everything except the part 2 full solution for whatever
;; reason and has lead to hours of frustration.
(defn vec->range [[start end]]
  (range start (inc end)))

(defn parse [input]
  (->> (re-seq #"\d+" input)
       (mapv h/to-long)
       vec->range))

;; we then split on the commas to separate the entries and map parse over all
;; the elements
(def parsed-test-input
  (->>
   (string/split test-input #",")
   (map parse)))

(def parsed-input
  (->>
   (string/split input #",")
   (map parse)))

;; # Part 1
;; 
;; for the first part because I really dislike regexes, we split the
;; string in two and check if they are equal, simple enough
(defn is-invalid [id]
  (let [idstr (str id)
        n (quot (count idstr) 2)
        [first second] [(subs idstr 0 n) (subs idstr n)]]
    (= first second)))

;; then its just a matter of going through the list of ranges, for each range
;; filtering out each valid id based on our predicate, concatenate all of these
;; lists and fold them down. I am using the reducers library here because all of
;; these functions can and do run in parallel
(defn solve [pred parsed-input]
  (->> parsed-input
       (r/mapcat (partial r/filter pred))
       (r/fold + +)))

(solve is-invalid parsed-test-input)
(solve is-invalid parsed-input)

;; # Part two
;; 
;; I had to deal with my regex alergy for part 2, in reality this could probably
;; work for part 1 as well with some modification but eh.
;; a breakdown of the regex:
;; - `^`: matches with the first non whitespace char
;; - `(\d+)`: parens open a capture group, which can be references later
;;   - `\d`: matches against a digit
;;   - `+`: matches against one or more of the last character, in this case one or more digits
;; - `\1`: matches against the first capture group
;; - `+`: matches against one or more of the first capture group
;; - `$`: matches against the end of the string.
(defn is-invalid2 [id]
  (seq (re-find #"^(\d+)\1+$" (str id))))

(solve is-invalid2 parsed-test-input)
(solve is-invalid2 parsed-input)

^{::clerk/visibility {:code :fold}}
(ImageIO/read (URL. "https://media.tenor.com/T1zotsnaPJsAAAAe/bruh-meme.png"))
