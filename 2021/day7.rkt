#lang racket

(require threading)

(define input (~> (file->string "inputs/day7.txt")
                  (string-trim "\n")
                  (string-split ",")
                  (map string->number _)))
;; part 1
(define (sum-cool subbr f lst)
  (apply + (map (λ (x)
                  (f x subbr)) lst)))

(define (median lst)
  (list-ref (sort lst <)
            (round (/ (length lst) 2))))

(printf "part 1: ~a\n" (sum-cool (median input)
                                 (lambda (x subr)
                                   (abs (- x subr)))
                                 input))
;; part 2
(define (mean lst)
  (/ (apply + lst) (length lst)))

(define (sum-to n)
  (* 0.5 n (+ n 1)))

(printf "part 2: ~a\n" (sum-cool (floor (mean input))
                                 (λ (x subr)
                                   (sum-to (abs (- x subr))))
                                 input))
