(* CSE341, HW3 Provided Code *)

exception NoAnswer

datatype pattern = WildcardP
                 | VariableP of string
                 | UnitP
                 | ConstantP of int
                 | ConstructorP of string * pattern
                 | TupleP of pattern list

datatype valu = Constant of int
              | Unit
              | Constructor of string * valu
              | Tuple of valu list

fun g f1 f2 p =
    let 
        val r = g f1 f2 
    in
        case p of
            WildcardP         => f1 ()
          | VariableP x       => f2 x
          | ConstructorP(_,p) => r p
          | TupleP ps         => List.foldl (fn (p,i) => (r p) + i) 0 ps
          | _                 => 0
    end

(**** for the challenge problem only ****)

datatype typ = AnythingT
             | UnitT
             | IntT
             | TupleT of typ list
             | DatatypeT of string

(**** you can put all your code here ****)
fun only_lowercase xs =
    List.filter (fn s => Char.isLower(String.sub(s, 0))) xs

fun longest_string1 xs =
    List.foldl (fn (s, acc) => if String.size s > String.size acc then s else acc) "" xs

fun longest_string2 xs =
    List.foldl (fn (s, acc) => if String.size s >= String.size acc then s else acc) "" xs

fun longest_string_helper f xs =
    List.foldl (fn (s, acc) => if f(String.size s, String.size acc) then s else acc) "" xs

val longest_string3 = longest_string_helper (fn (x, y) => x > y)
val longest_string4 = longest_string_helper (fn (x, y) => x >= y)

val longest_lowercase = longest_string1 o only_lowercase

val caps_no_X_string = 
    String.implode o 
    List.filter (fn c => c <> #"x" andalso c <> #"X") o 
    List.map Char.toUpper o 
    String.explode

fun first_answer f xs =
    case xs of
        [] => raise NoAnswer
      | x::xs' => case f x of
                      SOME v => v
                    | NONE => first_answer f xs'

fun all_answers f xs =
    let
        fun helper ([], acc) = SOME acc
          | helper (x::xs', acc) =
            case f x of
                NONE => NONE
              | SOME lst => helper (xs', acc @ lst)
    in
        helper (xs, [])
    end

fun count_wildcards p =
    g (fn () => 1) (fn _ => 0) p

fun count_wild_and_variable_lengths p =
    g (fn () => 1) String.size p

fun count_a_var (s, p) =
    g (fn () => 0) (fn x => if x = s then 1 else 0) p

fun check_pat p =
    let
        fun get_vars pat =
            case pat of
                VariableP x => [x]
              | ConstructorP(_, p') => get_vars p'
              | TupleP ps => List.foldl (fn (p', acc) => get_vars p' @ acc) [] ps
              | _ => []

        fun has_repeats xs =
            case xs of
                [] => false
              | x::xs' => List.exists (fn y => x = y) xs' orelse has_repeats xs'
    in
        not (has_repeats (get_vars p))
    end

fun match (v, p) =
    case (v, p) of
        (_, WildcardP) => SOME []
      | (v, VariableP s) => SOME [(s, v)]
      | (Unit, UnitP) => SOME []
      | (Constant i, ConstantP j) => if i = j then SOME [] else NONE
      | (Constructor(s1, v'), ConstructorP(s2, p')) => if s1 = s2 then match(v', p') else NONE
      | (Tuple vs, TupleP ps) => if List.length vs = List.length ps
                                 then all_answers match (ListPair.zip(vs, ps))
                                 else NONE
      | _ => NONE

fun first_match v ps =
    SOME (first_answer (fn p => match(v, p)) ps)
    handle NoAnswer => NONE

fun typecheck_patterns (constructors, patterns) =
    let
        fun unify_types (t1, t2) =
            case (t1, t2) of
                (AnythingT, other) => SOME other
              | (other, AnythingT) => SOME other
              | (UnitT, UnitT) => SOME UnitT
              | (IntT, IntT) => SOME IntT
              | (DatatypeT s1, DatatypeT s2) => if s1 = s2 then SOME (DatatypeT s1) else NONE
              | (TupleT ts1, TupleT ts2) =>
                if List.length ts1 = List.length ts2
                then case all_answers (fn (x, y) => case unify_types (x, y) of SOME t => SOME [t] | NONE => NONE) (ListPair.zip (ts1, ts2)) of
                         SOME combined => SOME (TupleT combined)
                       | NONE => NONE
                else NONE
              | _ => NONE

        fun infer_pattern_type pat =
            case pat of
                WildcardP => SOME AnythingT
              | VariableP _ => SOME AnythingT
              | UnitP => SOME UnitT
              | ConstantP _ => SOME IntT
              | TupleP ps => 
                (case all_answers (fn p => case infer_pattern_type p of SOME t => SOME [t] | NONE => NONE) ps of
                     SOME ts => SOME (TupleT ts)
                   | NONE => NONE)
              | ConstructorP (cname, p') =>
                (case List.find (fn (x, _, _) => x = cname) constructors of
                     SOME (_, dt_name, inner_t) =>
                     (case infer_pattern_type p' of
                          SOME t' => (case unify_types (t', inner_t) of
                                          SOME _ => SOME (DatatypeT dt_name)
                                        | NONE => NONE)
                        | NONE => NONE)
                   | NONE => NONE)
    in
        case patterns of
            [] => NONE
          | p::ps => List.foldl (fn (pat, acc) =>
                                    case (acc, infer_pattern_type pat) of
                                        (SOME acc_t, SOME pat_t) => unify_types (acc_t, pat_t)
                                      | _ => NONE) (infer_pattern_type p) ps
    end
