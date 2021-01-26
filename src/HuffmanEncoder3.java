import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


public class HuffmanEncoder3 {
    public  static String data="";
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
         HuffmanEncoder3 tempHe3=new HuffmanEncoder3();
        if(!root.isLeaf()){
            lookupTableHelper(root.leftChild,s+'0',lookupTable);
            lookupTableHelper(root.rightChild,s+'1',lookupTable);
        }
        else{
            lookupTable.put(root.character,s);

            tempHe3.data=tempHe3.data+root.character+"-"+s+"\n";
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

    public void ComP(File file) {
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
             HuffmanEncoder3 tempHe3=new HuffmanEncoder3();
             tempHe3.data=tempHe3.data+"@\n";
             tempHe3.data=tempHe3.data+result.encodedData;
            int a = result.encodedData.length();
//            int b = encoder.decompress(result).length();
            int b =imageData.length*8;
            try{
                File f = new File("/Users/vhlong/University/Kì 1 năm 4/Đa phương tiện/Encoded/output.txt");
                FileWriter fw = new FileWriter(f);
                fw.write(tempHe3.data);
                fw.close();
            }catch(Exception err){
                err.printStackTrace();
            }

//            System.out.println(imageDataString);
//            System.out.println(result.encodedData);
            //System.out.println(encoder.decompress(result));
            System.out.println("Encoded message: " + result.encodedData.length()+ " bits");
            System.out.println("Decoded message: " + b+ " bits");
            System.out.println("Percentage compression: "+ (100.00-(float)a/b*100));
        } catch (FileNotFoundException e) {
            System.out.println("Image not found" + e);
        } catch (IOException ioe){
            System.out.println("Exception while reading the image" + ioe);
        }



    }
//    static class Node2{
//        String character;
//        String code;
//
//        public Node2(String character, String code) {
//            this.character = character;
//            this.code = code;
//        }
//    }
    public void DeComP(){
        String content = "";
        String rawDa="";
        try {
            content = new String(Files.readAllBytes(Paths.get("/Users/vhlong/University/Kì 1 năm 4/Đa phương tiện/Encoded/output.txt")));
        } catch (Exception err) {
            err.printStackTrace();
        }
        //ArrayList<Node2> arrLast=new ArrayList<Node2>();
        HashMap<String,String> hmLast=new HashMap<String,String>();
//        StringTokenizer st=new StringTokenizer(content," -");
//        String tempa="";
//        String tempb="";
//        while(st.hasMoreTokens()){
//            tempa=st.nextToken();
//            //tempb=st.nextToken();
//            hmLast.put(tempa,tempb);
//            if(tempa=="@"){
//                break;
//            }
//        }
        //System.out.println(hmLast);
        String[] str_array = content.split("@");
        String stringa = str_array[0];
        String stringb = str_array[1];
        stringb=stringb.substring(1);
        String[] arra=stringa.split("\n");
        for(int i=0;i<arra.length;i++){
            int iend = arra[i].indexOf("-");
            hmLast.put(arra[i].substring(iend+1),arra[i].substring(0,iend));
        }
        System.out.println(hmLast);
        //System.out.println(stringa);
        String tempoStr="";
        StringBuilder resultBuilder = new StringBuilder();
        for (int i = 0; i < stringb.length(); i++){
            char c = stringb.charAt(i);
            tempoStr+=Character.toString(c);

            //System.out.println(tempoStr);

            if(hmLast.containsKey(tempoStr)){
                String temp = Integer.toBinaryString(Integer.parseInt(hmLast.get(tempoStr)));
                while(temp.length() !=8){
                    temp = "0"+temp;
                }
                resultBuilder.append(temp);
//                count++;


                tempoStr="";
            }
        }
        rawDa=resultBuilder.toString();
        try{
            File f = new File("/Users/vhlong/University/Kì 1 năm 4/Đa phương tiện/Encoded/raw.txt");
            FileWriter fw = new FileWriter(f);
            fw.write(rawDa);
            fw.close();
        }catch(Exception err){
            err.printStackTrace();
        }
        System.out.println(rawDa.length());

        System.out.println(tempoStr);

    }
}