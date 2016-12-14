package wordgame.abstraction.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class Dictionnary {
	
	private Set<String> words;
	
	public Dictionnary(String fileName) throws FileNotFoundException {
		this.words = new TreeSet<String>();
		File fileDic = new File(fileName);
		Scanner scDic = new Scanner(fileDic);
		while (scDic.hasNextLine()) {
			String w = scDic.nextLine();
			if (!w.isEmpty()) {
				 words.add(w);
			} 
		}
		scDic.close();
	}
	
	public boolean contain(String word) {
		return words.contains(word);
	}
	
}
