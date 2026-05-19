import tester.Tester;

// represent information about an image
class Image {
  int width; // in pixels
  int height; // in pixels
  String source;

  Image(int width, int height, String source) {
    this.width = width;
    this.height = height;
    this.source = source;
  }
  
  /* TEMPLATE:
     Fields:
     ... this.width ...      -- int
     ... this.height ...     -- int
     ... this.source ...     -- String
     
     Methods:
     ... this.sizeString()   -- String
   */
  
  
  // produces string depending on the number of pixels in an image;
  // pixels can be get by multiplying height and width
  String sizeString() {
    int pixels = this.height * this.width;
    if (pixels <= 10000) {
      return "small";
    } else if(pixels <= 100000) {
      return "medium";
    } else {
      return "large";
    }
  }
}

//examples and tests for Image
class ExamplesImage {

Image icon = new Image(50, 50, "icon.png");
Image wallpaper = new Image(1920, 1080, "wallpaper.jpg");
Image profilePic = new Image(200, 200, "profile.png");
Image thumbnail = new Image(100, 80, "thumbnail.jpg");

// test sizeString method
boolean testSizeString(Tester t) {
 return t.checkExpect(this.icon.sizeString(), "small")
     && t.checkExpect(this.wallpaper.sizeString(), "large")
     && t.checkExpect(this.profilePic.sizeString(), "medium")
     && t.checkExpect(this.thumbnail.sizeString(), "small");
}
}