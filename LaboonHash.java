import java.util.*;
import java.lang.Math; 

public class LaboonHash{
    
    private static final String initVector = "1AB0";
    
    public static void main(String[] args){
        boolean verbose = false;
        if(args.length<1||args.length>2){
            usage();
        }
        if(args.length==2&&!args[1].equals("-verbose")){
            usage();
        }
        else if(args.length==2&&args[1].equals("-verbose")){
            verbose = true;
        }
        
        String hash = c(args[0], verbose);
        
        System.out.println("LaboonHash hash = "+hash);
    }
    
    /**
     * Given some String input, strengthen and hash it. Can also show work being done.
     * Example: A -> A0000001 -> 1310
     * @param String input made of printable ASCII characters, boolean determining whether work should be printed out
     * @return String that is 4 character hexadecimal hash of input
     */

    public static String c(String input, boolean verbose){
        char[] lhs = initVector.toCharArray();
        String paddedString = padding(input);
        int count = 0;
        ArrayList<String> blocks = new ArrayList<String>();
        ArrayList<String> hashes = new ArrayList<String>();
        while(count!=paddedString.length()){
            char[] rhs = paddedString.substring(count, count+8).toCharArray();
            blocks.add(new String(rhs));
            hashes.add(new String(lhs));
            int[] result = new int[4];
            result = partOne(lhs, rhs);
            result = partTwo(result, rhs);
            result = partThree(result);
            char[] resultChar = new char[4];
            for(int i=0;i<4;i++){
                resultChar[i] = Integer.toHexString(result[i]%16).toUpperCase().toCharArray()[0];
            }
            lhs = resultChar;
            count+=8;
        }
        hashes.add(new String(lhs));
        if(verbose){
            System.out.println("\tPadded string: "+paddedString);
            System.out.println("\tBlocks:");
            for(int i = 0; i<blocks.size();i++){
                System.out.println("\t"+blocks.get(i));
            }
            for(int i = 0; i<blocks.size();i++){
                System.out.println("\tIterating with "+ hashes.get(i) +" / "+ blocks.get(i) +" = "+ hashes.get(i+1));
            }
            System.out.println("\tFinal result: "+hashes.get(hashes.size()-1));
        }
        return hashes.get(hashes.size()-1);
    }
    
    private static void usage(){
        System.out.println("Usage:");
        System.out.println("java LaboonHash *string* *verbosity_flag*");
        System.out.println("Verbosity flag can be omitted for hash output only");
        System.out.println("Other options: -verbose");
        System.exit(0);
    }
    
    private static String padding(String original){
        if(original.length()%8==0){
            return original;
        }
        int spaceRemaining = 8-(original.length()%8);
        int strength = original.length()%(int)(Math.pow(16,spaceRemaining));
        String hex = Integer.toHexString(strength).toUpperCase();
        String padded = original;
        for(int i=0;i<spaceRemaining-hex.length();i++){
            padded+="0";
        }
        padded+=hex;
        return padded;
    }
    
    private static int[] partOne(char[] lhs, char[] rhs){
        int[] result = new int[4];
        for(int i = 0; i<4; i++){
            int val = lhs[i] + rhs[3-i];
            result[i] = val;
        }
        return result;
    }
    
    private static int[] partTwo(int[] lhsResult, char[] rhs){
        int[] result = new int[4];
        for(int i = 0; i<4; i++){
            int val  = lhsResult[i]^rhs[7-i];
            result[i] = val;
        }
        return result;
    }
    
    private static int[] partThree(int[] result){
        for(int i = 0; i<4; i++){
            int val  = result[i]^result[3-i];
            result[i] = val;
        }
        return result;
    }
}