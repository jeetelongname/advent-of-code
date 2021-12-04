#lang racket

(define input (map (lambda (list)
                     (map (lambda (char)
                            (match char
                              [#\0 0]
                              [#\1 1]))
                          (string->list list)))
                   (file->lines "inputs/day3.txt")))

(define (bin->den bin)
  (foldr (lambda (elem acc)
           (+ acc (* (car elem) (expt 2 (cdr elem)))))
   0
   (map cons (reverse bin) (range 12))))

(define (clean input)
  (for/fold ([sum '(() () () () () () () () () () () ())])
            ([elm input])
    (for/list ([char elm]
               [idx (range 12)])
      (append (list-ref sum idx) (list (list-ref elm idx))))))

(define (list-of-maxes input)
  (for/list ([lst-num input])
    (for/fold ([sum (cons 0 0)])
              ([elm lst-num])
      (let ([zeros (car sum)]
            [ones  (cdr sum)])
        (match elm
          [0 (cons (+ 1 zeros) ones)]
          [1 (cons zeros (+ 1 ones))])))) )

(define (gamma/epsilon input)
  (let ([pair->bool (lambda (pair)
                      (let ([fst (car pair)]
                            [lst (cdr pair)])
                        (if (> fst lst) 0 1)))])
    (for/fold ([power '(() ())])
              ([pair (list-of-maxes (clean input))]) ;; where all the magic happens
      (let* ([dom (pair->bool pair)]
             [sub (if (zero? dom) 1 0)])
        (cons (append (car power) (list dom))
              (append (cdr power) (list sub)))))))
;; part 1
(let* ([both (gamma/epsilon input)]
       [gamma (bin->den (car both))]
       [epsilon (bin->den (cddr both))])
  (* epsilon gamma))

;;;  part 2
(define (witter-away len extract-func)
  (bin->den (car (for/fold ([sum input])
                           ([index len]
                            #:break (= (length sum) 1))
                   (let ([elements (extract-func (gamma/epsilon sum))])
                     (filter (lambda (num)
                               (= (list-ref num index)
                                  (list-ref elements index))) sum))))))

(define oxygen (witter-away 12 car))
(define carbon (witter-away 12 cddr))

(* oxygen carbon)
