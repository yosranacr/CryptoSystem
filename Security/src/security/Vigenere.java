/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package security;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Yosra Naceur
 */


public class Vigenere {
    
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
        
        public  String cryptanalysis(File f) throws IOException
	{
		// Cassage de la longueur de la clé en calculant l'indice de coincidence
		// On fait en force brute en testant pour chaque longueur de clé possible
	String ciphertext = FileToString(f);	
        System.out.println(ciphertext);
            int I = 0;
		boolean unfound = true;
		ArrayList<String> createdFiles = new ArrayList<String>();
		
		// Pour chaque taille de clé possible, mettre jusqu'à la taille totale du texte
		while(unfound && I < ciphertext.length())
		{
			createdFiles.clear();
			I++;
			// Tableau où seront stocké les indices de coincidence
			float ic[] = new float[I];
			
			// Pour chaque "partition"
			for(int i = 0; i < I; i++)
			{
				// Pour une taille de clé, on divise le texte en sous textes qu'on stocke dans des fichiers
				String generatedFilename = "workingfiles/key" + I + "part" + i + ".txt"; 
				createdFiles.add(generatedFilename);
				
				FileWriter out = new FileWriter(generatedFilename, false);
				for(int j = 0; j < ciphertext.length(); j++)
				{
					if(j % I == i)
						out.write(ciphertext.charAt(j));
				}
				
				out.close(); // On vide le buffer
				
				// Pour chaque texte créé on stocke l'indice de coincidence calculé
				BufferedReader in = new BufferedReader(new FileReader(generatedFilename));
				String subtext = in.readLine();
								
				// Boucle pour calculer la fréquence
				for(int j = 97; j <= 122; j++)
				{
					int frequency = 0;
					
					for(int k = 0; k < subtext.length(); k++)
					{
						if(subtext.charAt(k) == (char)j)
							frequency++;
					}
					
					ic[i] += (float) (Math.pow(frequency, 2) / (Math.pow(subtext.length(), 2)));
				}

				in.close();
			}		
			
			// Taux d'erreur en %
			double tolerance = 15;
			boolean isGoodLength = true;
			System.out.println("Test pour la taille : " + I);
			
			for(int i = 0; i < ic.length; i++)
			{
				System.out.println((0.075 + (0.075 * tolerance / 100) + " " + ic[i] + " " + (0.075 - (0.075 * tolerance / 100))));
				if(0.075 + (0.075 * tolerance / 100) < ic[i] || 0.075 - (0.075 * tolerance / 100) > ic[i])
					isGoodLength = false;
			}
			
			// Si tous les indices correspondent, c'est certainement la bonne clé ! On la calcule et on la propose
			if(isGoodLength)
			{
				System.out.println("La clé est certainement de taille " + I);
				unfound = false;
			}
			
			// Si la taille de la clé ne parait pas être bonne, on continue
		}
		
		String key = "";
		for(int i = 0; i < createdFiles.size(); i++)
		{
			key += (char)caesar_findGap(createdFiles.get(i));
		}
		
		return key;
	}
        	public static int caesar_findGap(String filename) throws IOException
	{
		// Tableau des fréquences (Code ASCII, Fréquence)
		int frequences[] = new int[26];
		BufferedReader in = new BufferedReader(new FileReader(filename));
		String message = in.readLine();
						
		// Calcul des fréquences des lettres
		for(int i = 97; i <= 122; i++)
		{
			for(int j = 0; j < message.length(); j++)
			{
				if(message.charAt(j) == (char)i)
					frequences[i - 97]++;
			}
		}
				
		int highestIndex = 0;
		for(int i = 0; i < 26; i++)
		{
			if(frequences[i] > frequences[highestIndex])
				highestIndex = i;
		}
				
		System.out.println("\nLettre la plus fréquente : '" + (char)(highestIndex + 97) + "', correspond donc au 'e'");
		
		// On récupère la valeur du décalage
		int decalage = ((int)'z' - (int)'a') - ((((int)'z' - (int)'a') - highestIndex) + ((int)'e' - (int)'a'));
		if(decalage < 0)
			decalage += 26;
		System.out.println("Décalage trouvé : " + decalage);
		
		return decalage + 97;
	}
}
