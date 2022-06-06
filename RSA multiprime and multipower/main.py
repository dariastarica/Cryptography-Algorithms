from Cryptodome.Util import number
import random
import math
import time


def generate_private_key(e, phi):
    return pow(e, -1, phi)


def generate_public_key(phi):
    e = random.randint(1, 1000)
    while math.gcd(e, phi) != 1:
        e = random.randint(1, 1000)
    print("E: ", e)
    return e


def multi_prime_rsa():
    p = number.getPrime(512)
    q = number.getPrime(512)
    r = number.getPrime(512)
    n = p * q * r

    def multi_prime_encryption(message):
        def calculate_phi():
            return (p - 1) * (q - 1) * (r - 1)

        phi = calculate_phi()

        public_key = generate_public_key(phi)
        private_key = generate_private_key(public_key, phi)
        return (message ** public_key) % n, public_key, private_key

    def multi_prime_decryption(enc_message, public_key, private_key):
        def exponential_algorithm():
            return (enc_message ** private_key) % n

        def TCR_algorithm():
            d_p = pow(public_key, -1, p - 1)
            d_q = pow(public_key, -1, q - 1)
            d_r = pow(public_key, -1, r - 1)

            q_inv = pow(q, -1, p)
            r_inv = pow(r, -1, q)

            m_1 = (enc_message ** d_p) % p
            m_2 = (enc_message ** d_q) % q
            m_3 = (enc_message ** d_r) % r

            h1 = (q_inv * (m_1 - m_2)) % p
            h2 = (r_inv * (m_2 - m_3)) % q

            m = m_2 + h1 * q
            m = m_3 + h2 * r
            return m

        def TCR_algorithm_with_addition_chains():


            d_p = pow(public_key, -1, p - 1)
            d_q = pow(public_key, -1, q - 1)
            d_r = pow(public_key, -1, r - 1)

            q_inv = pow(q, -1, p)
            r_inv = pow(r, -1, q)

            m_1 = (enc_message ** d_p) % p
            m_2 = (enc_message ** d_q) % q
            m_3 = (enc_message ** d_r) % r

            h1 = (q_inv * (m_1 - m_2)) % p
            h2 = (r_inv * (m_2 - m_3)) % q

            m = m_2 + h1 * q
            m = m_3 + h2 * r
            return m


        start = time.perf_counter()
        exp_decrypted_message = exponential_algorithm()
        stop = time.perf_counter()
        print("Exponential algorithm time: ", stop - start)

        start = time.perf_counter()
        tcr_decrypted_message = TCR_algorithm()
        stop = time.perf_counter()
        print("TCR algorithm time: ", stop - start)

        start = time.perf_counter()
        tcr_decrypted_message_2 = TCR_algorithm_with_addition_chains()
        stop = time.perf_counter()
        print("TCR algorithm with addition chains time: ", stop - start)

        return exp_decrypted_message, tcr_decrypted_message, tcr_decrypted_message_2

    message = 24
    encrypted_message, public_key, private_key = multi_prime_encryption(message)
    print("Multiprime encrypted message: ", encrypted_message)
    decrypted_message_exp, decrypted_message_tcr, decrypted_message_tcr_2 = multi_prime_decryption(encrypted_message, public_key, private_key)
    print("Multiprime Decrypted Message with Exponential Algorithm: ", decrypted_message_exp)
    print("Multiprime Decrypted Message with TCR Algorithm: ", decrypted_message_tcr)
    print("Multiprime Decrypted Message with TCR with Addition Chains Algorithm: ", decrypted_message_tcr_2)



def multi_power_rsa():
    p = number.getPrime(512)
    q = number.getPrime(512)
    n = pow(p, 2) * q

    def multi_power_encryption(message):
        def calculate_phi():
            return p * (p - 1) * (q - 1)

        phi = calculate_phi()

        public_key = generate_public_key(phi)
        private_key = generate_private_key(public_key, phi)
        return (message ** public_key) % n, public_key, private_key

    def multi_power_decryption(enc_message, public_key, private_key):
        def exponential_algorithm():
            return (enc_message ** private_key) % n

        def TCR_algorithm():
            dp = private_key % (p - 1)
            dq = private_key % (q - 1)
            pk_inv = pow(p * p, -1, q)

            mq = pow(enc_message, dq, q)
            A = pow(enc_message, dp, p)
            p_2 = p * p
            F = pow(A, public_key, p_2)
            E = enc_message - F % p_2
            B = E / p
            K = A * B * pow(public_key * F, -1, p) % p
            A = A + p * K
            V = (mq - A) * pk_inv % q
            A = A + p * p * V
            return A

        start = time.perf_counter()
        exp_decrypted_message = exponential_algorithm()
        stop = time.perf_counter()
        print("Exponential algorithm time: ", stop - start)

        start = time.perf_counter()
        tcr_decrypted_message = TCR_algorithm()
        stop = time.perf_counter()
        print("TCR algorithm time: ", stop - start)

        return exp_decrypted_message, tcr_decrypted_message

    message = 24
    encrypted_message, public_key, private_key = multi_power_encryption(message)
    print("Multipower encrypted message: ", encrypted_message)
    decrypted_message_exp, decrypted_message_tcr = multi_power_decryption(encrypted_message, public_key, private_key)
    print("Multiprime Decrypted Message with Exponential Algorithm: ", decrypted_message_exp)
    print("Multiprime Decrypted Message with TCR Algorithm: ", decrypted_message_tcr)


if __name__ == '__main__':
    multi_prime_rsa()
    # multi_power_rsa()
