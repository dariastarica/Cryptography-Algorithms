import java.math.BigInteger;
import java.util.Date;

public class BBS {
  // BLUM-BLUM-SHUB PSEUDORANDOM BIT GENERATOR
  protected long seed = new Date().getTime();
  protected BigInteger blumInteger;
  private long runtime;


  public BigInteger getBlumInteger() {
    return blumInteger;
  }

  public void setBlumInteger(BigInteger blumInteger) {
    this.blumInteger = blumInteger;
  }

  private BigInteger generateN() {
    BigInteger result, startN = BigInteger.valueOf(5);
    BigInteger p, q;
    p = startN.nextProbablePrime();
    while (!(p.remainder(BigInteger.valueOf(4)).equals(BigInteger.valueOf(3)))) {
      p = p.nextProbablePrime();
    }
    q = p.nextProbablePrime();
    while (!(q.remainder(BigInteger.valueOf(4)).equals(BigInteger.valueOf(3)))) {
      q = q.nextProbablePrime();
    }
    return p.multiply(q);
  }

  public void generatorBBS() {
    runtime = System.nanoTime();
    setBlumInteger(generateN());
    BigInteger x = BigInteger.valueOf(seed * seed).remainder(getBlumInteger());
    System.out.println(getBlumInteger());
    for (long i = 1; i <= 5; i++) {
      x = (x.multiply(x)).remainder(getBlumInteger());
      System.out.println(x.remainder(BigInteger.valueOf(2)));
    }
    runtime = System.nanoTime() - runtime;
    System.out.println("Algorithm ran for ");
    System.out.println((double) this.runtime / Math.pow(10, 9));
  }
}
