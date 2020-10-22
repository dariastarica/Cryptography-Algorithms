import java.util.ArrayList;
import java.util.List;

public class Main {
  public static void main(String[] args) {
    String plaintext = "0123456789ABCDEF";
    String cripttext = "C2578E3977A8DF48";
    String key = "D4D4D4D4D4D4D4D4D4";
    String key2 = "1010101010101010";
    String plaintext2="11F3424ABC58DECD";

    DES des = new DES();

    System.out.println("Encryption of " + plaintext+ " " +des.encrypt(plaintext, key));
    System.out.println("Decryption of " + cripttext + " " +des.decrypt(cripttext, key));

    List<String> doubleEncList = new ArrayList<>();
    List<String> plaintextList= new ArrayList<>();
    doubleEncList.add(des.encrypt(des.encrypt(plaintext,key), key2));
    doubleEncList.add(des.encrypt(des.encrypt(plaintext2,key), key2));
    plaintextList.add(plaintext);
    plaintextList.add(plaintext2);

    MeetInTheMiddle attack = new MeetInTheMiddle(des);
    System.out.println(attack.findKey(plaintextList, doubleEncList));
  }
}
