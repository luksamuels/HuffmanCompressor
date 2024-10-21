
import java.io.*;
import java.util.*;

public class HuffmanCompressor {

   public static final int NUM_CHARS = 256;
   
   public static void encode(String filename) throws FileNotFoundException, IllegalArgumentException {
      // 0. Check that file exists and is a txt; create refrences to files
      if(!filename.endsWith(".txt")) {
         throw new IllegalArgumentException("This program only works on .txt files!");
      }
      File f = new File(filename);
      if(!f.exists()) {
         throw new FileNotFoundException("The passed file doesn't exist. Try another file!");
      }
      FileInputStream userFile = new FileInputStream(f);
      //    clear any preexisting .tree and .comp files from the folder
      File tree = null;
      File comp = null;
      try {
         tree = new File(filename.split(".txt")[0] + ".tree");
         comp = new File(filename.split(".txt")[0] + ".comp");
         FileWriter treeClear = new FileWriter(tree);
         FileWriter compClear = new FileWriter(comp);
         treeClear.write("");
         compClear.write("");
         treeClear.close();
         compClear.close();
      } catch (IOException e) {
         System.out.println(e.toString());
         System.exit(1);
      }
      
      // 1. Read through the file, counting the freq of each character
      
      System.out.println("First, I'll read through the file you passed and " +
         "count how many of each character there are...");
      
      int[] frequencies = new int[NUM_CHARS];
      Arrays.fill(frequencies, 0);
      try {
         int readChar = userFile.read();
         while(readChar != -1) {
            frequencies[readChar]++;
            readChar = userFile.read();
         }  
      } catch (Exception e) {
         System.out.println(e.toString());
         System.exit(1);
      }
      System.out.println("\t...done");
      
      // 2. Build the tree;
      System.out.println("Now I'm going to use those counted characters to " +
         "build a frequency tree...");
      PriorityQueue<HuffNode> pq = new PriorityQueue<>();
      
      for(int i = 0; i < NUM_CHARS; i++) {
         if(frequencies[i] > 0) {
            pq.add(new HuffNode(i, frequencies[i]));
         }
      }
      
      while(pq.size() > 1) {
         HuffNode l = pq.poll();
         HuffNode r = pq.poll();
         pq.add(new HuffNode(l, r));
      }
      HuffNode root = pq.poll();
      
      System.out.println("\t...done");
      
      // 3. Save the tree to a .tree file, also make a dictionary
      System.out.println("Saving the tree...");
      
      Map<Integer, String> paths = new HashMap<>();
      PrintStream treeStream = new PrintStream(tree);
      
      saveTree(root, "", paths, treeStream);
      treeStream.close();
      System.out.println("\t...done");
      
      // 4. Go through the input file and translate each char to the .comp file
      System.out.println("Now I'm reading through the passed file again and " +
         "translating each char into its path...");
      try {
         userFile = new FileInputStream(f);
         OutputBitStream obs = new OutputBitStream(comp);
         int readChar = userFile.read();
         while(readChar != -1) {
            //System.out.print("read: " + readChar + " path: " + paths.get(readChar) + " ");
            obs.print(paths.get(readChar));
            readChar = userFile.read();
         }
         obs.close();
      } catch(Exception e) {
         System.out.println(e.toString());
         System.exit(1);
      }
      
      System.out.println("\t...done");
      // 5. Cleanup: Flush last bit, save output
      System.out.println("Files have been translated!");
      
   }

   private static void saveTree(HuffNode head, String path, Map<Integer, String> pathMap, PrintStream treeStream) {
      if(head.symbol != -1) { // means its a leaf
        treeStream.println(head.symbol);
        treeStream.println(path);
        pathMap.put(head.symbol, path);
      } else {
         saveTree(head.left, path + "0", pathMap, treeStream);
         saveTree(head.right, path + "1", pathMap, treeStream);
      }
   }
}