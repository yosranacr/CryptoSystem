/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package security;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Yosra Naceur
 */


public class Vigenere {
    
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


    public void VigenereEncipher(File f ,String s,String out) throws IOException{
    String result = encrypt( f, s);
    StringToFile(result, out);
    
    
    
}
    
        public void VigenereDecipher(File f ,String s,String out) throws IOException{
    String result = decrypt( f, s);
      StringToFile(result, out);
    
}
    
        String encrypt(File f,  String key) throws IOException {
            
        String text = FileToString(f);
        String res = "";
        text = text.toUpperCase();
        for (int i = 0, j = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c < 'A' || c > 'Z') continue;
            res += (char)((c + key.charAt(j) - 2 * 'A') % 26 + 'A');
            j = ++j % key.length();
        }
        return res;
    }

        String decrypt(File f,  String key) throws IOException {
        String text = FileToString(f);
        String res = "";
        text = text.toUpperCase();
        for (int i = 0, j = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c < 'A' || c > 'Z') continue;
            res += (char)((c - key.charAt(j) + 26) % 26 + 'A');
            j = ++j % key.length();
        }
        return res;
    }
}
