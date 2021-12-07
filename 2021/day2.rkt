#lang racket

(define input (map (lambda (x)
                     (let* ([raw  (string-split x)]
                            [inst (car raw)]
                            [move (car (cdr raw))])
                       (cons inst (string->number move))))
                   (file->lines "inputs/day2.txt")))

(let* ([i (for/fold ([sum (cons 0 0)])
                    ([move input])
            (match (car move)
              ["forward"
               (cons (+ (cdr move) (car sum)) (cdr sum))]
              ["up"
               (cons (car sum) (- (cdr sum) (cdr move)))]
              ["down"
               (cons (car sum) (+ (cdr sum) (cdr move)))]))]
       [pos (car i)]
       [depth (cdr i)])
  (* pos depth))

;;  first: forward, second: depth, third: aim
(let* ([i (for/fold ([sum '(0 0 0)])
                    ([move input])
            (match (car move)
              ["up"
               (list (first sum)
                     (second sum)
                     (- (last sum) (cdr move)))]
              ["down"
               (list (first sum)
                     (second sum)
                     (+ (last sum) (cdr move)))]
              ["forward"
               (list (+ (first sum) (cdr move))
                     (+ (second sum) (* (cdr move) (last sum)))
                     (last sum) )]
              ))]
       [pos (first i)]
       [depth (second i)])
  (* pos depth))

;; sayid from lost
;; fix website link on twitch