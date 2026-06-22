fun compose (f, g) = fn x => f(g x)

(* ('b -> 'c) * ('a -> 'b) -> ('a -> 'c) *)

infix !>

fun x !> f = f x

fun sqrt_of_abs i = i !> abs !> Real.fromInt !> Math.sqrt
		      
fun backup1 (f, g) = fn x => case f x of
				 NONE => g x
			      | SOME y => y

fun backup2 (f, g) = fn x => f x handle _ => g x
