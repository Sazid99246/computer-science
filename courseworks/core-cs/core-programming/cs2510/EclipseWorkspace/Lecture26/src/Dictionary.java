import java.util.HashMap;
import tester.Tester;

// 26.1.1 Dictionary Entry Representation
class DictEntry {
  String word;
  String meaning;

  DictEntry(String word, String meaning) {
    this.word = word;
    this.meaning = meaning;
  }
}

// 26.1.2 Wiki Entry Representation
class WikiEntry {
  String url;
  String contents;

  WikiEntry(String url, String contents) {
    this.url = url;
    this.contents = contents;
  }
}

// 26.3 Example usage suite of standard Java HashMaps
class ExampleHashMaps {
  void testHashMaps(Tester t) {
    HashMap<String, String> rooms = new HashMap<String, String>();

    // Put baseline structural mapping data into the hashtable
    rooms.put("Ben Lerner", "WVH314");
    rooms.put("Leena Razzaq", "WVH310B");
    rooms.put("Olin Shivers", "WVH308");
    rooms.put("Matthias Felleisen", "WVH308B");

    // Retrieve value mappings by string key
    t.checkExpect(rooms.get("Ben Lerner"), "WVH314");

    // Check key presence containment flags
    t.checkExpect(rooms.containsKey("Leena Razzaq"), true);
    t.checkExpect(rooms.containsKey("Amal Ahmed"), false);

    // Keys not present in the map return null
    t.checkExpect(rooms.get("Amal Ahmed"), null);
  }
}