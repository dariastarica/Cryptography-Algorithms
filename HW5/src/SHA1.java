import java.util.Arrays;

public class SHA1 {
    public String h0 = "67452301";
    public String h1 = "EFCDAB89";
    public String h2 = "98BADCFE";
    public String h3 = "10325476";
    public String h4 = "C3D2E1F0";

    public String hash(String input) { //pentru mesaje mici -- de modificat pentru lungime

        h0 = "67452301";
        h1 = "EFCDAB89";
        h2 = "98BADCFE";
        h3 = "10325476";
        h4 = "C3D2E1F0";
        String[] w = new String[80];
        String result = "";
        int length = 0;
        String[] chunks = generateChunks(input);
        for (String chunk : chunks) {
            //System.out.println(chunk);
            // se despart in 16 chunks
            for (int i = 0; i < 512; i += 32)
                w[length++] = chunk.substring(i, i + 32);
            // se mai adauga pana la 80 chunks
            for (int i = 16; i < 80; i++) {
                w[i] = cyclicShift(
                        xor(xor(xor(w[i - 3], w[i - 8]), w[i - 14]), w[i - 16]));
            }


        /*for (int i = 0 ;i < 16; i++){
            System.out.println("w[" + i + "] = " +  convertBinToHex(w[i]) );
        }*/


            String a = convertHexToBin(h0);
            String b = convertHexToBin(h1);
            String c = convertHexToBin(h2);
            String d = convertHexToBin(h3);
            String e = convertHexToBin(h4);
            String f, k;

            for (int i = 0; i < 80; i++) {
                if (i <= 19) {
                    f = or(and(b, c), and((not(b)), d));
                    //System.out.println(i);
                    k = convertHexToBin("5A827999");
                } else if (i <= 39) {
                    f = xor(xor(b, c), d);
                    k = convertHexToBin("6ED9EBA1");
                } else if (i <= 59) {
                    f = or(or(and(b, c), and(b, d)), and(c, d));
                    k = convertHexToBin("8F1BBCDC");
                } else {
                    f = xor(xor(b, c), d);
                    k = convertHexToBin("CA62C1D6");
                }
                String aux = a;
                for (int j = 0; j < 5; j++) {
                    aux = cyclicShift(aux);
                }
                aux =
                        hexAddHex8(
                                hexAddHex8(
                                        hexAddHex8(
                                                hexAddHex8(
                                                        convertBinToHex(aux),
                                                        convertBinToHex(f)  ///SUPER AVENTURA PROGRAMARII
                                                ),
                                                convertBinToHex(e)
                                        ),
                                        convertBinToHex(k)
                                ),
                                convertBinToHex(w[i])
                        );
                e = d;
                d = c;
                c = b;
                for (int j = 0; j < 30; j++) {
                    c = cyclicShift(c);
                    //System.out.println(convertBinToHex(c));
                }
                b = a;
                a = convertHexToBin(aux);

            }
            h0 = hexAddHex8(h0, convertBinToHex(a));
            h1 = hexAddHex8(h1, convertBinToHex(b));
            h2 = hexAddHex8(h2, convertBinToHex(c));
            h3 = hexAddHex8(h3, convertBinToHex(d));
            h4 = hexAddHex8(h4, convertBinToHex(e));


            result = result.concat(h0 + h1 + h2 + h3 + h4);
        }
        return result;
    }

    /*public String pad(String input) {
        StringBuilder sb = new StringBuilder();
        sb.append(hexCharToBin(input));
        sb.append("1");
        //System.out.println("lungime: "+ sb.length());
        while (sb.length() < 448) {
            sb.append("0");
        }
        String aux = hexToBin(Integer.toHexString(input.length()));
        //System.out.println(Integer.toHexString(input.length()));

        for (int i = 0; i < 56; i++) {
            sb.append("0");
        }
        sb.append(aux);
        return sb.toString();
    }*/

    public int getIntHexDigit(char hexDigit) {
        return (hexDigit >= '0' && hexDigit <= '9') ? hexDigit - '0' : (hexDigit - 'A' + 10);
    }

    public String hexAddHex8(String a, String b) {

        int transport = 0;
        char[] result = new char[8];

        for (int i = a.length() - 1; i >= 0; i--) {
            int val = getIntHexDigit(a.charAt(i)) + getIntHexDigit(b.charAt(i)) + transport;
            transport = val / 16;
            result[i] = getHexChar(val % 16);
        }

        return String.valueOf(result);

    }

