#lang racket

(define input (map string->number (string-split (string-trim  (file->string "inputs/day6.txt") "\n") ",")))
;; (define input (map string->number (string-split (string-trim "3,4,3,1,2" "\n") ",")))

(define (fish-buisness fish-timers)
  (let ([zeros (length (filter zero? fish-timers))])
    (map (lambda (x) (- x 1))
         (append (map (lambda (x)
                        (if (zero? x)
                            7
                            x)) fish-timers) (make-list zeros 9)))))

;; (length  (for/fold ([sum input])
;;                    ([len 80])
;;            (fish-buisness sum)))

;; part 2

(define (fish-buisness-formula fish-timers duration)
  (let* ([sorted (sort fish-timers <)]
         [base  (for/fold ([sum '(0 0 0 0 0 0 0 0 0)])
                          ([fish sorted])
                  (match fish
                    [0 (list (flatten (+ 1 (first sum) ) (rest sum)))]
                    [1 (list (first sum) (+ 1 (second sum)) (third sum) (fourth sum) (fifth sum) (sixth sum) (seventh sum) (eighth sum) (ninth sum))]
                    [2 (list (first sum)  (second sum)  (+ 1 (third sum)) (fourth sum)  (fifth sum) (sixth sum) (seventh sum) (eighth sum) (ninth sum))]
                    [3 (list (first sum)  (second sum) (third sum) (+ 1 (fourth sum))  (fifth sum) (sixth sum) (seventh sum) (eighth sum) (ninth sum))]
                    [4 (list (first sum)  (second sum) (third sum)  (fourth sum) (+ 1 (fifth sum)) (sixth sum) (seventh sum) (eighth sum) (ninth sum))]
                    [5 (list (first sum)  (second sum) (third sum)  (fourth sum)  (fifth sum) (+ 1 (sixth sum)) (seventh sum) (eighth sum) (ninth sum))]
                    [6 (list (first sum)  (second sum) (third sum)  (fourth sum)  (fifth sum)  (sixth sum) (+ 1 (seventh sum)) (eighth sum) (ninth sum))]
                    [7 (list (first sum)  (second sum) (third sum)  (fourth sum)  (fifth sum)  (sixth sum)  (seventh sum) (+ 1 (eighth sum)) (ninth sum))]
                    [8 (list (first sum)  (second sum) (third sum)  (fourth sum)  (fifth sum)  (sixth sum)  (seventh sum)  (eighth sum) (+ 1 (ninth sum)))]))])
    (for/fold ([sum base])
              ([_ duration])
      (let* ([zero (car sum)]
             [shift (append (cdr sum) (list (car sum)))])
        (list (first shift) (second shift) (third shift) (fourth shift)  (fifth shift) (sixth shift) (+ (seventh shift) zero) (eighth shift) (ninth shift))))))

(apply + (fish-buisness-formula input 256))
;; sort list
;; gather like elements, keep count
;;; loop begins
;; left shift all elements
;; if amount of 0 > 1 we add the amount to 6 # this is our fish reset
;; all zero valus get appended to list
;;; https://clips.twitch.tv/ResilientBlitheCucumberPeteZarollTie-NP9hC-vVkwCgjUZJ
