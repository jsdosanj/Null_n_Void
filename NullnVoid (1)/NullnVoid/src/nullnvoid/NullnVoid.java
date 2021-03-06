package nullnvoid;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.applet.*;
import javafx.application.Application;
import javafx.Applet;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.awt.*;

/**
 *
 * @author dharb
 */

/* TODO
1. *DONE* Method to track lives and end game if you run out
2. *DONE* Track progress and check to see if you chose winWord and then increase difficulty
3. *DONE* Method to increase difficulty
4. More to come
BUGS
1. *FIXED* Similarity label not removing, stacked on top of each other 
2. *FIXED* winWord can be hidden by fillRandomWords();
3. fillWords may not have any similarities to winWord
*/
public class NullnVoid extends Applet{
 String[][] gameSpace = new String[7][7];
 String winWord;
 Text sims = new Text("");
 GridPane pane = new GridPane();
 ArrayList < String > words = new ArrayList < > ();
 ArrayList < String > files = new ArrayList < > ();
 ArrayList < String > symbols = new ArrayList < > ();
 Hyperlink realWords = new Hyperlink();
 Text amtLives = new Text("");
 int counter = 0;
 int count = 0;
 int lives = 5;
 int score = 0;
 //@Override

 public void start(Stage primaryStage) throws IOException {
  //fills various ArrayLists with symbols and file names for later use
  createArrayLists();

  gameSpaceRandomizer();

  //Creating the gridpane and adding labels to it using the gameSpace array
  createGridPane(pane);

  StackPane root = new StackPane();
  root.setId("root");
  root.getChildren().add(pane);
  Scene scene = new Scene(root, 1280, 720);
  scene.getStylesheets().add("styling.css");
  primaryStage.setTitle("Null n Void");
  primaryStage.setScene(scene);
  primaryStage.show();
  
  primaryStage.setFullScreen(true);

  /*
   * @param args the command line arguments
   * @throws java.io.IOException
   */
 }

 public static void main (String[] args) throws IOException{
  init(args);
 }

