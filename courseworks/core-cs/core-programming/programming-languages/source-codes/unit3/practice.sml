fun fold (f, acc, xs) =
    case xs of
	[] => acc
      | x::xs' => fold (f, f(acc,x), xs')

fun flat_map (f, xs) =
    fold (fn (acc, x) => acc @ f x, [], xs)

fun even_string_total_length xs =
    let
	fun even_then_length (acc, s) =
	    if size s mod 2 = 0
	    then acc + size s
	    else acc
    in
	fold (even_then_length, 0, xs)
    end


fun swap_pairs (xs) =
    map (fn (a, b) => (b, a), xs)

fun size xs =
    fold(fn (acc, x) => acc + 1, 0, xs)

fun remove_all (x, xs) =
    filter(fn y => not(y = x), xs)

fun contains (x, xs) =
    fold(fn (acc, x') => acc orelse x = x', false, xs)

fun intersect (xs, ys) =
    filter(fn x => contains (x, ys), xs)
