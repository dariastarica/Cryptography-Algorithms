import java.math.BigInteger;
import java.util.function.BiFunction;

public class RSA {

    private BigInteger n;
    private BigInteger p, q;
    private BigInteger e = new BigInteger("65537"); //sau calculat, gcd(e,phi(pq))=1
    private long runtime;

    public BigInteger generateN() {
        BigInteger startN = new BigInteger("1000000000000000");
        p = startN.nextProbablePrime();
        q = p.nextProbablePrime();
        return p.multiply(q);
    }

    public BigInteger calculatePhi() {
        return (p.subtract(BigInteger.valueOf(1))).multiply(q.subtract(BigInteger.valueOf(1)));
    }


    public String generatePrivateKey() {
        return modInverse(e, calculatePhi()).toString();
    }

    static BigInteger modInverse(BigInteger a, BigInteger m) {
        BigInteger m0 = m;
        BigInteger y = BigInteger.valueOf(0);
        BigInteger x = BigInteger.valueOf(1);

        if (m.equals(BigInteger.valueOf(1)))
            return BigInteger.valueOf(0);

        while (a.compareTo(BigInteger.valueOf(1)) > 0) {
            // q is quotient
            BigInteger q = a.divide(m);

            BigInteger t = m;

            // m is remainder now, process
            // same as Euclid's algo
            m = a.remainder(m);
            a = t;
            t = y;

            // Update x and y
            y = x.subtract(q.multiply(y));
            x = t;
        }

        // Make x positive
        if (x.compareTo(BigInteger.valueOf(0)) < 0)
            x = x.add(m0);

        return x;
    }

    public BigInteger cript(String plaintext) {
        BigInteger criptext;
        setN(generateN());

        criptext = parseCharsToInt(plaintext);
        System.out.println(criptext);
        System.out.println(e);
        System.out.println(n);
        return criptext.modPow(e, n);
    }

    public BigInteger decript(BigInteger cripttext) {
        runtime = System.nanoTime();
        BigInteger d = new BigInteger(generatePrivateKey());
        BigInteger plaintext = cripttext.modPow(d, n);
        runtime = System.nanoTime() - runtime;
        System.out.println("Algorithm ran for ");
        System.out.println((float) this.runtime / Math.pow(10, 9));
        return plaintext;
    }

    public BigInteger decriptTCR(BigInteger criptext) {
        runtime = System.nanoTime();
        BigInteger d = new BigInteger(generatePrivateKey());
        BigInteger tcr = TCR(criptext, d, n);
        runtime = System.nanoTime() - runtime;
        System.out.println("Algorithm ran for ");
        System.out.println((float) this.runtime / Math.pow(10, 9));
        return tcr;
    }

    public BigInteger TCR(BigInteger a, BigInteger n, BigInteger m) { // a^n mod m
        // m = m1 * m2 => m1 = p ; m2 =q
        BigInteger m1 = p;
        BigInteger m2 = q;
        BigInteger n1 = n.mod(m1.subtract(BigInteger.valueOf(1)));
        BigInteger n2 = n.mod(m2.subtract(BigInteger.valueOf(1)));
        BigInteger m1ModInverse = m1.modInverse(m2);

        BigInteger x1 = (a.mod(m1)).modPow(n1, m1);
        BigInteger x2 = (a.mod(m2)).modPow(n2, m2);
        return x1.add(
                m1.multiply(
                        ((x2.subtract(x1))
                                .multiply(m1ModInverse))
                                .mod(m2)
                )
        );
    }

    public BigInteger parseCharsToInt(String string) {
        StringBuilder sb = new StringBuilder();
        char[] chars;
        chars = string.toCharArray();
        for (char c : chars) {
            sb.append((int) c);
        }
        return new BigInteger(sb.toString());
    }

    /*public String parseIntToChars(String string){

    }*/

    public BigInteger getN() {
        return n;
    }

    public void setN(BigInteger n) {
        this.n = n;
    }

    public BigInteger getP() {
        return p;
    }

    public void setP(BigInteger p) {
        this.p = p;
    }

    public BigInteger getQ() {
        return q;
    }

    public void setQ(BigInteger q) {
        this.q = q;
    }

    public BigInteger getE() {
        return e;
    }

    public void setE(BigInteger e) {
        this.e = e;
    }
}
