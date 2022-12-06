(ns twenty-twenty-two.day6)

(def examples '("mjqjpqmgbljsphdztnvjfqwrcgsmlb"
                "bvwbjplbgvbhsrlpgdmjqwftvncz"
                "nppdvjthqldpwncqszvftbrmjlhg"
                "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg"
                "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"))

(def actual (slurp "input/day6.txt"))

(defn find-marker
  ""
  [offset string]
  (loop [pointer 0]
    (let [chars (->> string
                     (drop pointer)
                     (take offset))
          unique? (== offset (count (distinct chars)))]
      (if unique?
        (+ pointer offset)
        (recur (inc pointer))))))

(comment
  ;; part 1
  (find-marker 4 actual)

  ;; part 2
  (find-marker 14 actual))
