import java.io.*;

public class InputBitStream {

   private final int BASE_INDEX = 7;

   private FileInputStream fs;
   private int currByte;
   private int nextByte;
   private int index;
   private int extraBits;
   
   public InputBitStream(File f) {
      try {
         fs = new FileInputStream(f);
         extraBits = fs.read(); // # of extra bits is the first char in the file
         currByte = fs.read();
         nextByte = fs.read();
         index = BASE_INDEX;
      } catch (Exception e) {
         System.out.println(e.toString());
         System.exit(1);
      }
   }
   
   public int nextBit() {
      if(hasNextBit()) {
         int res = (currByte >> index) & 0x01;
         index--;
         if(index == -1) {
            loadNextByte();
         }
         return res;
      }
      return -1;
   }
   
   private void loadNextByte() {
      currByte = nextByte;
      try {
         nextByte = fs.read();
      } catch (Exception e) {
         System.out.println(e.toString());
         System.exit(1);
      }
      index = BASE_INDEX;
   }
   
   public boolean hasNextBit() {
      // the input has the next bit if nextByte != -1 or index > extraBits
      return (nextByte != -1) || (index > extraBits);
   }
   
   public void close() {
      try {
         fs.close();
      } catch(Exception e) {
         System.out.println(e.toString());
         System.exit(1);
      }
   }
   
   
}