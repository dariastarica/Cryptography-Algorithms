import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Birthday {
    private SHA1 sha1;
    List<String> messages;
    private boolean found = false;

    public Birthday() {
        this.sha1 = new SHA1();
        this.messages= new ArrayList<>();
    }

    public void findCollisions() {
        int i=0;
        String h1,h2;
        //messages=generateMessages();
        while(true){
            System.out.println(i++);
            h1=sha1.truncatedHash(generateMessages());
            h2=sha1.truncatedHash(generateMessages());
            //System.out.println(h2);
            if(h1.compareTo(h2)==0){
                System.out.println("DA");
                return;
            }
        }
        //System.out.println("NU");
    }

    public String generateMessages() {
        StringBuilder sb = new StringBuilder();

        for(int i = 0, length = (int)(Math.random() * (54 - 27) + 28 ); i < length; i++){
            sb.append((char)(Math.random()*((int)'z' - (int)'a' + 1) + (int)'a'));
        }

        return sb.toString();
        }
    }

