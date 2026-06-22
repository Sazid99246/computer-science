use "hw3.sml";

fun assert (msg, expected, actual) =
    if expected = actual
    then print ("PASS: " ^ msg ^ "\n")
    else print ("FAIL: " ^ msg ^ "\n");

val test1_1 = only_lowercase ["Apple", "banana", "Cherry", "date"] = ["banana", "date"]
val test1_2 = only_lowercase [] = []
val test1_3 = only_lowercase ["a", "B", "c"] = ["a", "c"]
val _ = assert ("only_lowercase", true, test1_1 andalso test1_2 andalso test1_3)

val test2_1 = longest_string1 ["A", "bc", "def", "gh"] = "def"
val test2_2 = longest_string1 ["abc", "def", "ghi"] = "abc"
val test2_3 = longest_string1 [] = ""
val _ = assert ("longest_string1", true, test2_1 andalso test2_2 andalso test2_3)

val test3_1 = longest_string2 ["A", "bc", "def", "gh"] = "def"
val test3_2 = longest_string2 ["abc", "def", "ghi"] = "ghi"
val test3_3 = longest_string2 [] = ""
val _ = assert ("longest_string2", true, test3_1 andalso test3_2 andalso test3_3)

val test4_1 = longest_string3 ["abc", "def", "ghi"] = "abc"
val test4_2 = longest_string4 ["abc", "def", "ghi"] = "ghi"
val _ = assert ("longest_string_helpers", true, test4_1 andalso test4_2)

val test5_1 = longest_lowercase ["Apple", "banana", "Cherry", "date"] = "banana"
val test5_2 = longest_lowercase ["Apple", "Cherry"] = ""
val _ = assert ("longest_lowercase", true, test5_1 andalso test5_2)

val test6_1 = caps_no_X_string "aBxXXxDdx" = "ABDD"
val test6_2 = caps_no_X_string "XxxxX" = ""
val test6_3 = caps_no_X_string "hello" = "HELLO"
val _ = assert ("caps_no_X_string", true, test6_1 andalso test6_2 andalso test6_3)

val test7_1 = first_answer (fn x => if x > 3 then SOME (x * 2) else NONE) [1, 2, 4, 5] = 8
val test7_2 = (first_answer (fn x => NONE) [1, 2, 3]; false) handle NoAnswer => true
val _ = assert ("first_answer", true, test7_1 andalso test7_2)

val test8_1 = all_answers (fn x => if x > 2 then SOME [x, x] else NONE) [3, 4] = SOME [3, 3, 4, 4]
val test8_2 = all_answers (fn x => if x > 2 then SOME [x] else NONE) [3, 1, 4] = NONE
val test8_3 = all_answers (fn x => SOME [x]) [] = SOME []
val _ = assert ("all_answers", true, test8_1 andalso test8_2 andalso test8_3)

val test9b_1 = count_wildcards WildcardP = 1
val test9b_2 = count_wildcards (TupleP [WildcardP, VariableP "x", WildcardP]) = 2
val _ = assert ("count_wildcards", true, test9b_1 andalso test9b_2)

val test9c_1 = count_wild_and_variable_lengths (TupleP [WildcardP, VariableP "foo"]) = 4
val _ = assert ("count_wild_and_variable_lengths", true, test9c_1)

val test9d_1 = count_a_var ("x", TupleP [VariableP "x", VariableP "y", VariableP "x"]) = 2
val test9d_2 = count_a_var ("z", TupleP [VariableP "x", VariableP "y"]) = 0
val _ = assert ("count_a_var", true, test9d_1 andalso test9d_2)

val test10_1 = check_pat (TupleP [VariableP "x", VariableP "y", WildcardP]) = true
val test10_2 = check_pat (TupleP [VariableP "x", VariableP "x"]) = false
val test10_3 = check_pat (ConstructorP ("x", VariableP "x")) = true
val _ = assert ("check_pat", true, test10_1 andalso test10_2 andalso test10_3)

val test11_1 = match (Unit, UnitP) = SOME []
val test11_2 = match (Constant 5, VariableP "v") = SOME [("v", Constant 5)]
val test11_3 = match (Tuple [Unit, Constant 4], TupleP [WildcardP, VariableP "n"]) = SOME [("n", Constant 4)]
val test11_4 = match (Constant 5, ConstantP 6) = NONE
val _ = assert ("match", true, test11_1 andalso test11_2 andalso test11_3 andalso test11_4)

val test12_1 = first_match Unit [ConstantP 5, UnitP] = SOME []
val test12_2 = first_match (Constant 10) [VariableP "x", WildcardP] = SOME [("x", Constant 10)]
val test12_3 = first_match (Constant 5) [UnitP, ConstantP 6] = NONE
val _ = assert ("first_match", true, test12_1 andalso test12_2 andalso test12_3)

val mock_constructors = [("foo", "bar", IntT), ("baz", "bar", UnitT)]
val test_ch_1 = typecheck_patterns (mock_constructors, [TupleP [VariableP "x", VariableP "y"], TupleP [WildcardP, WildcardP]]) = SOME (TupleT [AnythingT, AnythingT])
val test_ch_2 = typecheck_patterns (mock_constructors, [ConstructorP ("foo", ConstantP 5), ConstructorP ("baz", UnitP)]) = SOME (DatatypeT "bar")
val test_ch_3 = typecheck_patterns (mock_constructors, [ConstantP 5, UnitP]) = NONE
val _ = assert ("challenge_problem", true, test_ch_1 andalso test_ch_2 andalso test_ch_3)