    public String hexToBin(String input) {

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            String aux = Integer.toBinaryString(
                    ((input.charAt(i) >= '0' && input.charAt(i) <= '9') ? input.charAt(i) - '0' : (input.charAt(i) - 'a' + 10)) * 8
            );
            int l = aux.length();
            while (l < 8) {
                sb.append("0");
                l++;
            }
            sb.append(aux);
        }
        return sb.toString();
    }

    public String hexCharToBin(String input) {

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            String aux = Integer.toBinaryString(
                    input.charAt(i)
            );
            int l = aux.length();
            while (l < 8) {
                sb.append("0");
                l++;
            }
            sb.append(aux);
        }
        return sb.toString();
    }


    public static char getHexChar(int i) {
        if (i >= 0 && i <= 9) {
            return (char) (i + '0');
        }
        return (char) (i + 'A' - 10);
    }

    public static String convertBinToHex(String inputString) {

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < inputString.length() / 4; i++) {
            sb.append(
                    getHexChar(
                            (inputString.charAt(i * 4) - '0') * 8
                                    + (inputString.charAt(i * 4 + 1) - '0') * 4
                                    + (inputString.charAt(i * 4 + 2) - '0') * 2
                                    + (inputString.charAt(i * 4 + 3) - '0')));
        }

        return sb.toString();
    }


    public String convertHexToBin(String hexString) {
        StringBuilder sb = new StringBuilder();

        for (char c : hexString.toCharArray()) {
            char[] binDigit = toBinaryString(convertCharIntoInteger(c));

            sb.append(binDigit);
        }

        return sb.toString();
    }

    public static char[] toBinaryString(int number) {
        char[] code = new char[4];

        for (int i = 0; i < 4; i++) code[i] = '0';

        int l = 0;

        while (number > 0) {
            code[l++] = (char) (number % 2 + '0');
            number /= 2;
        }

        char[] reversedCode = new char[4];

        for (int i = 0; i < 4; i++) reversedCode[i] = '0';

        for (int i = 0; i < 4; i++) {
            reversedCode[4 - i - 1] = code[i];
        }

        return reversedCode;
    }

    public int convertCharIntoInteger(char character) {
        if (character >= '0' && character <= '9') return (int) character - '0';
        return (int) character - 'A' + 10;
    }

    public synchronized String xor(String stream1, String stream2) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < stream1.length(); i++)
            result.append(
                    (char) ((stream1.charAt(i) - '0') ^ (stream2.charAt(i % stream2.length()) - '0') + '0'));

        return result.toString();
    }

    public String and(String stream1, String stream2) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < stream1.length(); i++)
            result.append(
                    (char) (((stream1.charAt(i) - '0') & (stream2.charAt(i % stream2.length()) - '0')) + '0'));
        return result.toString();
    }

    public String or(String stream1, String stream2) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < stream1.length(); i++)
            result.append(
                    (char) (((stream1.charAt(i) - '0') | (stream2.charAt(i % stream2.length()) - '0')) + '0'));
        return result.toString();
    }

    public String not(String stream) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < stream.length(); i++)
            result.append(
                    (char) (~(stream.charAt(i) - '0') + '0'));
        return result.toString();
    }

    public String cyclicShift(String input) {
        StringBuilder result = new StringBuilder();
        result.append(input, 1, input.length());
        result.append(input.charAt(0));
        return result.toString();
    }

    public String truncatedHash(String input) {
        String result = hash(input);
        return result.substring(0, 8);
    }

    public String[] generateChunks(String input) {
        String[] result = new String[1];
        int chunkNr = 0;
        StringBuilder sb = new StringBuilder();

        sb.append(hexCharToBin(input));
        sb.append("1");
        //System.out.println("lungime: "+ sb.length());
        if (sb.length() < 448) {
            while (sb.length() < 448) {
                sb.append("0");
            }
            String length = hexToBin(Integer.toHexString(input.length()));
            //System.out.println(Integer.toHexString(input.length()));

            for (int i = 0; i < 56; i++) {
                sb.append("0");
            }
            sb.append(length);
            result[chunkNr] = sb.toString();
        }
        return result;
    }
}
