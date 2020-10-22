import java.math.BigInteger;

public class Main {
  public static void main(String[] args) {
    // 10/11
    JacobySymbol JS = new JacobySymbol();
    BBS b = new BBS();
    System.out.println("JS pseudorandom bit Generator");
    JS.generatorJacoby();
    System.out.println("BBS pseudorandom bit Generator");
    b.generatorBBS();
  }
}
