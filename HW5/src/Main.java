
public class Main {
    public static void main(String[] args) {
        String messageDigest,messageDigest2;
        String plaintext = "abc";
        String plaintext2="abb";

        SHA1 sha1 = new SHA1();
        Birthday attack= new Birthday();

        messageDigest = sha1.hash(plaintext);
        System.out.println(messageDigest);

        messageDigest2 = sha1.hash(plaintext2);
        System.out.println(messageDigest2);

        //attack.findCollisions();

    }
}
