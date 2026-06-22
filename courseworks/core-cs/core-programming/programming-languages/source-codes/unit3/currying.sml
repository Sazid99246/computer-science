fun sorted3_tupled (x, y, z) = z >= y andalso y >= x

val t1 = sorted3_tupled (7,9,11)

(* new way: currying *)
			
val sorted3 = fn x => fn y => fn z => z >= y andalso y >= x

(*fun sorted3 x = fn y => fn z => ... *)

val t2 = ((sorted3 7) 9) 11
