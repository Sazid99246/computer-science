signature RATIONAL =
sig
    type rational
    exception BadFrac
    val make_frac : int * int -> rational
    val toString  : rational -> string
    val add       : rational * rational -> rational
    val Whole     : int -> rational
end

structure Rational :> RATIONAL =
struct
    type rational = int * int
    exception BadFrac

    fun Whole i = (i, 1)
    
    fun make_frac (x, y) = 
        if y = 0 
        then raise BadFrac 
        else (x, y)

    fun toString (x, y) = 
        if y = 1 
        then Int.toString x
        else Int.toString x ^ "/" ^ Int.toString y
    
    fun add ((a, b), (c, d)) = 
        (a * d + b * c, b * d)
end

signature ICECREAMSHOP =
sig
    exception BadOrder
    type order
    val max_scoops : int
    val available_flavors : string list
    val buy_order : string * int * int -> order
    val consume_scoop : order -> order option
    val num_scoops : order -> int
    val has_scoops : order -> bool
end

structure IceCreamShop :> ICECREAMSHOP =
struct
     val max_scoops = 3  val available_flavors = ["vanilla", "chocolate",
			       "huckleberry", "moose tracks"]
     exception BadOrder
     type order = (string * int)
     fun buy_order (flavor, scoops, money) =
	 if money < scoops orelse scoops < 0 orelse scoops > max_scoops
	    orelse not (isSome(List.find (fn x => x = flavor)
					 available_flavors))
	 then raise BadOrder
	 else (flavor, scoops)
     fun consume_scoop (f, s) =
	 if s > 0
	 then SOME(f,s - 1)
	 else NONE
     fun num_scoops (_, s) = s
     fun has_scoops (_, s) = s > 0
end
