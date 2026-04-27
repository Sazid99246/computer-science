def part_a(yearly_salary, portion_saved, cost_of_dream_home):
	#########################################################################
	
	portion_down_payment = 0.25
	amount_saved = 0
	r = 0.05
	months = 0
	
	###############################################################################################
	## Determine how many months it would take to get the down payment for your dream home below ##
	###############################################################################################
	
	down_payment = cost_of_dream_home * portion_down_payment
	monthly_salary = yearly_salary / 12
	monthly_saving = monthly_salary * portion_saved
	
	while amount_saved < down_payment:
	    amount_saved += amount_saved * r / 12
	    amount_saved += monthly_saving
	    months += 1
	
	print(f"Number of months {months}")
	return months