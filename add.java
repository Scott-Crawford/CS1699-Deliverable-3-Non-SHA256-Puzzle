import java.util.*;
import java.lang.Math; 


public class add{    
    public static void main(String[] args){
        int val = args[0].toCharArray()[0] ^ args[1].toCharArray()[0];
        String result = Integer.toHexString(val).toUpperCase();
        System.out.println(result);
    }
}