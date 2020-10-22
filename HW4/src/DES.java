
public class DES {

  // <editor-fold desc="Date primite">
  public int[] IP = {
    58, 50, 42, 34, 26, 18, 10, 2, 60, 52, 44, 36, 28, 20, 12, 4, 62, 54, 46, 38, 30, 22, 14, 6, 64,
    56, 48, 40, 32, 24, 16, 8, 57, 49, 41, 33, 25, 17, 9, 1, 59, 51, 43, 35, 27, 19, 11, 3, 61, 53,
    45, 37, 29, 21, 13, 5, 63, 55, 47, 39, 31, 23, 15, 7
  };

  int[] IP1 = {
    40, 8, 48, 16, 56, 24, 64, 32, 39, 7, 47, 15, 55, 23, 63, 31, 38, 6, 46, 14, 54, 22, 62, 30, 37,
    5, 45, 13, 53, 21, 61, 29, 36, 4, 44, 12, 52, 20, 60, 28, 35, 3, 43, 11, 51, 19, 59, 27, 34, 2,
    42, 10, 50, 18, 58, 26, 33, 1, 41, 9, 49, 17, 57, 25
  };
  int[] EP = {
    32, 1, 2, 3, 4, 5, 4, 5, 6, 7, 8, 9, 8, 9, 10, 11, 12, 13, 12, 13, 14, 15, 16, 17, 16, 17, 18,
    19, 20, 21, 20, 21, 22, 23, 24, 25, 24, 25, 26, 27, 28, 29, 28, 29, 30, 31, 32, 1
  };
  int[][][] sbox = {
    {
      {14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7},
      {0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8},
      {4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0},
      {15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13}
    },
    {
      {15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10},
      {3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5},
      {0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15},
      {13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9}
    },
    {
      {10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8},
      {13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1},
      {13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7},
      {1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12}
    },
    {
      {7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15},
      {13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9},
      {10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4},
      {3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14}
    },
    {
      {2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9},
      {14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6},
      {4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14},
      {11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3}
    },
    {
      {12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11},
      {10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8},
      {9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6},
      {4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13}
    },
    {
      {4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1},
      {13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6},
      {1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2},
      {6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12}
    },
    {
      {13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7},
      {1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2},
      {7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8},
      {2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11}
    }
  };
  int[] P = {
    16, 7, 20, 21, 29, 12, 28, 17, 1, 15, 23, 26, 5, 18, 31, 10, 2, 8, 24, 14, 32, 27, 3, 9, 19, 13,
    30, 6, 22, 11, 4, 25
  };
  int[] PC1 = {
    57, 49, 41, 33, 25, 17, 9, 1, 58, 50, 42, 34, 26, 18, 10, 2, 59, 51, 43, 35, 27, 19, 11, 3, 60,
    52, 44, 36, 63, 55, 47, 39, 31, 23, 15, 7, 62, 54, 46, 38, 30, 22, 14, 6, 61, 53, 45, 37, 29,
    21, 13, 5, 28, 20, 12, 4
  };
  int[] PC2 = {
    14, 17, 11, 24, 1, 5, 3, 28, 15, 6, 21, 10, 23, 19, 12, 4, 26, 8, 16, 7, 27, 20, 13, 2, 41, 52,
    31, 37, 47, 55, 30, 40, 51, 45, 33, 48, 44, 49, 39, 56, 34, 53, 46, 42, 50, 36, 29, 32
  };
  // </editor-fold>

  public String encrypt(String plaintext, String key) {
    plaintext = convertHexToBin(plaintext);
    key = convertHexToBin(key);
    // System.out.println("key:" + key);
    // key = deleteParityCheckBits(key);
    String[] generatedKeys = keyScheduling(key);
    plaintext = permutation(IP, plaintext);
    // System.out.println("Dupa IP: " + plaintext);

    for (int i = 0; i < 16; i++) {
      plaintext = T(plaintext, generatedKeys[i]);
    }
    // System.out.println(plaintext);
    plaintext = rev(plaintext);
    // System.out.println(plaintext);

    plaintext = permutation(IP1, plaintext);
    plaintext = convertBinToHex(plaintext);
    return plaintext;
  }

