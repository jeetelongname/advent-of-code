#lang racket

;; I get a list of numbers, work out if the previous is larger or smaller.
;; tag the numbers,
;; filter out all of the decreases
;; count

(define input (map string->number (file->lines "inputs/day1.txt")))
;; NA, larger, smaller
(define (enumerate lst)
  (for/list ([elm lst]
             [idx (length lst)])
    (cons idx elm)))

(define (spoon lst)
  (filter-map (λ (elm-big-boy)
                (let ([idx (car elm-big-boy)]
                      [elm (cdr elm-big-boy)])
                  (and (not (zero? idx)) (< (list-ref lst (- idx 1)) elm)))) (enumerate lst)))


(length (spoon input))
;; part 2
;; iterate through, add last 2
(length (spoon (for/list  ([elm input]
                           [idx (length input)]
                           #:when (not (or (zero? idx) (= 1 idx))))
                 (+ elm (list-ref input (- idx 1)) (list-ref input (- idx 2))))))

;; https://clips.twitch.tv/EntertainingBitterReubenTheThing-vs1t9bQ02VNBGMVo
;; https://clips.twitch.tv/GenerousBlatantGiraffePicoMause-SMByPpgnIsZ787VI
