N = 20
low = 0
high = 1001
guess = (high+low)//2
count = 1
while guess != N:
    if guess < N:
        low = guess
    elif guess > N:
        high = guess
        guess = (high+low)//2
        count += 1

print("count:",count)
print("answer:",guess)
