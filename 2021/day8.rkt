#lang racket

(require threading)
(define input (~>>  (file->lines "inputs/day8.sample.txt")
                    (map (λ (x) (string-split x "|")))
                    (map (λ (x) (map (λ (y) (string-split y " ")) x)))))

;; part 1
(for/fold ([sum 0])
          ([lst (map cadr input)])
  (+ sum  (length (filter (λ (x)
                            (member (string-length x) '(2 3 4 7))) lst))))

;; decode my input
;; work

;; 1 be can mean either cf
;; 7 edb can mean acf diffing it with 1 gives us a value
;; a = d
;; gather all length 6 strings the number 6 is the one that shares only one letter of 1
;; that shared letter is our f (f = e)
;; we know our f so we can work out our c (c = b)
;; three is the only 5 character string to conta∈ all of one
;; 2 will conta∈ the c value while 5 contains the f value
;; diff 8 with the 2 6ers, remove known values, 6 and 8 should be equal while zero will not be so we can work out d
;; 1 7 a 6 f c 3 2 5 8 6 0
