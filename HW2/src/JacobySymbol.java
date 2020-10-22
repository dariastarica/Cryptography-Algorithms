
import java.math.BigInteger;
import java.util.Date;


public class JacobySymbol {
  protected BigInteger a;
  protected BigInteger n;
  private long runtime;

  public JacobySymbol() {}

  @Override
  public String toString() {
    return "JacobySymbol{" + "a=" + a + ", n=" + n + '}';
  }

  public long generateN() {
    BigInteger result;
    BigInteger startN = BigInteger.valueOf(5);
    BigInteger p, q;
    p = startN.nextProbablePrime();
    while (!(p.remainder(BigInteger.valueOf(4)).equals(BigInteger.valueOf(3)))) {
      p = p.nextProbablePrime();
    }
    q = p.nextProbablePrime();
    while (!(q.remainder(BigInteger.valueOf(4)).equals(BigInteger.valueOf(3)))) {
      q = q.nextProbablePrime();
    }

    result = p.multiply(q);
    return Long.parseLong(result.toString());
  }

  public long computeJScurs(long a, long n) {
    long b = a % n;
    long c = n;
    int s = 1;
    while (b >= 2) {
      while (b % 4 == 0) b = b / 4;
      if (b % 2 == 0) {
        if (c % 8 == 3 || c % 8 == 5) s = -s;
        b = b / 2;
      }
      if (b == 1) break;
      if (b % 4 == 3 && c % 4 == 3) {
        s = -s;
        long aux = b;
        b = c % b;
        c = aux;
      }
    }
    return s * b;
  }

  public long computeJS(long a, long b) {
    if (b <= 0 || (b % 2) == 0) return 0;
    int j = 1;
    if (a < 0) {
      a = -a;
      if ((b % 4) == 3) j = -j;
    }
    while (a != 0) {
      while ((a % 2) == 0) {
        a = a / 2;
        if ((b % 8) == 3 || (b % 8) == 5) j = -j;
      }
      long aux = a;
      a = b;
      b = aux;
      if ((a % 4) == 3 && (b % 4) == 3) j = -j;
      a = a % b;
    }
    if (b == 1) {
      return j;
    } else return 0;
  }

  public void generatorJacoby() {
    runtime = System.nanoTime();
    BigInteger result = BigInteger.valueOf(0);
    long n = generateN();
    long a = (new Date().getTime()) % n;
    for (long i = 0; i <= 1000; i++) {
      System.out.println((computeJS(a + i, n) + 1) / 2);
    }
    runtime = System.nanoTime() - runtime;
    System.out.println("Algorithm ran for ");
    System.out.println((double) this.runtime / Math.pow(10, 9));
  }

  public long getNanoRuntime() {
    return this.runtime;
  }
}
