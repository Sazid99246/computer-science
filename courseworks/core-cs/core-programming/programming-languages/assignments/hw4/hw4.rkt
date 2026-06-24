#lang racket

(provide (all-defined-out)) ;; so we can put tests in a second file

;; 1. sequence
(define (sequence spacing low high)
  (if (> low high)
      null
      (cons low (sequence spacing (+ low spacing) high))))

;; 2. string-append-map
(define (string-append-map xs suffix)
  (map (lambda (x) (string-append x suffix)) xs))

;; 3. list-nth-mod
(define (list-nth-mod xs n)
  (cond [(< n 0) (error "list-nth-mod: negative number")]
        [(null? xs) (error "list-nth-mod: empty list")]
        [else (car (list-tail xs (remainder n (length xs))))]))

;; 4. stream-for-k-steps
(define (stream-for-k-steps s k)
  (if (= k 0)
      null
      (let ([next (s)])
        (cons (car next) (stream-for-k-steps (cdr next) (- k 1))))))

;; 5. funny-number-stream
(define funny-number-stream
  (letrec ([f (lambda (n)
                (cons (if (= (remainder n 6) 0) (- n) n)
                      (lambda () (f (+ n 1)))))])
    (lambda () (f 1))))

;; 6. dan-then-dog
(define dan-then-dog
  (letrec ([dan (lambda () (cons "dan.jpg" dog))]
           [dog (lambda () (cons "dog.jpg" dan))])
    dan))

;; 7. stream-add-one
(define (stream-add-one s)
  (lambda ()
    (let ([next (s)])
      (cons (cons 1 (car next)) (stream-add-one (cdr next))))))

;; 8. cycle-lists
(define (cycle-lists xs ys)
  (letrec ([f (lambda (n)
                (lambda ()
                  (cons (cons (list-nth-mod xs n) (list-nth-mod ys n))
                        (f (+ n 1)))))])
    (f 0)))

;; 9. vector-assoc
(define (vector-assoc v vec)
  (letrec ([f (lambda (idx)
                (if (>= idx (vector-length vec))
                    #f
                    (let ([elt (vector-ref vec idx)])
                      (if (and (pair? elt) (equal? (car elt) v))
                          elt
                          (f (+ idx 1))))))])
    (f 0)))

;; 10. caching-assoc
(define (caching-assoc xs n)
  (let ([cache (make-vector n #f)]
        [slot 0])
    (lambda (v)
      (let ([cache-res (vector-assoc v cache)])
        (if cache-res
            cache-res
            (let ([assoc-res (assoc v xs)])
              (if assoc-res
                  (begin
                    (vector-set! cache slot assoc-res)
                    (set! slot (remainder (+ slot 1) n))
                    assoc-res)
                  #f)))))))

;; 11. while-greater macro
(define-syntax while-greater
  (syntax-rules (do)
    [(while-greater e1 do e2)
     (let ([max-val e1])
       (letrec ([loop (lambda ()
                        (if (> e2 max-val)
                            (loop)
                            #t))])
         (loop)))]))

;; 12. Challenge Problem: cycle-lists-challenge
(define (cycle-lists-challenge xs ys)
  (letrec ([loop (lambda (cur-x cur-y)
                   (lambda ()
                     (let ([x-pair (if (null? cur-x) xs cur-x)]
                           [y-pair (if (null? cur-y) ys cur-y)])
                       (cons (cons (car x-pair) (car y-pair))
                             (loop (cdr x-pair) (cdr y-pair))))))])
    (loop xs ys)))

;; 13. Challenge Problem: caching-assoc-lru
(define (caching-assoc-lru xs n)
  (let ([cache (make-vector n #f)]     ; elements will be pairs: (assoc-pair . age)
        [counter 0])                   ; global tick/logical time
    (lambda (v)
      ;; Custom vector association to find the element and track its position
      (letrec ([find-in-cache (lambda (idx)
                                (if (>= idx n)
                                    #f
                                    (let ([elt (vector-ref cache idx)])
                                      (if (and elt (equal? (caar elt) v))
                                          idx
                                          (find-in-cache (+ idx 1))))))])
        (let ([cache-idx (find-in-cache 0)])
          (if cache-idx
              (let ([hit (vector-ref cache cache-idx)])
                (set! counter (+ counter 1))
                (vector-set! cache cache-idx (cons (car hit) counter))
                (car hit))
              (let ([assoc-res (assoc v xs)])
                (if assoc-res
                    (begin
                      (set! counter (+ counter 1))
                      ;; Find slot to replace (either empty or minimum time stamp)
                      (letrec ([find-slot-to-replace (lambda (idx min-idx min-val)
                                                       (if (>= idx n)
                                                           min-idx
                                                           (let ([elt (vector-ref cache idx)])
                                                             (if (not elt)
                                                                 idx ; immediate slot available
                                                                 (if (< (cdr elt) min-val)
                                                                     (find-slot-to-replace (+ idx 1) idx (cdr elt))
                                                                     (find-slot-to-replace (+ idx 1) min-idx min-val))))))])
                        (let ([slot (find-slot-to-replace 0 0 +inf.0)])
                          (vector-set! cache slot (cons assoc-res counter))
                          assoc-res)))
                    #f))))))))