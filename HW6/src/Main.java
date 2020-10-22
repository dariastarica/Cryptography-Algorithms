import java.math.BigInteger;
import java.sql.SQLOutput;

public class Main {
    public static void main(String[] args) {

        RSA rsa =new RSA();
        String plaintext = "HI";
        BigInteger cripttext;
        cripttext=rsa.cript(plaintext);
        System.out.println("chars to int: " + rsa.parseCharsToInt(plaintext));
        System.out.println("Cript: " + cripttext);

        System.out.println("Decript: " + rsa.decript(cripttext));
        System.out.println("Decript using TCR: " + rsa.decriptTCR(cripttext));

       // Wiener attack = new Wiener();
        //System.out.println(attack.decript(cripttext));
    }
}
