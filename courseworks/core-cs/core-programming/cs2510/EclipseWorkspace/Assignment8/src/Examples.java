import java.util.ArrayList;
import java.util.Arrays; // <-- Fixed the "Arrays cannot be resolved" problem
import java.util.function.Predicate;
import tester.Tester;

// Examples and verification suites for Deques and Permutations
public class Examples {

  Deque<String> deque1; // Empty list
  Deque<String> deque2; // Lexicographically sorted list
  Deque<String> deque3; // Unsorted list

  Permutation predictableCipher; // Updated to Permutation
  ArrayList<Character> customKey;

  // Initializes sample instances to baseline structures
  public void reset() {
    // 1. Empty Deque setup
    deque1 = new Deque<String>();

    // 2. Ordered string Deque setup ("abc", "bcd", "cde", "def")
    deque2 = new Deque<String>();
    Sentinel<String> s2 = deque2.header;
    Node<String> n4 = new Node<String>("def", s2, s2);
    Node<String> n3 = new Node<String>("cde", n4, s2);
    Node<String> n2 = new Node<String>("bcd", n3, s2);
    Node<String> n1 = new Node<String>("abc", n2, s2);

    // 3. Unordered string Deque setup ("xyz", "abc", "mno", "def")
    deque3 = new Deque<String>();
    Sentinel<String> s3 = deque3.header;
    Node<String> u4 = new Node<String>("def", s3, s3);
    Node<String> u3 = new Node<String>("mno", u4, s3);
    Node<String> u2 = new Node<String>("abc", u3, s3);
    Node<String> u1 = new Node<String>("xyz", u2, s3);

    // 4. Permutation Setup
    customKey = new ArrayList<Character>(Arrays.asList('b', 'e', 'a', 'c', 'd', 'f', 'g', 'h', 'i',
        'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'));

    predictableCipher = new Permutation(customKey);
  }

  // Verifies size counting functionality
  public void testSize(Tester t) {
    reset();
    t.checkExpect(deque1.size(), 0);
    t.checkExpect(deque2.size(), 4);
    t.checkExpect(deque3.size(), 4);
  }

  // Verifies safe structural construction exceptions
  public void testConstructorException(Tester t) {
    t.checkConstructorException(new IllegalArgumentException("Given neighbor nodes cannot be null"),
        "Node", "badNode", null, null);
  }

  // Verifies insertion operations at head and tail locations
  public void testAddMethods(Tester t) {
    reset();

    // Test addAtHead on empty list
    deque1.addAtHead("newHead");
    t.checkExpect(deque1.size(), 1);
    t.checkExpect(deque1.header.next.getDataValue(), "newHead");

    reset();

    // Test addAtTail on ordered list
    deque2.addAtTail("endNode");
    t.checkExpect(deque2.size(), 5);
    t.checkExpect(deque2.header.prev.getDataValue(), "endNode");
  }

  // Verifies extraction deletion processes from both terminal ends
  public void testRemoveMethods(Tester t) {
    reset();

    // Exception checking on empty extraction
    t.checkException("Removing head from empty list throws exception",
        new RuntimeException("Cannot remove from an empty Deque"), this.deque1, "removeFromHead");

    // Verify successful terminal removal operations
    t.checkExpect(deque2.removeFromHead(), "abc");
    t.checkExpect(deque2.size(), 3);
    t.checkExpect(deque2.header.next.getDataValue(), "bcd");

    t.checkExpect(deque2.removeFromTail(), "def");
    t.checkExpect(deque2.size(), 2);
  }

  // Verifies item linear searches and internal selective removal mutations
  public void testFindAndRemoveNode(Tester t) {
    reset();

    Predicate<String> matchesCde = s -> s.equals("cde");
    Predicate<String> missingMatch = s -> s.equals("missing");

    ANode<String> foundNode = deque2.find(matchesCde);
    t.checkExpect(foundNode.getDataValue(), "cde");

    ANode<String> fallbackNode = deque2.find(missingMatch);
    t.checkExpect(fallbackNode, deque2.header);

    deque2.removeNode(foundNode);
    t.checkExpect(deque2.size(), 3);
    t.checkExpect(deque2.find(matchesCde), deque2.header);
  }

  // Verifies the structural mapping substitution cycle rules
  public void testEncodingAndDecoding(Tester t) {
    reset();

    // Testing Example 1: plaintext "badace" -> ciphertext "ebcbad"
    t.checkExpect(predictableCipher.encode("badace"), "ebcbad");
    t.checkExpect(predictableCipher.decode("ebcbad"), "badace");

    // Testing Example 2: ciphertext "abeedc" -> plaintext "cabbed"
    t.checkExpect(predictableCipher.decode("abeedc"), "cabbed");
    t.checkExpect(predictableCipher.encode("cabbed"), "abeedc");
  }

  // Verifies random initialization correctness
  public void testRandomEncoder(Tester t) {
    Permutation randomCipher1 = new Permutation();
    Permutation randomCipher2 = new Permutation();

    t.checkExpect(randomCipher1.code.size(), 26);

    for (char c : randomCipher1.alphabet) {
      t.checkExpect(randomCipher1.code.contains(c), true);
    }

    t.checkExpect(randomCipher1.code.equals(randomCipher2.code), false);
  }
}