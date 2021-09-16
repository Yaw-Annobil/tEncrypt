'''
Created By Johnny copied from Geeks for Geeks
'''

# PIL module is used to extract pixels of the image and modify it
# from PIL import Image

# Convert encoding data into 8-bit binary
# form using ASCII value of characters

from modify_pixels import modify


def encrypt(new_image, data):
    w = new_image.size[0]
    (x, y) = (0, 0)

    for pixel in modify(new_image.getdata(), data):

        new_image.putpixel((x, y), pixel)

        if x == w - 1:
            x = 0

            y += 1
        else:
            x += 1
    return new_image
