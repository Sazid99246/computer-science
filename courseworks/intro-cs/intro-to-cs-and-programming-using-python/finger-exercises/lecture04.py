N = int(input('Write a number: '))

i = 1
while i**3 < N:
    i += 1
    if i**3 == N:
        print(i)
    else:
        print('error')
