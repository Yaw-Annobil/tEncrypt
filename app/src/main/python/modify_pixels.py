# Pixels are modified according to the
# 8-bit binary data and finally returned

from data_generator import generate


def modify(pix, data):
    datalist = generate(data)

    len_data = len(datalist)

    image_data = iter(pix)

    for i in range(len_data):

        pix = [value for value in image_data.__next__()[:3]
               + image_data.__next__()[:3]
               + image_data.__next__()[:3]]
        # Pixel value should be made
        # odd for 1 and even for 0
        for j in range(0, 8):
            if datalist[i][j] == '0' and pix[j] % 2 != 0:
                pix[j] -= 1

            elif datalist[i][j] == '1' and pix[j] % 2 == 0:
                if pix[j] != 0:
                    pix[j] -= 1
                else:
                    pix[j] += 1
                # pix[j] -= 1
                # Eighth pixel of every set tells
                # whether to stop ot read further.
                # 0 means keep reading; 1 means thec
                # message is over.
        if i == len_data - 1:
            if pix[-1] % 2 == 0:
                if pix[-1] != 0:
                    pix[-1] -= 1
                else:
                    pix[-1] += 1

        else:
            if pix[-1] % 2 != 0:
                pix[-1] -= 1

        pix = tuple(pix)
        yield pix[0:3]
        yield pix[3:6]
        yield pix[6:9]


