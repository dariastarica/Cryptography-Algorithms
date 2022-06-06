# Reed Solomon
import random
from itertools import combinations

k = 3
s = 1
n = k + 2 * s

def encoding(m, p):
    def input_vector_representation_inefficient(m):
        m_vector = []
        #  P - 161 bits; 20 ch at the time, binary repr of ascii < 161

        # repr in baza P
        int_m = int(m)
        r = int_m % p
        while r:
            m_vector.append(r)
            int_m = int_m // p
            r = int_m % p
        return list(reversed(m_vector))

    def input_vector_representation_efficient():
        p = 9
        vector = [int.to_bytes(ord(ch),1,'big') for ch in m]
        print(vector)

    input_vector_representation_efficient()
    print(input_vector_representation_inefficient(m))

    m_vector = input_vector_representation_inefficient(m)

    y = []

    for i in range(1, n + 1):
        polynomial = 0
        for poz in range(0, k - 2):
            polynomial += ((m_vector[poz] * i + m_vector[poz + 1]) * i) % p
        y.append(polynomial % 11)
    return y  # d


def decoding(z):

    def compute_A():
        # compute combinatii de K elemente din 1...n
        possible_A = [list(c) for c in list(combinations(range(1, n + 1), 3))]
        for A in possible_A: # calculez coeficentul liber pana cand fc = 0
            sum_products = 0
            for i in A:
                product = 1
                j_product = 1
                for j in A:
                    if i != j:
                        j_product *= j
                        product *= i-j
                sum_products += z[i-1] * pow(product, p-2, p) * j_product
            fc = sum_products % p
            if fc == 0:
                return A
    A = compute_A()
    print("A: ", A)

    # def compute_polynomial():


def simulate_error(encoded_vector):
    random_poz = random.randint(0, len(encoded_vector) - 1)
    encoded_vector[random_poz] = (encoded_vector[random_poz] + random.randint(10, 100)) % p
    return encoded_vector


if __name__ == '__main__':
    input_m = "29"
    p = 11
    y_encoding = encoding(input_m, p)
    print(y_encoding)
    print(simulate_error(y_encoding))
    z = [9, 2, 6, 5, 8]  # one error
    m_decoded = decoding(z)
    print(m_decoded)
