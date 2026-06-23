val r = ref NONE

(*
val _ = r := SOME "hi"

val i = 1 + valof (!r)
*)

type 'a foo = 'a ref
val f : 'a -> 'a foo = ref
val r2 = f NONE (* also need value restriction here *)

(* where the value restriction arises despite no mutation *)
val pairwithone = List.map (fn x => (x,1))

(* a workaround *)
fun pairwithone2 xs = List.map (fn x => (x,1)) xs
