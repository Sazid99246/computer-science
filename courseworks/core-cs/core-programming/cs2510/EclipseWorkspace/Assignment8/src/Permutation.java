import java.util.*;

/**
 * A class that defines a new permutation code, as well as methods for encoding
 * and decoding of the messages that use this code.
 */
class Permutation {
  ArrayList<Character> alphabet = new ArrayList<Character>(
      Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
          'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'));

  ArrayList<Character> code = new ArrayList<Character>(26);
  Random rand = new Random();

  Permutation() {
    this.code = this.initEncoder();
  }

  Permutation(ArrayList<Character> code) {
    this.code = code;
  }

  ArrayList<Character> initEncoder() {
    ArrayList<Character> remainingPool = new ArrayList<Character>(this.alphabet);
    ArrayList<Character> randomizedKey = new ArrayList<Character>(26);

    while (!remainingPool.isEmpty()) {
      int randomIndex = this.rand.nextInt(remainingPool.size());
      randomizedKey.add(remainingPool.remove(randomIndex));
    }
    return randomizedKey;
  }

  // Abstracted Helper: Converts a string by looking up characters from a source
  // list
  // and swapping them with characters from a destination list.
  private String transform(String message, ArrayList<Character> fromList,
      ArrayList<Character> toList) {
    StringBuilder result = new StringBuilder();
    for (int i = 0; i < message.length(); i++) {
      char targetChar = message.charAt(i);
      int targetIndex = fromList.indexOf(targetChar);
      result.append(toList.get(targetIndex));
    }
    return result.toString();
  }

  // Encodes: Maps positions from Alphabet -> Code
  String encode(String source) {
    return this.transform(source, this.alphabet, this.code);
  }

  // Decodes: Maps positions from Code -> Alphabet
  String decode(String codeMessage) {
    return this.transform(codeMessage, this.code, this.alphabet);
  }
}