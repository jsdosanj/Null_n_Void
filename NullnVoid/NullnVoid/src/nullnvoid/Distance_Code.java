package nullnvoid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class Distance_Code {
   public static void main(String[] args) throws IOException {
       int rWord = (int )(Math.random() * 50);
            //Put sWord in loop to generate the "Other Words" Must make nested loop for correct word to be randomly placed in a table
       int sWord = (int )(Math.random() * 50);
       // write code to ouput here
       String fileWord3 = Files.readAllLines(Paths.get("char3.txt")).get(rWord);
       //String fileWord4 = Files.readAllLines(Paths.get("char4.txt")).get(rWord);
       //String fileWord5 = Files.readAllLines(Paths.get("char5.txt")).get(rWord);
       //String fileWord6 = Files.readAllLines(Paths.get("char6.txt")).get(rWord);
       //String fileWord7 = Files.readAllLines(Paths.get("char7.txt")).get(rWord);
       //String fileWord8 = Files.readAllLines(Paths.get("char8.txt")).get(rWord);
       //String fileWord9 = Files.readAllLines(Paths.get("char9.txt")).get(rWord);
       //String fileWord10 = Files.readAllLines(Paths.get("char10.txt")).get(rWord);
       //String fileWord11 = Files.readAllLines(Paths.get("char11.txt")).get(rWord);
       //String fileWord12 = Files.readAllLines(Paths.get("char12.txt")).get(rWord);
       //String fileWord13 = Files.readAllLines(Paths.get("char13.txt")).get(rWord);
       //String fileWord14 = Files.readAllLines(Paths.get("char14.txt")).get(rWord);
            //Runs 2 Strings through the comparrison
                    //System.out.println(LD(s,t));
            //Test Output
       System.out.println(fileWord3.toUpperCase());
   }
      private static int Minimum(int a, int b, int c) {
       int mi;
       mi = a;
       if (b < mi) {
           mi = b;
       }
       if (c < mi) {
           mi = c;
       }
       return mi;
   }
       // Compute String Comparison
       public static int LD(String s, String t) {
           int d[][]; // matrix
           int n; // length of s
           int m; // length of t
           int i; // Runs through s
           int j; // Runs through t
           char s_i; // ith character of s
           char t_j; // jth character of t
           int cost; // cost
           //Part 1
           n = s.length ();
           m = t.length ();
           if (n == 0) {
               return m;
           }
           if (m == 0) {
               return n;
           }
           d = new int[n + 1][m + 1];
           //Part 2
           for (i = 0; i <= n; i++) {
               d[i][0] = i;
           }

           for (j = 0; j <= m; j++) {
               d[0][j] = j;
           }

           //Part 3
           for (i = 1; i <= n; i++) {
               s_i = s.charAt(i - 1);

               //Part4
               for (j = 1; j <= m; j++) {
                   t_j = t.charAt(j - 1);
                   //Part 5
                   if (s_i == t_j) {
                       cost = 0;
                   }else{
                       cost = 1;
               }
                   //Part 6
                   d[i][j] = Minimum(d[i-1][j]+1, d[i][j-1]+1, d[i-1][j-1] + cost);
               }
           }
           return ((-1)*(d[n][m]))+(n);
       }
       public static void createGameSpace(){
           //Code to draw gamespace and set page with random characters and the Words
}
       public static void gameSpaceRandomizer(){
           //code to create matrix of random words to display and make them clickable
           //random spot should be same string as the fileWordn (fileWord3...fileWord4...)
       }
}
