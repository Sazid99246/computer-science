val x = 1

fun f y =
    let
	val x = y+1
    in
	fn z => x + y + z
    end

val x = 3

val g = f 4

val y = 5

val z = g 6

val x = 1

fun f y = 
    let 
        val x = y+1
    in
        fn z => x + y  + z
    end

val x = 3

val g = f 4 (* always adds 9 to its argument. always *)

val y = 5

val z = g 6

fun f g = 
    let 
        val x = 3
    in
        g 2
    end

val x = 4

fun h y = x + y (* always adds 4 to its argument. always *)

val z = f h
