fun is_older (date1 : int*int*int, date2 : int*int*int) =
    if (#1 date1) <> (#1 date2)
    then (#1 date1) < (#1 date2)
    else if (#2 date1) <> (#2 date2)
    then (#2 date1) < (#2 date2)
    else (#3 date1) < (#3 date2)

fun number_in_month (dates : (int*int*int) list, month : int) =
    if null dates
    then 0
    else
	let val num = number_in_month ((tl dates), month)
	in
	    if (#2 (hd dates)) = month
	    then num + 1
	    else num
	end

fun number_in_months (dates : (int*int*int) list, months : int list) =
    if null months
    then 0
    else number_in_month (dates, (hd months)) + number_in_months (dates, (tl months))

fun dates_in_month (dates : (int*int*int) list, month : int) =
    if null dates
    then []
    else
	let val in_dates = dates_in_month ((tl dates), month)
	in
	    if (#2 (hd dates)) = month
	    then (hd dates) :: in_dates
	    else in_dates
	end

fun dates_in_months (dates : (int*int*int) list, months : int list) =
    if null months
    then []
    else dates_in_month (dates, (hd months)) @ dates_in_months (dates, (tl months))

fun get_nth (strings : string list, n : int) =
    if n = 1
    then (hd strings)
    else get_nth((tl strings), n-1)

fun date_to_string (date : int*int*int) =
    let val months = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"]
    in
	get_nth (months, (#2 date)) ^ " " ^ Int.toString (#3 date) ^ ", " ^ Int.toString (#1 date)
    end

fun number_before_reaching_sum (sum : int, nums : int list) =
    if (hd nums) >= sum
    then 0
    else 1 + number_before_reaching_sum(sum-(hd nums), (tl nums))

fun what_month (days : int) =
    let val days_every_month = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31]
    in
	number_before_reaching_sum(days, days_every_month) + 1
    end

fun month_range (day1 : int, day2 : int) =
    if day1 > day2
    then []
    else what_month(day1) :: month_range(day1+1, day2)

fun oldest (dates : (int*int*int) list) =
    if null dates
    then NONE
    else
	let fun oldest_nonempty (dates : (int*int*int) list) =
		if null (tl dates)
		then (hd dates)
		else 
		    let val tl_ans = oldest_nonempty(tl dates)
		    in
			if is_older((hd dates), tl_ans)
			then (hd dates)
			else tl_ans
		    end
	in
	    SOME (oldest_nonempty dates)
	end

fun cumulative_sum (numbers: int list) =
    let fun helper (numbers: int list, sum: int) =
	    if null numbers
	    then []
	    else ((hd numbers) + sum) :: helper((tl numbers), (hd numbers) + sum)
    in
	helper(numbers, 0)
    end
	    
fun in_list (elem : int, elems : int list) =
    if null elems
    then false
    else if (hd elems) = elem
    then true
    else in_list(elem, (tl elems))

fun remove_duplicates (elems : int list) =
    if null elems
    then []
    else if in_list((hd elems), (tl elems))
    then remove_duplicates((tl elems))
    else (hd elems) :: remove_duplicates((tl elems))

fun number_in_months_challenge (dates : (int*int*int) list, months : int list) =
    number_in_months(dates, remove_duplicates(months))

fun dates_in_months_challenge (dates : (int*int*int) list, months : int list) =
    dates_in_months(dates, remove_duplicates(months))

fun reasonable_date (date : int*int*int) =
    let fun leap_year (year : int) = (year mod 400 = 0) orelse (not(year mod 100 = 0) andalso year mod 4 = 0)
        fun reasonable_year (year : int) = year > 0
        fun reasonable_month (month : int) = month > 0 andalso month <= 12
        fun reasonable_day (day : int) = day > 0
        fun get_nth (nums : int list, n : int) =
            if n = 1
            then hd nums
            else get_nth(tl nums, n-1)
        val non_leap_year_days = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31]
        val leap_year_days = [31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31]
        fun reasonable_leap_date (date : int*int*int) =
            reasonable_year (#1 date) andalso reasonable_month(#2 date) andalso reasonable_day(#3 date)
            andalso leap_year(#1 date) andalso (#3 date) <= get_nth(leap_year_days, (#2 date))
        fun reasonable_nonleap_date (date : int*int*int) = 
            reasonable_year (#1 date) andalso reasonable_month(#2 date) andalso reasonable_day(#3 date)
            andalso not(leap_year(#1 date)) andalso (#3 date) <= get_nth(non_leap_year_days, (#2 date))
    in
        reasonable_leap_date(date) orelse reasonable_nonleap_date(date)
    end

fun number_in_month (dates : (int*int*int) list, month : int) =
    if null dates
    then 0
    else
	let val num = number_in_month ((tl dates), month)
	in
	    if (#2 (hd dates)) = month
	    then num + 1
	    else num
	end

fun number_in_months (dates : (int*int*int) list, months : int list) =
    if null months
    then 0
    else number_in_month (dates, (hd months)) + number_in_months (dates, (tl months))

fun dates_in_month (dates : (int*int*int) list, month : int) =
    if null dates
    then []
    else
	let val in_dates = dates_in_month ((tl dates), month)
	in
	    if (#2 (hd dates)) = month
	    then (hd dates) :: in_dates
	    else in_dates
	end
