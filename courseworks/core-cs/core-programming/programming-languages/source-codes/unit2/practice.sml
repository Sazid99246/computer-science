fun swap_pair (a, b) =
    (b, a)

fun swap_pairs_list ps =
    case ps of
	[] => []
      | p :: ps' => swap_pair (p) :: swap_pairs_list(ps')

fun size xs =
    case xs of
	[] => 0
      | x :: xs' => 1 + size(xs')
		    
fun contains (x : int, xs) =
    case xs of
	[] => false
      | x' :: xs' => (x = x') orelse contains (x, xs')

fun remove_all (x : int, xs) =
    case xs of
	[] => []
      | x' :: xs' => if x = x'
		     then remove_all (x, xs')
		     else x' :: remove_all (x, xs')
					   

type cart = real * real
datatype shape =
	 Circle of cart * real (* coordinates and radius *)
	 | Square of cart * real (* coordinates and side length *)
	 | Rectangle of cart * real * real (* coordinates and side lengths *)

fun area sh =
    case sh of
	Circle (_, r) => 3.14 * r * r
      | Square (_, l) => l * l
      | Rectangle (_, w, h) => w * h

fun quadrant_one_only shs =
    let
	fun is_quadrant_one sh =
	    case sh of
		Circle((x, y), _) => x > 0.0 andalso y > 0.0
	      | Square((x, y), _) => x > 0.0 andalso y > 0.0
	      | Rectangle((x, y), _, _) => x > 0.0 andalso y > 0.0
    in
	case shs of
	    [] => []
	  | sh :: shs' => if is_quadrant_one (sh)
			  then sh :: quadrant_one_only (shs')
			  else quadrant_one_only (shs')
    end

fun construct_squares xs =
    case xs of
	[] => []
      | x :: xs' => Square((x, x), abs(x)) :: construct_squares(xs')


datatype exp = Constant of int
	     | Negate of exp
	     | Add of exp * exp
	     | Multiply of exp * exp

fun eval (Constant i) = i
  | eval (Add(e1, e2)) = (eval e1) + (eval e2)
  | eval (Negate e1) = ~ (eval e1)
  | eval (Multiply(e1, e2)) = (eval e1) * (eval e2)

datatype dessert =
	 IceCream of (string * int) (* flavor * num scoops *)
	 | Pie of (string * int) (* flavor * num slices *)
	 | Brownie of (int) (* number of brownies *)
	 | WhippedCream
	 | Feast of dessert list (* collection of desserts *)

fun add_whipped_cream ds =
    case ds of
	[] => []
      | d :: ds' => (d, WhippedCream) :: add_whipped_cream (ds')

fun ice_cream_feast fs =
    let
	fun help fs =
	    case fs of
		[] => []
	      | f :: fs' => IceCream(f, 1) :: help(fs')
    in
	Feast(help(fs))
    end

fun flatten d =
    case d of
	Feast (ds) =>
	let
	    fun help (ds) =
		case ds of
		    [] => []
		  | d :: ds' => flatten (d) @ help (ds')
	in
	    help (ds)
	end
      | _ => [d]

fun num_scoops (d, f) =
    case d of
	IceCream (f, i) => i
      | Feast (ds) =>
	let
	    fun help ds =
		case ds of
		    [] => 0
		  | d' :: ds' => num_scoops(d', f) + help(ds')
	in
	    help (ds)
	end
      | _ => 0

fun flavors d =
    case d of
	IceCream (f, _) => [f ^ " ice cream"]
      | Pie (f, _) => [f ^ " pie"]
      | Brownie (_) => ["brownie"]
      | WhippedCream => ["whipped cream"]
      | Feast (ds) =>
	let
	    fun help ds =
		case ds of
		    [] => []
		  | d' :: ds' => flavors (d') @ help (ds')
	in
	    help (ds)
	end

fun enough_ice_cream d =
    let
	fun plus_minus d =
	    case d of
		IceCream (_, i) => i
	      | Pie (_, i) => ~i
	      | Brownie (i) => ~i
	      | Feast (ds) =>
		let
		    fun help ds =
			case ds of
			    [] => 0
			  | d' :: ds' => plus_minus(d') + help(ds')
		in
		    help (ds)
		end
	      | _ => 0
    in
	plus_minus (d) >= 0
    end
