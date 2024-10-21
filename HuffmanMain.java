import java.util.*;
import java.io.*;

public class HuffmanMain {
   public static void main(String[] args) {
      Scanner userInput = new Scanner(System.in);
      System.out.println("Welcome to Luke's .txt file compressor.");
      
      while(true) {          
         System.out.print("\nWould you like to (c)ompress or (d)ecompress a file? ");
         int response = getAorB(userInput, "c", "d");
         
         // compression
         if(response == 1) {
            System.out.print("Please enter the path to the file you'd like to compress: ");
            String filepath = userInput.next();
            try {
               HuffmanCompressor.encode(filepath);
            } catch (Exception e) {
               System.out.println(e.toString());
               System.exit(1);
            }
         } else {
         // decompression
            System.out.print("Please enter the path to the .comp file you'd like to decompress: ");
            String filepath = userInput.next();
            filepath = filepath.split(".comp")[0];
            try {
               HuffmanDecompressor.decode(filepath);
            } catch (Exception e) {
               System.out.println(e.toString());
               System.exit(1);
            }
         }
         System.out.print("Process more files? ");
         if(getAorB(userInput, "y", "n") == 0) {
            break;
         }
      }
   }
   
   public static int getAorB(Scanner s, String a, String b) {
      System.out.print("(" + a + "/" + b + "/quit)? ");
      String response = s.next();
      if(response.equals("quit") || response.equals("q")) {
         System.exit(0);
      } else if(response.equals(a) || response.equals(Character.toString(a.charAt(0)))) {
        return 1;
      } else if(response.equals(b) || response.equals(Character.toString(b.charAt(0)))) {
        return 0;
      }
      System.out.print("Please enter only \"" + a + "\", \"" + b + "\", or \"quit\". ");
      return getAorB(s, a, b);
   }
}  