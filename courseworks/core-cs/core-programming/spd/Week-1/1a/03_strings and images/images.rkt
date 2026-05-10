;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-beginner-reader.ss" "lang")((modname images) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #t)))
(require 2htdp/image)

;(circle 18 "solid" "red")
;(rectangle 30 40 "outline" "blue")
;(text "hello" 24 "orange")

(overlay (circle 10 "solid" "red")
         (circle 20 "solid" "yellow")
         (circle 30 "solid" "green"))