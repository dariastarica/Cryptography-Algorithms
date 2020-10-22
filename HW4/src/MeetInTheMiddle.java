import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.util.ArrayList;
import java.util.List;

public class MeetInTheMiddle {

    public List<String> generatedKeys = new ArrayList<>();
    public List<String> encriptStrings = new ArrayList<>();
    public List<String> possibleKeys = new ArrayList<>();
    public DES des;

    public MeetInTheMiddle(DES des) {
        this.des = des;
    }


    public String findKey(List<String> plaintextList, List<String> cripttextList) {
        int[] set = {0, 1};
        int k = 8;
        String dec;
        List<String> keys = new ArrayList<>();
        generateKeys(set, k);
        String plaintext = plaintextList.get(0);
        for (String key : generatedKeys) {
            encriptStrings.add(des.encrypt(plaintext, key));
        }

        for (String key : generatedKeys) {
            dec = des.decrypt(cripttextList.get(0), key);
            if (encriptStrings.contains(dec)) {
                possibleKeys.add(key);
            }
        }
        System.out.println(possibleKeys);

        plaintext=plaintextList.get(1);
        for (String key : generatedKeys) {
            encriptStrings.add(des.encrypt(plaintext, key));
        }
        for (String key : generatedKeys) {
            dec = des.decrypt(cripttextList.get(1), key);
            if (encriptStrings.contains(dec) && possibleKeys.contains(key)) {
                keys.add(key);
            }
        }
        return keys.toString();
    }

    public void generateKeys(int[] set, int k) {
        int n = set.length;
        generateKeysRec(set, "", n, k);
    }

    public void generateKeysRec(int[] set, String prefix, int n, int k) {
        String output = "";
        if (k == 0) {
            for (int i = 0; i < 8; i++) output = output.concat(prefix);
            generatedKeys.add(output);
            return;
        }
        for (int i = 0; i < n; ++i) {
            String newPrefix = prefix + set[i];
            generateKeysRec(set, newPrefix, n, k - 1);
        }
    }
}
