import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;


public class HuffmanEncoder3 {
    private static final int ALPHABET_SIZE = 256;
    public HuffmanEncodedResult compress( ArrayList<Integer> data){
         int[] freq = buildFrequencyTable(data);
         Node root = buildHuffmanTree(freq);
         Map<Integer,String> lookupTable = lookupTable(root);

        return new HuffmanEncodedResult(generatedEncodedData(data, lookupTable), root);
    }

    private static String generatedEncodedData(ArrayList<Integer> data, Map<Integer, String> lookupTable) {
         StringBuilder builder = new StringBuilder();
//        for(char character : data.toCharArray()){
//            builder.append(lookupTable.get(character));
//        }
        for(int i=0;i<data.size();i++){
            builder.append(lookupTable.get(data.get(i)));
        }
        return builder.toString();
    }

    private static Map<Integer, String> lookupTable( Node root){
         Map<Integer,String> lookupTable = new HashMap<>();
        lookupTableHelper(root, "", lookupTable);
        return lookupTable;
    }

    private static void lookupTableHelper(Node root, String s, Map<Integer, String> lookupTable) {
        if(!root.isLeaf()){
            lookupTableHelper(root.leftChild,s+'0',lookupTable);
            lookupTableHelper(root.rightChild,s+'1',lookupTable);
        }
        else{
            lookupTable.put(root.character,s);
        }
    }

    private static Node buildHuffmanTree(int[] freq){
         PriorityQueue<Node> priorityQueue = new PriorityQueue<>();
        for(char i=0;i<ALPHABET_SIZE;i++){
            if(freq[i]>0){
                priorityQueue.add(new Node( i,freq[i],null,null));

            }
        }
        if(priorityQueue.size() == 1){
            priorityQueue.add(new Node('\0',1,null,null));
        }
        while(priorityQueue.size()>1){
             Node left = priorityQueue.poll();
             Node right = priorityQueue.poll();
             Node parent = new Node('\0', left.frequency + right.frequency,left,right);
            priorityQueue.add(parent);
        }
        return priorityQueue.poll();
    }
    private static int[] buildFrequencyTable ( ArrayList<Integer> data){
         int[] freq = new int[ALPHABET_SIZE];
        for(int i=0;i<data.size();i++){
            freq[data.get(i)]++;
            //System.out.println(i);
        }
        return freq;
    }
    public static String encodeImage(byte[] imageByteArray){
        return Base64.getEncoder().encodeToString(imageByteArray);
    }
    public String decompress(final HuffmanEncodedResult result){
         StringBuilder resultBuilder = new StringBuilder();
        Node current = result.getRoot();
        int i=0;int count=0;
        while(i<result.getEncodedData().length()){
            while(!current.isLeaf()){
                char bit = result.getEncodedData().charAt(i);
                if(bit == '1'){
                    current = current.rightChild;
                }
                else if(bit == '0'){
                    current = current.leftChild;
                }
                else{
                    throw new IllegalArgumentException("Bit in message is invalid "+ bit);
                }
                i++;
            }

                String temp = Integer.toBinaryString(current.character);
                while(temp.length() !=8){
                    temp = "0"+temp;
                }
                resultBuilder.append(temp);
                count++;
                //System.out.println(temp);



            current = result.getRoot();
        }
        //System.out.println(count);
        return resultBuilder.toString();
    }
    static class Node implements Comparable<Node>{
        private  int character;
        private  int frequency;
        private Node leftChild;
        private Node rightChild;
        private Node( int character,  int frequency,  Node leftChild,  Node rightChild){
            this.character = character;
            this.frequency = frequency;
            this.leftChild = leftChild;
            this.rightChild = rightChild;

        }
        boolean isLeaf(){
            return this.leftChild==null && this.rightChild==null;
        }

        @Override
        public int compareTo(Node that) {
             int frequencyComparison = Integer.compare(this.frequency, that.frequency);
            if(frequencyComparison!=0){
                return frequencyComparison;
            }
            return Integer.compare(this.character, that.character); //just so we get any result
        }
    }
    static class HuffmanEncodedResult{
         Node root;
         String encodedData;
        HuffmanEncodedResult( String encodedData,  Node root){
            this.encodedData = encodedData;
            this.root = root;
        }
        public Node getRoot(){
            return this.root;
        }
        public String getEncodedData(){
            return this.encodedData;
        }
    }

    public void comNdecom(File file) {
        //File file = new File("/Users/vhlong/testImage/test4.jpg");
        try {
            FileInputStream imageInFile = new FileInputStream(file);
            byte imageData[] = new byte[(int) file.length()];
            imageInFile.read(imageData);
            //System.out.println(imageData);

            //String imageDataString = encodeImage(imageData);
            ArrayList<Integer> imageDataString=new ArrayList<>();
            for(int i=0;i<imageData.length;i++){
               // System.out.println(i);
                imageDataString.add(Byte.toUnsignedInt(imageData[i]));
            }
            System.out.println("Raw bytes: " +imageDataString.size()+" bytes");
             HuffmanEncoder3 encoder = new HuffmanEncoder3();
             HuffmanEncodedResult result = encoder.compress(imageDataString);
            int a = result.encodedData.length();
            int b = encoder.decompress(result).length();
//            System.out.println(imageDataString);
//            System.out.println(result.encodedData);
            //System.out.println(encoder.decompress(result));
            System.out.println("Encoded message: " + result.encodedData.length()+ " bits");
            System.out.println("Decoded message: " + encoder.decompress(result).length()+ " bits");
            System.out.println("Percentage compression: "+ (100.00-(float)a/b*100));
        } catch (FileNotFoundException e) {
            System.out.println("Image not found" + e);
        } catch (IOException ioe){
            System.out.println("Exception while reading the image" + ioe);
        }



    }
}