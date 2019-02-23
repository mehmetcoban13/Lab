import java.io.File;
import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.Scanner; 

/* !! IMPORTANT -- ADD "file1", "file2", and "file3" as an argument, copy-paste to the Java project as well !! */
public class Lab1 {

	public static void main(String[] args) {
		
		Hashtable<String, Integer> hashMapWordCtr = new Hashtable<String, Integer>();
		/* To keep the number of appearances of the words. */
		
		Hashtable<String, String> hashMapWordPlace = new Hashtable<String, String>();
		/* To keep the files where the words appear in. */
		
		String query; //To keep a word that is taken from keyboard
		String words[][]; //To keep the words separately according to their files
		
		
		// #Q-1
		words = new String[args.length][];
		
		/* The words in files are taken related to their files to a double array 'lines' */
		try {
			for(int i=0 ; i < args.length ; i++) {
				System.out.println("Parsing " + args[i] + "...");			
				words[i] = splitWords(new Scanner(new File(args[i])).nextLine(), " ");
				/* 'splitWords' function takes place at the beginning of scanning the files */
			}
		} catch (FileNotFoundException e) { e.printStackTrace(); }
		
		
		/* Consider all words to calculate the number of appereances and keep them in a hash table */
		for(int i=0; i<words.length ; i++) {
			for(int j=0; j<words[i].length; j++) {
				if(hashMapWordCtr.get(words[i][j])==null)
					hashMapWordCtr.put(words[i][j], 1);
				else
					hashMapWordCtr.put(words[i][j], hashMapWordCtr.get(words[i][j]) + 1);
			}
		}
		
		System.out.println("There are " + hashMapWordCtr.size() + " distinct words in files file1, file2, file3.");
		
		/* Query a word how many time(s) appears */
		System.out.print("\nSearch a word to query how many times there are(case sensitive!) : ");
		query = new Scanner(System.in).nextLine();
		
		if(hashMapWordCtr.get(query)!=null) 
			System.out.println("\nThe word '" + query + "' appears " + hashMapWordCtr.get(query) + " time(s)...\n");
		else 
			System.out.println("\nThe word '" + query + "' does not exist in these files...\n");
		
		
		System.out.println();
		
		
		// #Q-2
		
		/* Consider all words to find their places in the files and keep their place in a hash table */
		for(int i=0; i<words.length ; i++) {
			System.out.println("Parsing " + args[i] + "...");	
			for(int j=0; j<words[i].length; j++) {
				if(hashMapWordPlace.get(words[i][j])==null)
					hashMapWordPlace.put(words[i][j], args[i]);
				else {
					if(! hashMapWordPlace.get(words[i][j]).contains(args[i]))
						hashMapWordPlace.put(words[i][j], hashMapWordPlace.get(words[i][j]) + ", " + args[i]);
				}
			}
		}
		
		System.out.println("There are " + hashMapWordPlace.size() + " distinct words in files file1, file2, file3.");
		
		
		/* Query a word where appears in the files */
		System.out.print("\nSearch a word to query in which file(s) contains(case sensitive!) : ");
		query = new Scanner(System.in).nextLine();
		
		if(hashMapWordPlace.get(query)!=null) 
			System.out.println("\nThe word '" + query + "' appears in " + hashMapWordPlace.get(query) + ".\n");
		else 
			System.out.println("\nThe word '" + query + "' does not exist in these files.\n");
		
	}
	
	/* This function removes all commas and dots and does the splitting job according to String "split" */
	private static String[] splitWords(String line, String split) {
		line = line.trim();
		line = line.replace(",", "");
		line = line.replace(".", "");
		return line.split(split);  //and returns an array of the words
	}
}