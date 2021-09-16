from encode import encode

from decryption import decode


def encrypt(name, data):
    saved_dir = encode(name, data)

    return saved_dir


# Press the green button in the gutter to run the script.
if __name__ == '__main__':
    # a = encrypt('/home/johnny/Downloads/th.jpeg', 'Hello Im working')

    print(decode('/Users/johna/PycharmProjects/sterganography/Encrypted_2.png'))
