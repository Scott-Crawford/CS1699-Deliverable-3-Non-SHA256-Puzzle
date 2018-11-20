import java.util.*;

public class LaboonCrypt{    
    public static void main(String[] args){
        boolean verbose = false;
        boolean veryverbose = false;
        boolean ultraverbose = false;
        if(args.length<1||args.length>2){
            usage();
        }
        if(args.length==2&&args[1].equals("-verbose")){
            verbose = true;
        }
        else if(args.length==2&&args[1].equals("-veryverbose")){
            verbose = true;
            veryverbose = true;
        }
        else if(args.length==2&&args[1].equals("-ultraverbose")){
            verbose = true;
            veryverbose = true;
            ultraverbose = true;
        }
        else if(args.length==2){
            usage();
        }
        String input = args[0];
        String[][] hashArray = new String[12][12];
        for(int i = 0; i<hashArray.length; i++){
            for(int j = 0; j<hashArray[i].length; j++){
                String hash = LaboonHash.c(input, ultraverbose);
                hashArray[i][j] = hash;
                input = hash;
            }
        }
        if(verbose){
            System.out.println("Initial array:");
            for(int i = 0; i<hashArray.length; i++){
                for(int j = 0; j<hashArray[i].length; j++){
                    System.out.print(hashArray[i][j]+" ");
                }
                System.out.println();
            }
        }
        char[] inputChars = args[0].toCharArray();
        int prevX = 0;
        int prevY = 0;
        for(int i = 0; i<inputChars.length;i++){
            int down = inputChars[i]*11;
            int x = (down+prevX)%12;
            int right = (inputChars[i]+ 3) * 7;
            int y = (right+prevY)%12;
            String prevHash = hashArray[x][y];
            hashArray[x][y] = LaboonHash.c(hashArray[x][y], ultraverbose);
            if(veryverbose){
                System.out.println("Moving "+down+" down and "+right+" right - modifying ["+x+", "+y+"] from "+prevHash+" to "+hashArray[x][y]);
            }
            prevX = x;
            prevY = y;
        }
        
        if(verbose){
            System.out.println("Final array:");
            for(int i = 0; i<hashArray.length; i++){
                for(int j = 0; j<hashArray[i].length; j++){
                    System.out.print(hashArray[i][j]+" ");
                }
                System.out.println();
            }
        }
        String concat = "";
        for(int i = 0; i<hashArray.length; i++){
            for(int j = 0; j<hashArray[i].length; j++){
                concat+= hashArray[i][j];
            }
        }
        String hash = LaboonHash.c(concat,ultraverbose);
        System.out.println("LaboonCrypt hash: "+hash);
        
    }
    
    private static void usage(){
        System.out.println("Usage:");
        System.out.println("java LaboonHash *string* *verbosity_flag*");
        System.out.println("Verbosity flag can be omitted for hash output only");
        System.out.println("Other options: -verbose -veryverbose -ultraverbose");
        System.exit(0);
    }
}