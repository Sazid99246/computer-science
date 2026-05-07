def keys_with_value(aDict, target):
    """
    aDict: a dictionary
    target: an integer or string
    Assume that keys and values in aDict are integers or strings.
    Returns a sorted list of the keys in aDict with the value target.
    If aDict does not contain the value target, returns an empty list.
    """
    # Your code here
    result = []
    for key, value in aDict.items():
        if value == target:
            result.append(key)
    return sorted(result)


# Examples:
aDict = {1: 2, 2: 4, 5: 2}
target = 2
print(keys_with_value(aDict, target))  # prints the list [1,5]


def all_positive(d):
    """
    d is a dictionary that maps int:list
    Suppose an element in d is a key k mapping to value v (a non-empty list).
    Returns the sorted list of all k whose v elements sums up to a
    positive value.
    """
    result = []
    for key, value in d.items():
        if sum(value) > 0:
            result.append(key)
    return sorted(result)


# Examples:
d = {5: [2, -4], 2: [1, 2, 3], 1: [2]}
print(all_positive(d))   # prints the list [1, 2]
