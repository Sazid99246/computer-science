fun neg_counter e =
    case e of
	Int _ => 0
      | Neg e => 1 + neg_counter e
      | Add (e1,e2) => neg_counter e1 + neg_counter e2

fun stringer e =
    case e of
	Int i => Int.toString i
      | Neg e => "­(" ^ (stringer e) ^ ")"
      | Add (e1,e2) => "(" ^ (stringer e1) ^ " + " ^ (stringer e2) ^ ")"

fun evaluator e =
    case e of
	Int i => Int i
      | Neg e => (case evaluator e of Int i => Int (~i))
      | Add (e1,e2) => (case (evaluator e1, evaluator e2) of
			    (Int i, Int j) => Int (i + j))
