fun sum_of_evens(x : int) =
    if x <= 0
    then 0
    else if x mod 2 = 0
    then x + sum_of_evens(x-2)
    else sum_of_evens(x-1);

fun to_binary(x : int) =
    if x <= 0
    then 0
    else (x mod 2) + 10 * to_binary(x div 2);
