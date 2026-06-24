#lang racket

(provide (all-defined-out))

;;;;;; zero-argument functions (thunks) delay evaluation

(define (factorial-normal x)
  (if (= x 0)
      1
      (* x (factorial-normal (- x 1)))))

(define (my-if-bad e1 e2 e3)
  (if e1 e2 e3))

; seems to be okay:
(define (t1) (my-if-bad #t (+ 1 1) (+ 2 2)))

; but wait:
(define (t2) (my-if-bad #t (print "hi") (print "bye")))
(define (t3) (my-if-bad #t (+ 1 1) (t3)))
(define (t4) (let ([x 2]) (my-if-bad #f (begin (set! x 7) 0) (+ x x))))

(define (factorial-bad x)
  (my-if-bad (= x 0)
             1
             (* x 
                (factorial-bad (- x 1)))))

(define (my-if-strange-but-works e1 e2 e3)
  (if e1 (e2) (e3)))

(define (factorial-okay x)
  (my-if-strange-but-works (= x 0)
			   (lambda () 1)
			   (lambda () (* x (factorial-okay (- x 1))))))