  public String decrypt(String cripttext, String key) {
    cripttext = convertHexToBin(cripttext);
    key = convertHexToBin(key);
    // System.out.println("key:" + key);
    // key = deleteParityCheckBits(key);
    String[] generatedKeys = keyScheduling(key);
    cripttext = permutation(IP, cripttext);
    // System.out.println("Dupa IP: " + cripttext);

    for (int i = 15; i >= 0; i--) {
      cripttext = T(cripttext, generatedKeys[i]);
    }
    // System.out.println(cripttext);
    cripttext = rev(cripttext);
    // System.out.println(cripttext);

    cripttext = permutation(IP1, cripttext);
    cripttext = convertBinToHex(cripttext);
    return cripttext;
  }

  static int i = 0;

  public String T(String stream, String key) {

    String u = stream.substring(0, 32);
    String v = stream.substring(32, 64);

    StringBuilder result = new StringBuilder();

    result.append(v);
    result.append(xor(u, F(v, key)));
    // System.out.println("L: "+ (++i)+" " + v + "\nR: " + i+" "+ xor(u, F(v, key)));
    return result.toString();
  }

  public String xor(String stream1, String stream2) {
    StringBuilder result = new StringBuilder();
    for (int i = 0; i < stream1.length(); i++)
      result.append(
          (char) ((stream1.charAt(i) - '0') ^ (stream2.charAt(i % stream2.length()) - '0') + '0'));
    return result.toString();
  }

  public String rev(String stream) {
    String u = stream.substring(0, 32);
    String v = stream.substring(32, 64);
    String result = "";
    result = result.concat(v);
    result = result.concat(u);
    return result;
  }

  // <editor-fold desc="Bin->Hex Hex->Bin">
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

  public String deleteParityCheckBits(String input) {

    StringBuilder sb = new StringBuilder();

    for (int i = 0; i < 64; i++) {
      if (i % 8 != 7) sb.append(input.charAt(i));
    }

    return sb.toString();
  }

  public String convertHexToBin(String hexString) {
    StringBuilder sb = new StringBuilder();

    char[] convertedInput = hexString.toCharArray();

    char[] result = new char[64];

    int length = 0;

    for (char c : convertedInput) {
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
  // </editor-fold>

  public String permutation(int[] perm, String input) {
    StringBuilder output = new StringBuilder();
    for (int value : perm) output.append(input.charAt(value - 1));
    return output.toString();
  }

  // <editor-fold desc="Functia F">
  public String F(String stream, String key) { // stream-32 bits + key-48 bits => 32 bits

    String xorResult;
    StringBuilder sBoxResult = new StringBuilder(32);
    String output;
    // extindere stream pana la 48 bits
    stream = permutation(EP, stream);
    // xor intre key si stream extins
    xorResult = xor(stream, key);
    // S-boxex 48 bits -> 32 biti
    for (int i = 0; i < 48; i += 6) {
      sBoxResult.append(sBox(xorResult.substring(i, i + 6), i / 6));
    }
    // permutare cu P
    output = permutation(P, sBoxResult.toString());
    // System.out.println(output);
    return output;
  }

  public String sBox(String stream, int iteratie) {
    StringBuilder rowBuilder = new StringBuilder();
    rowBuilder.append(stream.charAt(0));
    rowBuilder.append(stream.charAt(5));
    int row = Integer.parseInt(rowBuilder.toString(), 2);
    int col = Integer.parseInt(stream.substring(1, 5), 2);
    return convertHexToBin(Integer.toHexString(sbox[iteratie][row][col]).toUpperCase());
  }

  // </editor-fold>

  public String[] keyScheduling(String key) { // key- 56 + 8 bits
    key = permutation(PC1, key);
    String[] keys = new String[16];
    String keyBuilder;
    for (int i = 0; i < 16; i++) {
      keyBuilder = "";
      keyBuilder = keyBuilder.concat(cyclicShift(key.substring(0, 28), i));
      keyBuilder = keyBuilder.concat(cyclicShift(key.substring(28, 56), i));
      // System.out.println("Split: "+ keyBuilder);
      key = keyBuilder;
      keys[i] = permutation(PC2, keyBuilder);
      // System.out.println("\nKEY SCHEDULING "+keys[i]);
    }
    return keys;
  }

  public String cyclicShift(String input, int i) {
    StringBuilder result = new StringBuilder();
    if (i == 0 || i == 1 || i == 8 || i == 15) {
      result.append(input, 1, 28);
      result.append(input.charAt(0));
    } else {
      result.append(input, 2, 28);
      result.append(input.charAt(0));
      result.append(input.charAt(1));
    }
    return result.toString();
  }
}
