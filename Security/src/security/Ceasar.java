/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package security;

import static java.awt.SystemColor.text;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Scanner;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Yosra Naceur
 */
@Entity
public class Ceasar implements Serializable {

    @Id
    private Long id;
final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";  
   public static void caesarEncipher (File input, int shift, String out) throws FileNotFoundException, IOException

        {      String dir = "C:\\Users\\Yosra Naceur\\Desktop\\";
                
              //  File newfile = new File( out);
                File textFile = new File(dir + out);
                BufferedWriter bw = new BufferedWriter(new FileWriter(textFile));
              //  PrintStream encoded = new PrintStream(newfile);  // creates new file

                       Scanner in = new Scanner(input); // creates scanner

                while (in.hasNextLine())  // runs while the file has another line

                {

                        String word = in.nextLine();   
                        System.out.println(word);// gets next line of file

                       String cipher = word.toUpperCase();     // makes all letters uppercase

                        String encipher = "";                   // String to be written into new file

                        int loop;

                        for (loop = 0; loop < cipher.length(); loop++) // loops through the line to check each character

                        {

                                char curr = cipher.charAt(loop);  // current character

                                char newChar = crypt(curr, shift);  // runs through crypt method (Below)
                                 bw.write(newChar);
                                encipher = encipher + newChar; // adds the new character to outgoing string

                        }
                        
                       // encoded.println(encipher);
                        

                }

               // encoded.close();
               bw.close();
                
        }

 

        public static String caesarDecipher (File input, int shift, String out) throws FileNotFoundException

        {

                PrintStream decoded = new PrintStream(out);

               
                Scanner in = new Scanner(input);

                while (in.hasNextLine())

                {

                        String word = in.nextLine();

                        word = word.toUpperCase();                                    

                        String cipher = word;

                        String decipher = "";

                        int loop;

                        for (loop = 0; loop < cipher.length(); loop++)

                        {

                                char curr = cipher.charAt(loop);

                                char newChar = decrypt(curr, shift);

                                decipher = decipher + newChar;

                        }

                        decoded.println(decipher);

                }

                decoded.close();

                return "DONE";

       }

 

        public static char crypt(char c, int shift)

        {

                char encrypted = c;  // character passed in from for loop

                int alter = shift; // shift passed in

                if (Character.isLetter(encrypted)) // checks for only letters

               {

                        int enc;

                       if ((int) encrypted + alter > 91) // if ASCII value after altering is beyond 'Z' value, run this to loop back to 'A'

                        {

                                enc = encrypted - 65;

                                enc += alter;

                                enc = enc % 26;

                                enc += 65;

                                encrypted = (char) enc;

                       }

                        else enc =  encrypted + alter;

                        encrypted = (char) enc;

                }

                return encrypted;

        }

 

        public static char decrypt(char c, int shift)

        {

                char decrypted = c;

                int alter = shift;

                if (Character.isLetter(decrypted))

                {

                        int dec;

                        if ((int) decrypted - alter < 65)  // if ASCII value after altering is below 'A' value, run this to correct overflow

                        {

                                dec = decrypted + 65;

                                dec -= alter;

                                dec = dec % 26;

                                dec -= 65;

                                decrypted = (char) dec;

                       }
                        else dec = decrypted - alter;

                        decrypted = (char) dec;

                }

                return decrypted;

    
}
        
          
    public static int caesar_findGap(File filename) throws IOException
	{
		// Tableau des fréquences (Code ASCII, Fréquence)
		int frequences[] = new int[26];
		BufferedReader in = new BufferedReader(new FileReader(filename));
		String msg = in.readLine();
						
		 int[] counts = countLetters(msg);
                   //find intex of highest count
                 int indOfMax = maxIndex(counts);
                    //find&return shift
                final int IND_E = 4;
               int key = indOfMax - IND_E;
               if (key<0) return key+26;
               else return key;
	}
 public int maxIndex(int[] vals) {
        
        int indOfMax = 0;
        int maxSoFar = vals[0];
        
        for (int i=1;i<vals.length;i++){
        
            if (vals[i]>maxSoFar) {
                maxSoFar = vals[i];
                indOfMax = i;
            }
        }
        
        return indOfMax;
    }
 
   public int[] countLetters (String msg) {
        int[] counts = new int[26];
        for (char c : msg.toCharArray()) {
            char cuc = Character.toUpperCase(c);
            if (Character.isAlphabetic(cuc)) counts[ALPHABET.indexOf(cuc)]++;
        }
           
        return counts;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}