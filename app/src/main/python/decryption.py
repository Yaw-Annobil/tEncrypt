# decode the data in the image
from PIL import Image
import io


def decode(image_):
    # image = Image.open(image_, "r")

    image = Image.open(io.BytesIO(image_))


    data = ''

    image_data = iter(image.getdata())

    while True:
        pixels = [value for value in image_data.__next__()[:3] +
                  image_data.__next__()[:3] +
                  image_data.__next__()[:3]]

        # string of binary data
        binary_str = ''

        for i in pixels[:8]:
            if i % 2 == 0:
                binary_str += '0'
            else:
                binary_str += '1'
        data += chr(int(binary_str, 2))

        if pixels[-1] % 2 != 0:
            return data


# print(decode('Encrypted_2.png'))


