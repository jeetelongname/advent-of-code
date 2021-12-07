#lang racket

;; find all the intersections of all lines
;; intersection? function
;;
(define input (map (lambda (str)
                     (map (lambda (str-point)
                            (map string->number (string-split str-point ",")))
                          (string-split str " -> " #:trim? #t))) (file->lines "inputs/day5.txt")))
(define (extract-x line)
  (match (flatten line)
    [(list x1 _ x2 _)
     (list x1 x2)]))

(define (extract-y line)
  (match (flatten line)
    [(list _ y1 _ y2)
     (list y1 y2)]))

(define (nodiag? line)
  (match (flatten line)
    [(list x1 y1 x2 y2)
     (or (= x1 x2)
         (= y1 y2))]))

(define (filter-cardies lines)
  (filter nodiag? lines))

;; expand out all lines
;; we iterate through x
;; if x are = then start at y1 end at y2
;; if y are = then start at x1 end at x2
(define (expand-lines line)
  (if (apply = (extract-x line))

      (map (lambda (y-val)
             (cons (caar line) y-val))
           (let ([y-limits (extract-y line)])
             (if (> (car y-limits) (cadr y-limits))
                 (inclusive-range (cadr y-limits) (car y-limits))
                 (inclusive-range (car y-limits) (cadr y-limits)))))

      (map (lambda (x-val)
             (cons x-val (caadr line)))
           (let ([x-limits (extract-x line)])
             (if (> (car x-limits) (cadr x-limits))
                 (inclusive-range (cadr x-limits) (car x-limits))
                 (inclusive-range (car x-limits) (cadr x-limits)))))))

(define clean (for/fold ([sum '()])
                        ([lst (map expand-lines (filter-cardies input))])
                (append sum lst)))

(- (for/fold ([sum 0])
             ([elem clean])
     (if (member elem (remove elem clean))
         (+ 1 sum)
         sum)) 1)
