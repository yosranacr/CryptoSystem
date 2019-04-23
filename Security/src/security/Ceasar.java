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


public String FileToString(File f) throws IOException
{
    BufferedReader reader = new BufferedReader(new FileReader(f.getName()));
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

public void StringToFile(String s, String FileName) {
    
}

public void caesarEncipher(File f ,int s,String out) throws IOException{
    String result = encrypt( s, f);
      StringToFile(result, out);
    
}

public void caesarDecipher(File f ,int s,String out) throws IOException{
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
        
     
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}