public class HuffNode implements Comparable<HuffNode> {
   public int symbol; // must be a value between 0-255, or -1 for null;
   public HuffNode left, right;
   public int frequency;
   
   public HuffNode(int character, int frequency) {
      this.symbol = character;
      this.frequency = frequency;
      this.left = null;
      this.right = null;
   }
   
   public HuffNode(HuffNode left, HuffNode right) {
      this.left = left;
      this.right = right;
      this.symbol = -1;
      this.frequency = left.frequency + right.frequency;
   }
   
   // might produce wrong order?
   public int compareTo(HuffNode other) {
      return Integer.compare(this.frequency, other.frequency);
   }
}     