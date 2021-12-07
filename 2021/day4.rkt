#lang racket

;;  list of numbers
;;  list of 2d lists
;;
;;  we check that the number called is not in the 2d list,
;;  if it is we mark it.
;;
;; we check if any row or coloum fully marked,
;; then we decare that board a winner and muliply
;; the last number by the sum of all the unmarked numbers

;; raco pkg install algorithms
(require algorithms)

;;; parsing
;; (define input (string-split (file->string "inputs/day4.txt") ","))
(define input (string-split (file->string "inputs/day4-sample.txt") ","))
(define boards (string-split (last input) "\n\n"))
(define guesses (map string->number (append (init input) (list (car boards)))))
(set! boards (map (lambda (lst)
                    (map (lambda (elem)
                           (map string->number (string-split elem #:trim? #t))) lst))
                  (map (lambda (str)
                         (string-split str "\n")) (cdr boards))))

;;; part 1
(define (board->checked-board board number)
  (for/list ([row board])
    (map (lambda (elem) (if (equal? elem number) #t elem)) row)))

(define (single-winner? board)
  (let ([truthy-board (map (lambda (row)
                             (map (lambda (elm)
                                    (eq? #t elm)) row)) board)])
    (any? `(,(for/fold ([sum #f])
                       ([row truthy-board]
                        #:break sum)

               (all? row))
            ,(for/fold ([sum #f])
                       ([col (length (car board))]
                        #:break sum)
               (let ([vals (for/list ([row truthy-board])
                             (list-ref row col))])
                 (all? vals)))))))

(define (winner? boards)
  (any? (map single-winner? (cdr boards))))

(define part1  (let* ([num&list  (for/fold ([sum (cons 0 boards)])
                                           ([number guesses]
                                            #:break (winner? sum))
                                   (cons number  (map (lambda (board)
                                                        (board->checked-board board number)) (cdr sum))))]
                      [num (car num&list)]
                      [lst (car (filter single-winner? (cdr num&list)))])
                 (* num (sum (flatten (map (lambda (row)
                                             (filter-not (lambda (elem)
                                                           (equal? elem #t)) row)) lst))))))

;; part 2
(let* ([num&list  (for/fold ([sum (cons 0 boards)])
                            ([number guesses]
                             #:break (all? `(,(winner? (cdr sum)) ,(= (length (cdr sum)) 2))))
                    (cons number (filter-not single-winner?
                                             (map (lambda (board)
                                                    (board->checked-board board number)) (cdr sum)))))]
       [num (car num&list)]
       [lst num&list])
  (printf "~a ~a" num lst)
  (* num (sum (flatten (map (lambda (row)
                                  (filter-not (lambda (elem)
                                                (equal? elem #t)) row)) lst)))))
