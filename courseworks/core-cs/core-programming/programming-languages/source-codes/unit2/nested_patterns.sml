fun zip3 list_triple =
    case list_triple of
	([], [], []) => []
      | (hd1: : t11, hd2: :tl2, hd3 : : t13) => (hd1,hd2,hd3) :: zip3(t11,t12,t13)
      | _ => raise ListLengthMismatch

fun unzip3 lst =
    case lst of
      | [] => ([], [],[])
      | (a, b,c) :: tl => let val (11,12,13) = unzip3 tl
