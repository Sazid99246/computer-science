## 6.100A PSet 1: Part C
## Name:
## Time Spent:
## Collaborators:

##############################################
## Get user input for initial_deposit below ##
##############################################

initial_deposit = float(input("Enter the initial deposit: "))

#########################################################################
## Initialize other variables you need (if any) for your program below ##
#########################################################################

house_cost = 800000
portion_down_payment = 0.25
down_payment = house_cost * portion_down_payment

months = 36
steps = 0

#########################################################################
## Determine the lowest rate of return needed to get the down payment for your dream home below ##
#########################################################################

# Case 1: already enough money
if initial_deposit >= down_payment - 100:
    r = 0.0

# Case 2: impossible even with 100% return
elif initial_deposit * (1 + 1.0 / 12) ** months < down_payment - 100:
    r = None

# Case 3: use bisection search
else:
    low = 0.0
    high = 1.0

    while True:
        steps += 1

        r = (low + high) / 2

        amount_saved = initial_deposit * (1 + r / 12) ** months

        # close enough
        if abs(amount_saved - down_payment) <= 100:
            break

        # need larger rate
        elif amount_saved < down_payment:
            low = r

        # need smaller rate
        else:
            high = r

print("Best savings rate:", r)
print("Steps in bisection search:", steps)
