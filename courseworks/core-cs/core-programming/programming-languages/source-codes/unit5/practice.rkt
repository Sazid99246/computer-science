#lang racket

(define (greater-by-one-list xs)
  (map (lambda (x) (+ 1 x)) xs))

(define (increase-by-one xs)
  (cond [(null? xs) null]
        [#t (begin (increase-by-one (mcdr xs))
                   (set-mcar! xs (+ (mcar xs) 1)))]))

(define silly-previous
  (let ([prev 0])
    (lambda (x) (let ([res prev])
                  (begin (set! prev x) res)))))

(define silly-only-unique
  (let ([prev null])
    (lambda (x)
      (if (member x prev)
          (error "silly-only-unique: value used previously")
          (begin (set! prev (cons x prev)) x)))))

(define powers-of-two
  (letrec ([next-thunk (lambda (x)
                         (cons x (lambda () (next-thunk (* x 2)))))])
    (lambda () (next-thunk 1))))

(define zero-through-three
  (letrec ([next-thunk (lambda (x)
                         (cons (modulo x 4)
                               (lambda () (next-thunk (+ x 1)))))])
    (lambda () (next-thunk 0))))

(define (zero-through-n n)
  (letrec ([next-thunk (lambda (x)
                         (cons (modulo x (+ n 1))
                               (lambda () (next-thunk (+ x 1)))))])
    (lambda () (next-thunk 0))))

(define (get-ith s i)
  (if (= i 0)
      (car (s))
      (get-ith (cdr (s)) (- i 1))))

(define (stream-maker init fn)
  (letrec ([next-thunk (lambda (x)
                         (cons x (lambda () (next-thunk (fn x)))))])
    (lambda () (next-thunk init))))

(define powers-of-two2 (stream-maker 1 (lambda (x) (* x 2))))