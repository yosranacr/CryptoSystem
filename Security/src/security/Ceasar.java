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
import java.util.Hashtable;
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
	public static final char[] alpha = 
			{'A','B','C','D','E','F','G','H','I','J','K','L','M',
			'N','O','P','Q','R','S','T','U','V','W','X','Y','Z'}; 
    public int analyse_frequence(File f) throws IOException{
                     String fichier = FileToString(f);
                    fichier = fichier.toUpperCase();
                    char[] texte = fichier.toCharArray();
                    int x;
                    Hashtable<Character, Integer> apparence = new Hashtable<Character, Integer>();
   
                    //on a initialisé l'appartion de chaque caractères avec des 0
                    for(int i=0; i<26;i++){
                    apparence.put(alpha[i],0);
                    }
                    
                    //System.out.println(apparence.get('A'));
                    
                    //on a comptabilisé pour chaque caractère le nombre de fois où il apparait
                    for (int i=0; i< texte.length;i++){
                    if (Character.isUpperCase(texte[i])){
                    x= apparence.get(texte[i]);
                     //System.out.println(x);
                    x++;
                    apparence.put(texte[i], x);
                    }
                    }
                    
                    //içi on va déterminer le caractère le plus utilisé
                    int max = apparence.get(alpha[0]);
                    char position = 'A';
                    
                    for (int i=0; i< 26;i++){
                    if (apparence.get(alpha[i])> max) {
                        max= apparence.get(alpha[i]);
                        position = alpha[i];
                    }
                    }
                    
                    //System.out.println(max);
                    //System.out.println(position);
                    
                    //on a supposé que la lettre E est la plus utilisé pour deduire le decalage
                    
                    int decalage ;
                    
                    if( position > 'S') decalage = position - 'S';
                    else decalage = 26 - ('S' - position);
                    
                    //System.out.println(-position);
                    System.out.println("le decalage est de "+ decalage +" caractères");
                  
                    return decalage;   
                }
                
    
    
public String FileToString(File f) throws IOException
{System.out.println("Hello");
    BufferedReader reader = new BufferedReader(new FileReader(f));
    
StringBuilder stringBuilder = new StringBuilder();
String line = null;
String ls = System.getProperty("line.separator");
while ((line = reader.readLine()) != null) {
	stringBuilder.append(line);
	stringBuilder.append(ls);
}
// delete the last new line separator
stringBuilder.deleteCharAt(stringBuilder.length() - 1);
reader.close();

String content = stringBuilder.toString();
return content;
}

public void StringToFile(String s, String FileName) throws IOException {
    System.out.println(s);
        BufferedWriter writer = new BufferedWriter(new FileWriter(FileName));
    writer.write(s);
    writer.close();
}

public void caesarEncipher(File f ,int s,String out) throws IOException{
    String result = encrypt( s, f);
      StringToFile(result, out);
    
}

public void caesarDecipher(File f ,int s,String out) throws IOException{
    System.out.println(f.getName() +" " + s + " " + out);
    String result = decrypt( s, f);
      StringToFile(result, out);
    
}
public String encrypt( int s, File f) throws IOException 
{   String text = FileToString(f);
  StringBuffer resultb= new StringBuffer(); 
  
        for (int i=0; i<text.length(); i++) 
        { 
            if (Character.isUpperCase(text.charAt(i))) 
            { 
                char ch = (char)(((int)text.charAt(i) + 
                                  s - 65) % 26 + 65); 
                resultb.append(ch); 
            } 
            else
            { 
                char ch = (char)(((int)text.charAt(i) + 
                                  s - 97) % 26 + 97); 
                resultb.append(ch); 
            } 
        } 
        String result = resultb.toString();
        return result; 
} 

 

        public  String decrypt( int s, File f) throws IOException

        {  char ch;
            String text = FileToString(f);
            StringBuffer resultb= new StringBuffer(); 
            String decryptedMessage = "";

              	for(int i = 0; i < text.length(); ++i){
			ch = text.charAt(i);
			
			if(ch >= 'a' && ch <= 'z'){
	            ch = (char)(ch - s);
	            
	            if(ch < 'a'){
	                ch = (char)(ch + 'z' - 'a' + 1);
	            }
	            
	            decryptedMessage += ch;
	        }
	        else if(ch >= 'A' && ch <= 'Z'){
	            ch = (char)(ch - s);
	            
	            if(ch < 'A'){
	                ch = (char)(ch + 'Z' - 'A' + 1);
	            }
	            
	            decryptedMessage += ch;
	        }
	        else {
	        	decryptedMessage += ch;
	        }
		}

    return decryptedMessage;
}
         public int[] countLetters (String msg) {
        int[] counts = new int[26];
        for (char c : msg.toCharArray()) {
            char cuc = Character.toUpperCase(c);
            if (Character.isAlphabetic(cuc)) counts[ALPHABET.indexOf(cuc)]++;
        }
           
        return counts;
    }
    
    /**
     * Find index of max value in array.
     * @param   vals    array of ints to be checked.
     * @return  index of max value in ints array
     */
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}