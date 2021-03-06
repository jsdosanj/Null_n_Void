import javax.swing.JOptionPane;

public class Distance_Code {
   public static void main(String[] args) {
       // write code to ouput here
       String s = JOptionPane.showInputDialog("S");
       String t = JOptionPane.showInputDialog("T");
       System.out.println(LD(s,t));
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
           return d[n][m];
       }
}