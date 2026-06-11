import java.util.*;

/**
 * A class that defines a new permutation code, as well as methods for encoding
 * and decoding of the messages that use this code.
 */
class Permutation {
  // The original list of characters to be encoded
  ArrayList<Character> alphabet = new ArrayList<Character>(
      Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
          'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'));

  ArrayList<Character> code = new ArrayList<Character>(26);

  // A random number generator
  Random rand = new Random();

  // Create a new instance of the encoder/decoder with a new permutation code
  Permutation() {
    this.code = this.initEncoder();
  }

  // Create a new instance of the encoder/decoder with the given code
  Permutation(ArrayList<Character> code) {
    this.code = code;
  }

  /**
   * Generates a random permutation of the 26 letters of the alphabet. * EFFECT:
   * Dynamically pulls random items from an alphabet clone pool. Returns: A
   * freshly randomized list of characters representing our code key.
   */
  ArrayList<Character> initEncoder() {
    ArrayList<Character> remainingPool = new ArrayList<Character>(this.alphabet);
    ArrayList<Character> randomizedKey = new ArrayList<Character>(26);

    while (!remainingPool.isEmpty()) {
      int randomIndex = this.rand.nextInt(remainingPool.size());
      randomizedKey.add(remainingPool.remove(randomIndex));
    }

    return randomizedKey;
  }

  /**
   * Encodes a plaintext string using the substitution cipher mapping. Plaintext
   * character index in 'alphabet' -> Ciphertext character at that index in
   * 'code'.
   */
  String encode(String source) {
    StringBuilder result = new StringBuilder();

    for (int i = 0; i < source.length(); i++) {
      char originalChar = source.charAt(i);
      int alphabetIndex = this.alphabet.indexOf(originalChar);

      // Pull matching encrypted substitution letter from our code key map
      char encodedChar = this.code.get(alphabetIndex);
      result.append(encodedChar);
    }

    return result.toString();
  }

  /**
   * Decodes a ciphertext string back into plaintext using the inverse map.
   * Ciphertext character index in 'code' -> Plaintext character at that index in
   * 'alphabet'.
   */
  String decode(String codeMessage) {
    StringBuilder result = new StringBuilder();

    for (int i = 0; i < codeMessage.length(); i++) {
      char encodedChar = codeMessage.charAt(i);
      int codeIndex = this.code.indexOf(encodedChar);

      // Pull corresponding original matching unencrypted alphabet letter
      char originalChar = this.alphabet.get(codeIndex);
      result.append(originalChar);
    }

    return result.toString();
  }
}