#lang racket

(define input (map string->number (string-split (string-trim  (file->string "inputs/day6.txt") "\n") ",")))
;; (define input (map string->number (string-split (string-trim "3,4,3,1,2" "\n") ",")))
;; naieve approach
(define (fish-buisness fish-timers)
  (let ([zeros (length (filter zero? fish-timers))])
    (map (lambda (x) (- x 1))
         (append (map (λ (x) (if (zero? x) 7 x)) fish-timers) (make-list zeros 9)))))

(length  (for/fold ([sum input])
                   ([_ 80])
           (fish-buisness sum)))

;; scalable apporach
(define (fish-buisness-formula fish-timers duration)
  (let ([base (for/fold ([sum (make-list 9 0)])
                        ([fish fish-timers])
                (list-update sum fish add1))])
    (for/fold ([sum base])
              ([_ duration])
      (let* ([zero (car sum)]
             [shift (append (cdr sum) (list (car sum)))])
        (list-update shift 6 (λ (x) (+ x zero)))))))

(apply + (fish-buisness-formula input 256))
;; sort list
;; gather like elements, keep count
;;; loop begins
;; left shift all elements
;; if amount of 0 > 1 we add the amount to 6 # this is our fish reset
;; all zero valus get appended to list
;;; https://clips.twitch.tv/ResilientBlitheCucumberPeteZarollTie-NP9hC-vVkwCgjUZJ
