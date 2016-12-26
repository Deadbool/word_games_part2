package wordgame.abstraction.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import wordgame.GameTUI;

public class Dictionnary {
	
	public static final Dictionnary DICO = new Dictionnary(GameTUI.class.getResource("/Dico.txt").getPath());
	
	private Set<String> words;
	
	public Dictionnary(String fileName) {
		this.words = new TreeSet<String>();
		File fileDic = new File(fileName);
		Scanner scDic;
		try {
			scDic = new Scanner(fileDic);
			while (scDic.hasNextLine()) {
				String w = scDic.nextLine();
				if (!w.isEmpty()) {
					 words.add(w.toUpperCase());
				} 
			}
			scDic.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public boolean contain(String word) {
		return words.contains(word.toUpperCase());
	}
	
}
