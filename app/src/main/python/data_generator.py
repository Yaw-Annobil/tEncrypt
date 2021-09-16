# Convert encoding data into 8-bit binary
# form using ASCII value of characters

def generate(data):
    # list of binary codes
    # of given data
    new_data = []

    for i in data:
        new_data.append(format(ord(i),'08b'))

    return new_data