 public void createGridPane(GridPane pane) {
  /*checks if each index char equals !, then adds it as text normally
       if not, then it adds it as a hyperlink so we can click on it
       and use the onAction event
       */

  for (int x = 0; x < gameSpace.length; x++) {
   for (int y = 0; y < gameSpace.length; y++) {
    if (gameSpace[y][x].charAt(0) == '!') {

     Text label = new Text(gameSpace[y][x]);
     label.setId("label");
     pane.add(label, y, x);
    } else {

     Hyperlink realWords = new Hyperlink(gameSpace[y][x]);
     realWords.setId("hyperlink");
     realWords.setTextFill(Color.WHITE);
     pane.add(realWords, y, x);
     sims = new Text("There are " + LD(realWords.getText(), winWord) + " similarities.");
     amtLives = new Text("YOU HAVE " + lives + " LIVES LEFT!");

     //on click action event that handles most of the game, displays the progress
     //of similarities and lives
     realWords.setOnAction((ActionEvent e) -> {

      int similarities;
      similarities = LD(realWords.getText(), winWord);
      pane.getChildren().remove(sims);
      pane.getChildren().remove(amtLives);
      amtLives = new Text("YOU HAVE " + lives + " LIVES LEFT!");
      amtLives.setId("label");
      sims = new Text("There are " + similarities + " similarities.");
      sims.setId("label");
      pane.add(amtLives, 25, 0);
      pane.add(sims, 20, 0);
      System.out.println("There are " + similarities + " similarities.");

      //when the player chooses the winWord, it increasesDifficulty by adding
      //the longer words
      if (similarities == winWord.length()) {
       try {
        increaseDifficulty();
       } catch (IOException ex) {
        Logger.getLogger(NullnVoid.class.getName()).log(Level.SEVERE, null, ex);
       }
      } else {
       lives--;

      }
      if (lives < 0) {
       pane.getChildren().clear();
       Text loser = new Text("YOU LOSE!");
       loser.setId("label");
       pane.getChildren().add(loser);
       Text scoreAmt = new Text("YOUR SCORE WAS " + score);
       scoreAmt.setId("label");
       pane.add(scoreAmt, 5, 0);
      }

     });
    }
   }
  }
 }
 public void createArrayLists() throws IOException {
  Scanner sc1;
  sc1 = new Scanner(new File("symbols.txt"));
  symbols = new ArrayList < > ();

  while (sc1.hasNext()) {
   String nextWord = sc1.next();
   symbols.add(nextWord);
  }

  Scanner sc2;
  sc2 = new Scanner(new File("files.txt"));

  files = new ArrayList < > ();

  while (sc2.hasNext()) {
   String nextWord = sc2.next();
   files.add(nextWord);
  }
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
  n = s.length();
  m = t.length();
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
    } else {
     cost = 1;
    }
    //Part 6
    d[i][j] = Minimum(d[i - 1][j] + 1, d[i][j - 1] + 1, d[i - 1][j - 1] + cost);
   }
  }
  return ((-1) * (d[n][m])) + (n);

 }

 public void gameSpaceRandomizer() throws IOException {

  //int rWord = (int)(Math.random() * 50);
  // System.out.println(rWord);
  //Put sWord in loop to generate the "Other Words" Must make nested loop for correct word to be randomly placed in a table 
  // int sWord = (int)(Math.random() * 50);
  // write code to ouput here
  //String fileWord3 = Files.readAllLines(Paths.get("char3.txt")).get(rWord);
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
  //  String s = JOptionPane.showInputDialog("This String will compare with String t");
  //   String t = JOptionPane.showInputDialog("This String will compare with String s");
  //Runs 2 Strings through the comparrison
  // System.out.println(s);
  //   System.out.println(t);
  //  System.out.println(LD(s,t));

  /* fills the ArrayList from the file, shuffles the words in Collection, and then
   sets the winWord to index 0 for easy access*/
  fillArray();

  gameSpace = new String[7][7];

  // below code fills an array with random symbols
  fillSymbols();

  //populates the rest of the array with random words from the txt file
  fillRandomWords(words);

  //below code selects a random array spot to put the win word
  setWinWord(winWord);
 }

 public void fillSymbols() {
  for (int i = 0; i < 7; i++)
   for (int j = 0; j < 7; j++)
    gameSpace[i][j] = symbols.get(count) + " ";
  count++;
 }

 public void increaseDifficulty() throws IOException {
  clearArray();
  pane.getChildren().clear();
  counter++;
  score++;
  gameSpaceRandomizer();
  createGridPane(pane);
 }

 public void setWinWord(String winWord) {
  //below code selects a random array spot to put the win word
  String winnerWord = winWord;
  int randomNum1 = (int)(Math.random() * 7);
  int randomNum2 = (int)(Math.random() * 7);
  gameSpace[randomNum1][randomNum2] = winnerWord;
 }


 public void fillRandomWords(ArrayList words) {
  ArrayList < String > randomWords = words;
  for (int i = 1; i < 12; i++) {
   int randomNum3 = (int)(Math.random() * 7);
   int randomNum4 = (int)(Math.random() * 7);
   gameSpace[randomNum3][randomNum4] = randomWords.get(i).toUpperCase();
   System.out.println(randomWords.get(i).toUpperCase());
  }
 }

 public void fillArray() throws IOException {
  //fills the ArrayList words with the words from char3.txt
  String y = files.get(counter);
  System.out.print(counter);

  Scanner x;
  x = new Scanner(new File(y));

  words = new ArrayList < > ();

  while (x.hasNext()) {
   String nextWord = x.next();
   words.add(nextWord);
  }

  //shuffles the char3.txt words in the ArrayList which was made above and filled
  //with words from char3.txt
  Collections.shuffle(words);
  System.out.println(words);

  /*sets the 0th index word as the winWord which is okay because it is
  shuffled everytime */
  winWord = words.get(0).toUpperCase();
  System.out.println("Win word is " + winWord); //for testing purposes
 }

 public void clearArray() {
  for (int i = 0; i < words.size(); i++) {
   words.remove(i);
  }

 }
}