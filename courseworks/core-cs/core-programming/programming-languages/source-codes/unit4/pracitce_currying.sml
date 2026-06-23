fun filter_by_example f x =
     List.filter (fn x' => f x = f x')

fun same_size_as xs = filter_by_example List.length xs

fun count_o s =
    List.length (List.filter (fn x => x = #"o") (String.explode s))

val silly_application = filter_by_example count_o "dogsarecool"

fun contains x =
    List.foldl (fn (x', acc) => acc orelse x' = x) false

fun filter_unique f prev xs =
    case xs of
	[] => []
      | x'::xs' =>
	let
	    val result = f x'
	in
	    if contains result prev
	    then filter_unique f prev xs'
	    else x' :: filter_unique f (result :: prev) xs'
	end

fun unique_sums xs = filter_unique List.length [] xs

fun all_that_contain x = (List.filter (contains x))

val even_only =
    List.map (List.filter (fn x => x mod 2 = 0))

fun even_only_not_empty xs =
    List.filter (not o List.null) (even_only xs)

val unique_size_not_empty = filter_unique String.size [0]

val names = ["Sazid", "Lazz", "Shofiqul", "Mukta", "Jhankar", "Maisha", "Jahid", "Promi"] : string list
					  
val all_pairs =
    List.map (fn x => List.map (fn y => (x, y)) names) names
