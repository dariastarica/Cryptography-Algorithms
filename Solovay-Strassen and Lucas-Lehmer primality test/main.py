import random
import math


def Solovay_Strassen_test(n):
    def compute_Jacobi_Symbol(a, n):
        if n <= 0 or (n % 2) == 0:
            return 0
        j = 1
        if a < 0:
            a = -a
            if (n % 4) == 3:
                j = -j
        while a != 0:
            while (a % 2) == 0:
                a = a / 2
                if (n % 8) == 3 or (n % 8) == 5:
                    j = -j

            aux = a
            a = n
            n = aux
            if (a % 4) == 3 and (n % 4) == 3:
                j = -j
            a = a % n

        if n == 1:
            return j
        else:
            return 0

    t = 60
    for i in range(0, t):
        a = random.randint(2, n - 2)
        r = pow(a, (n - 1) // 2, n)
        if r != 1 and r != n - 1:
            return "composite"
        s = compute_Jacobi_Symbol(a, n)
        # print("JS: ", s, "pt a= ", a, ", n= ", n)
        if r != s % n:
            return "composite"
    return "prime"


def Lucas_Lehmer_test(n):  # n - Mersenne number
    def modular_reduction():

    s = int(math.log(n + 1, 2))
    for d in range(2, int(math.sqrt(s))):
        if s % d == 0:
            return "composite"
    u = 4
    for k in range(0, s - 2):
        u = (u * u - 2) % n  # algoritm din cursul 2
    if u == 0:
        return "prime"
    else:
        return "composite"


if __name__ == '__main__':
    number = 31
    print(Solovay_Strassen_test(number))
