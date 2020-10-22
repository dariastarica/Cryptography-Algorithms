import java.math.BigInteger;
import java.util.Random;

public class Wiener {
    private RSA rsa;
    private int l,d;

    public Wiener() {
        rsa= new RSA();
    }

    public BigInteger decript(BigInteger criptext){
        int i=0;
        do{

        }while(criteriul(l,d)==1);
        return criptext;
    }

    public int[] continuedFractionExpansion(int n, int d){
        int[] e = new int[1000];
        int count =0;
        int q=n/d;
        int r=n%q;
        e[count++]=q;

        while(r!=0){
            n=d; d=r;
            q=n/d;
            r=n%d;
            e[count++]=q;
        }
        return e;
    }

    public int criteriul(int l, int d){
        return 1;
    }
}
