import java.io.File;
import java.io.PrintStream;
import java.util.List;
import java.util.ArrayList;

public class OutputBitStream {
   
   private final int BASE_INDEX = 7; // max index of a char's bits
   private int currByte;
   private PrintStream ps;
   private int index;
   private List<Integer> buffer;
   
   public OutputBitStream(File f){
      this.ps = null;
      try {
         this.ps = new PrintStream(f);
      } catch (Exception e) {
         System.out.println(e.toString());
         System.exit(1);
      }
      this.currByte = 0;
      this.index = BASE_INDEX;
      this.buffer = new ArrayList<>();
   }
   
   public void print(String bits) {
      for(int i = 0; i < bits.length(); i++) {
         if(bits.charAt(i) == '1') {
            currByte |= 1 << index;
         } 
         index--;
         if(index == -1) {
            flush();
         }
      }
   }
   
   // prints the current output and resets the buffer
   public void flush() {
      if(index != 7) {
         buffer.add(currByte);
         index = BASE_INDEX;
         currByte = 0x00;
      }
   }
   
   public void close() {
      int bits_remaining = index;
      if(bits_remaining == BASE_INDEX) {
         bits_remaining = 0;
      }
      flush();
      ps.write(bits_remaining);
      for(int b : buffer) {
         ps.write(b);
      }
      ps.close();
   }
}