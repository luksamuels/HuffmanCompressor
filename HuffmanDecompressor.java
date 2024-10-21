import java.io.*;
import java.util.*;

public class HuffmanDecompressor {
   
   
   // main method: decode(filename)
   
   public static void decode(String filename) throws FileNotFoundException, IOException {
      // 1. make sure that filename.comp & filename.tree exist
      File comp = new File(filename + ".comp");
      File tree = new File(filename + ".tree");
      File output = new File(filename + ".decomp");
      FileWriter outputClear = new FileWriter(output);
      outputClear.write("");
      outputClear.close();
      
      if(!(comp.exists() && tree.exists())) {
         throw new FileNotFoundException("Ensure both .comp and .tree files exist in the current directory for the given file name.");
      }
      
      // 2. Read through filename.tree and build the tree (which is really just a dictionary)
      Map<String, Character> paths = new HashMap<String, Character>();
      Scanner s = new Scanner(tree);
      while(s.hasNextLine()) {
         Character symbol = (char)Integer.parseInt(s.nextLine());
         String path = s.nextLine();
         paths.put(path, symbol);
      }
      
      // 3. Read through filename.comp and translate bit by bit
      PrintStream toOutput = new PrintStream(output);
      InputBitStream compBits = new InputBitStream(comp);
      String currentPath = "";
      while(compBits.hasNextBit()) {
         currentPath += compBits.nextBit();
         if(paths.get(currentPath) != null) {
            toOutput.print(paths.get(currentPath));
            currentPath = "";
         }
      }
      toOutput.close();
      System.out.println("File has been decompressed!");
   }
   
}