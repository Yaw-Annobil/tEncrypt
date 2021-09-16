from PIL import Image

import io

from encrytion import encrypt


def encode(image_, data):
    # bytes = image_.decode()
    image = Image.open(io.BytesIO(image_))

    if len(data) == 0:
        raise ValueError('Data is Empty')

    new_image = image.copy()

    encrypted_image = encrypt(new_image, data)

    f = io.BytesIO()

    encrypted_image.save(f, format= 'png')

    # import random

    # new_image_name = f'{save_dir}Encoded_{random.randint(0,100000)}.png'
    #
    # encrypted_image.save(new_image_name)

    # e_image = Image.open(new_image_name)
    #
    # e_image.show()



    return f.getvalue()

