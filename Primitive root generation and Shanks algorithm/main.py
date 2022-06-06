import random
import math


def generate_primitive_root(p):
    a = random.randint(2, p - 2)
    return (-(a * a)) % p


def shanks(a, p, b):
    m = 1 + int(math.sqrt(p - 1))
    print(m)  # bun
    table = {(a ** j)%p: j for j in range(0, m)}
    print(table)  # bun
    y = b
    for i in range(0, m):
        print(pow(pow(a, m, p), -1, p))
        aux = b * pow(pow(pow(a, m, p), -1, p), i, p) % p
        print("aux: ", aux)
        if aux in table.keys():
            print(i, table[aux])
            return i * m + table[aux]


if __name__ == '__main__':
    p = 23
    a = 7
    b = 10
    # print("Log_", a, b, "=", shanks(a, p, b))
#
    print( pow(-8,-1,11)* (-5) % 11)