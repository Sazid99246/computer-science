;; CSE341, Programming Languages, Homework 5

#lang racket
(provide (all-defined-out)) ;; so we can put tests in a second file

;; definition of structures for MUPL programs - Do NOT change
(struct var  (string) #:transparent)  ;; a variable, e.g., (var "foo")
(struct int  (num)    #:transparent)  ;; a constant number, e.g., (int 17)
(struct add  (e1 e2)  #:transparent)  ;; add two expressions
(struct isgreater (e1 e2)    #:transparent) ;; if e1 > e2 then 1 else 0
(struct ifnz (e1 e2 e3) #:transparent) ;; if not zero e1 then e2 else e3
(struct fun  (nameopt formal body) #:transparent) ;; a recursive(?) 1-argument function
(struct call (funexp actual)       #:transparent) ;; function call
(struct mlet (var e body) #:transparent) ;; a local binding (let var = e in body) 
(struct apair   (e1 e2) #:transparent) ;; make a new pair
(struct first   (e)     #:transparent) ;; get first part of a pair
(struct second  (e)     #:transparent) ;; get second part of a pair
(struct munit   ()      #:transparent) ;; unit value -- good for ending a list
(struct ismunit (e)     #:transparent) ;; if e1 is unit then 1 else 0

;; a closure is not in "source" programs; it is what functions evaluate to
(struct closure (env fun) #:transparent) 

;; =============================================================================
;; Problem 1: Warm-Up
;; =============================================================================

;; (a) Convert a Racket list to a MUPL list
(define (racketlist->mupllist rl)
  (cond [(null? rl) (munit)]
        [else (apair (car rl) (racketlist->mupllist (cdr rl)))]))

;; (b) Convert a MUPL list back to a Racket list
(define (mupllist->racketlist ml)
  (cond [(munit? ml) null]
        [else (cons (apair-e1 ml) (mupllist->racketlist (apair-e2 ml)))]))

;; =============================================================================
;; Problem 2: Implementing the MUPL Language
;; =============================================================================

;; lookup a variable in an environment — Do NOT change this function
(define (envlookup env str)
  (cond [(null? env) (error "unbound variable during evaluation" str)]
        [(equal? (car (car env)) str) (cdr (car env))]
        [#t (envlookup (cdr env) str)]))

(define (eval-under-env e env)
  (cond [(var? e) 
         (envlookup env (var-string e))]
        [(int? e) e]
        [(closure? e) e]
        [(munit? e) e]
        [(add? e) 
         (let ([v1 (eval-under-env (add-e1 e) env)]
               [v2 (eval-under-env (add-e2 e) env)])
           (if (and (int? v1)
                    (int? v2))
               (int (+ (int-num v1) (int-num v2)))
               (error "MUPL addition applied to non-number")))]
        [(isgreater? e)
         (let ([v1 (eval-under-env (isgreater-e1 e) env)]
               [v2 (eval-under-env (isgreater-e2 e) env)])
           (if (and (int? v1) (int? v2))
               (if (> (int-num v1) (int-num v2)) (int 1) (int 0))
               (error "MUPL isgreater applied to non-number")))]
        [(ifnz? e)
         (let ([v1 (eval-under-env (ifnz-e1 e) env)])
           (if (int? v1)
               (if (not (= (int-num v1) 0))
                   (eval-under-env (ifnz-e2 e) env)
                   (eval-under-env (ifnz-e3 e) env))
               (error "MUPL ifnz condition must be an integer")))]
        [(fun? e)
         (closure env e)]
        [(mlet? e)
         (let ([v (eval-under-env (mlet-e e) env)])
           (eval-under-env (mlet-body e) (cons (cons (mlet-var e) v) env)))]
        [(apair? e)
         (apair (eval-under-env (apair-e1 e) env)
                (eval-under-env (apair-e2 e) env))]
        [(first? e)
         (let ([v (eval-under-env (first-e e) env)])
           (if (apair? v)
               (apair-e1 v)
               (error "MUPL first applied to non-pair")))]
        [(second? e)
         (let ([v (eval-under-env (second-e e) env)])
           (if (apair? v)
               (apair-e2 v)
               (error "MUPL second applied to non-pair")))]
        [(ismunit? e)
         (let ([v (eval-under-env (ismunit-e e) env)])
           (if (munit? v) (int 1) (int 0)))]
        [(call? e)
         (let ([cl (eval-under-env (call-funexp e) env)]
               [act (eval-under-env (call-actual e) env)])
           (if (closure? cl)
               (let* ([f (closure-fun cl)]
                      [c-env (closure-env cl)]
                      [ext-env (cons (cons (fun-formal f) act) c-env)]
                      [final-env (if (fun-nameopt f)
                                     (cons (cons (fun-nameopt f) cl) ext-env)
                                     ext-env)])
                 (eval-under-env (fun-body f) final-env))
               (error "MUPL call expected a closure")))]
        [#t (error (format "bad MUPL expression: ~v" e))]))

;; Do NOT change
(define (eval-exp e)
  (eval-under-env e null))
        
;; =============================================================================
;; Problem 3: Expanding the Language
;; =============================================================================

(define (ifmunit e1 e2 e3) 
  (ifnz (ismunit e1) e2 e3))

(define (mlet* bs e2) 
  (if (null? bs)
      e2
      (mlet (car (car bs)) (cdr (car bs)) (mlet* (cdr bs) e2))))

(define (ifeq e1 e2 e3 e4) 
  (mlet "_x" e1
        (mlet "_y" e2
              (ifnz (isgreater (var "_x") (var "_y"))
                    e4
                    (ifnz (isgreater (var "_y") (var "_x"))
                          e4
                          e3)))))

;; =============================================================================
;; Problem 4: Using the Language
;; =============================================================================

(define mupl-filter 
  (fun "filter" "f"
       (fun null "xs"
            (ifmunit (var "xs")
                     (munit)
                     (mlet "v" (call (var "f") (first (var "xs")))
                           (ifnz (var "v")
                                 (apair (first (var "xs")) 
                                        (call (call (var "filter") (var "f")) (second (var "xs"))))
                                 (call (call (var "filter") (var "f")) (second (var "xs")))))))))

(define mupl-all-gt
  (mlet "filter" mupl-filter
        (fun null "i"
             (call (var "filter") 
                   (fun null "x" (isgreater (var "x") (var "i")))))))

;; =============================================================================
;; Problem 5: Challenge Problem
;; =============================================================================

(struct fun-challenge (nameopt formal body freevars) #:transparent) ;; a recursive(?) 1-argument function

(require racket/set)

;; Helper function to calculate free variables and return (cons transformed-AST free-vars-set)
(define (compute-free-vars-helper e)
  (cond [(var? e) 
         (cons e (set (var-string e)))]
        [(int? e) (cons e (set))]
        [(munit? e) (cons e (set))]
        [(closure? e) (cons e (set))]
        [(add? e)
         (let* ([res1 (compute-free-vars-helper (add-e1 e))]
                [res2 (compute-free-vars-helper (add-e2 e))])
           (cons (add (car res1) (car res2))
                 (set-union (cdr res1) (cdr res2))))]
        [(isgreater? e)
         (let* ([res1 (compute-free-vars-helper (isgreater-e1 e))]
                [res2 (compute-free-vars-helper (isgreater-e2 e))])
           (cons (isgreater (car res1) (car res2))
                 (set-union (cdr res1) (cdr res2))))]
        [(ifnz? e)
         (let* ([res1 (compute-free-vars-helper (ifnz-e1 e))]
                [res2 (compute-free-vars-helper (ifnz-e2 e))]
                [res3 (compute-free-vars-helper (ifnz-e3 e))])
           (cons (ifnz (car res1) (car res2) (car res3))
                 (set-union (cdr res1) (cdr res2) (cdr res3))))]
        [(apair? e)
         (let* ([res1 (compute-free-vars-helper (apair-e1 e))]
                [res2 (compute-free-vars-helper (apair-e2 e))])
           (cons (apair (car res1) (car res2))
                 (set-union (cdr res1) (cdr res2))))]
        [(first? e)
         (let* ([res (compute-free-vars-helper (first-e e))])
           (cons (first (car res)) (cdr res)))]
        [(second? e)
         (let* ([res (compute-free-vars-helper (second-e e))])
           (cons (second (car res)) (cdr res)))]
        [(ismunit? e)
         (let* ([res (compute-free-vars-helper (ismunit-e e))])
           (cons (ismunit (car res)) (cdr res)))]
        [(call? e)
         (let* ([res1 (compute-free-vars-helper (call-funexp e))]
                [res2 (compute-free-vars-helper (call-actual e))])
           (cons (call (car res1) (car res2))
                 (set-union (cdr res1) (cdr res2))))]
        [(mlet? e)
         (let* ([res1 (compute-free-vars-helper (mlet-e e))]
                [res2 (compute-free-vars-helper (mlet-body e))]
                [body-free (set-remove (cdr res2) (mlet-var e))])
           (cons (mlet (mlet-var e) (car res1) (car res2))
                 (set-union (cdr res1) body-free)))]
        [(fun? e)
         (let* ([res (compute-free-vars-helper (fun-body e))]
                [f-free (cdr res)]
                [f-free (set-remove f-free (fun-formal e))]
                [f-free (if (fun-nameopt e)
                            (set-remove f-free (fun-nameopt e))
                            f-free)])
           (cons (fun-challenge (fun-nameopt e) (fun-formal e) (car res) f-free)
                 f-free))]
        [#t (error (format "bad MUPL expression: ~v" e))]))

;; Top level function expected by grading environment
(define (compute-free-vars e)
  (car (compute-free-vars-helper e)))

(define (eval-under-env-c e env)
  (cond [(var? e) 
         (envlookup env (var-string e))]
        [(int? e) e]
        [(closure? e) e]
        [(munit? e) e]
        [(add? e) 
         (let ([v1 (eval-under-env-c (add-e1 e) env)]
               [v2 (eval-under-env-c (add-e2 e) env)])
           (if (and (int? v1) (int? v2))
               (int (+ (int-num v1) (int-num v2)))
               (error "MUPL addition applied to non-number")))]
        [(isgreater? e)
         (let ([v1 (eval-under-env-c (isgreater-e1 e) env)]
               [v2 (eval-under-env-c (isgreater-e2 e) env)])
           (if (and (int? v1) (int? v2))
               (if (> (int-num v1) (int-num v2)) (int 1) (int 0))
               (error "MUPL isgreater applied to non-number")))]
        [(ifnz? e)
         (let ([v1 (eval-under-env-c (ifnz-e1 e) env)])
           (if (int? v1)
               (if (not (= (int-num v1) 0))
                   (eval-under-env-c (ifnz-e2 e) env)
                   (eval-under-env-c (ifnz-e3 e) env))
               (error "MUPL ifnz condition must be an integer")))]
        [(fun-challenge? e)
         (let ([opt-env (filter (lambda (binding) 
                                  (set-member? (fun-challenge-freevars e) (car binding))) 
                                env)])
           (closure opt-env e))]
        [(mlet? e)
         (let ([v (eval-under-env-c (mlet-e e) env)])
           (eval-under-env-c (mlet-body e) (cons (cons (mlet-var e) v) env)))]
        [(apair? e)
         (apair (eval-under-env-c (apair-e1 e) env)
                (eval-under-env-c (apair-e2 e) env))]
        [(first? e)
         (let ([v (eval-under-env-c (first-e e) env)])
           (if (apair? v) (apair-e1 v) (error "MUPL first applied to non-pair")))]
        [(second? e)
         (let ([v (eval-under-env-c (second-e e) env)])
           (if (apair? v) (apair-e2 v) (error "MUPL second applied to non-pair")))]
        [(ismunit? e)
         (let ([v (eval-under-env-c (ismunit-e e) env)])
           (if (munit? v) (int 1) (int 0)))]
        [(call? e)
         (let ([cl (eval-under-env-c (call-funexp e) env)]
               [act (eval-under-env-c (call-actual e) env)])
           (if (closure? cl)
               (let* ([f (closure-fun cl)] 
                      [c-env (closure-env cl)]
                      [ext-env (cons (cons (fun-challenge-formal f) act) c-env)]
                      [final-env (if (fun-challenge-nameopt f)
                                     (cons (cons (fun-challenge-nameopt f) cl) ext-env)
                                     ext-env)])
                 (eval-under-env-c (fun-challenge-body f) final-env))
               (error "MUPL call expected a closure")))]
        [#t (error (format "bad MUPL expression: ~v" e))]))

;; Do NOT change this
(define (eval-exp-c e)
  (eval-under-env-c (compute-free-vars e) null))