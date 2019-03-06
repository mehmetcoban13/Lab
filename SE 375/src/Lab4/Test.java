package Lab4;
import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* !! IMPORTANT -- ADD "file1", "file2", and "file3" as an argument, copy-paste to the Java project as well !! */

public class Test {
	
	public static void main(String[] args) {
		
		String query; /* A String For User Input */ 
		
		ExecutorService executor1 = Executors.newFixedThreadPool(10);
		
		// #Q-1
		Map<String, Integer> DS1 = new HashMap<String, Integer>(); /* HashTable Array To Store The Results */
		 /* Thread Array for Word Counter */
		
		
			for(int i=0; i<args.length; i++) { /* Creating and starting the threads for distinct word counter */
				Runnable thr1 =new MyThread1(args[i], DS1);
				executor1.execute(thr1);
			}
		
		
		executor1.shutdown();
		
		while (!executor1.isTerminated()) {}
		System.out.println("Finished all threads");
		
		
		System.out.println("\nThere are " + DS1.size() + " distinct words in files file1, file2, file3.");
		
		/* Query a word how many time(s) appears */
		System.out.print("\nSearch a word to query how many times there are(case sensitive!) : ");
		query = new Scanner(System.in).nextLine();
		
		if(DS1.get(query)!=null) 
			System.out.println("\nThe word '" + query + "' appears " + DS1.get(query) + " time(s)...\n");
		else 
			System.out.println("\nThe word '" + query + "' does not exist in these files...\n");
	
		System.out.println();
		
	
		
		
		// #Q-1
		ExecutorService executor2 = Executors.newFixedThreadPool(10);
		ConcurrentHashMap<String, String> DS2 = new ConcurrentHashMap<String, String>(); /* HashTable Array To Store The Results */
		 /* Thread Array for Word Counter */
	
		
		for(int i=0; i<args.length; i++) { /* Creating and starting the threads for distinct word counter */
			Runnable thr2 =new Thread(new MyThread2(args[i], DS2));
			executor2.execute(thr2);
		}
		executor2.shutdown();
		
		while (!executor2.isTerminated()) {}
		System.out.println("Finished all threads");
		
		System.out.println("\nThere are " + DS2.size() + " distinct words in files file1, file2, file3.");
		
		/* Query a word where appears in the files */
		System.out.print("\nSearch a word to query in which file(s) contains(case sensitive!) : ");
		query = new Scanner(System.in).nextLine();
		
		if(DS2.get(query)!=null) 
			System.out.println("\nThe word '" + query + "' appears in " + DS2.get(query) + ".\n");
		else 
			System.out.println("\nThe word '" + query + "' does not exist in these files.\n");
	}
}


class Lab3 {
	
	//attributes
	private String fileName;
	private Map<String, Integer> hashMap;
	private Map<String, String> hashMapLoc;
	
	//setters-getters
	public void setHashMap(Map<String, Integer> hashMap) { this.hashMap = hashMap; }
	public void setHashMapLoc(Map<String, String> hashMapLoc) { this.hashMapLoc = hashMapLoc; }
	public String getFileName() { return fileName; }
	public Map<String, Integer> getHashMap() { return hashMap; }
	public Map<String, String> getHashMapLoc() { return hashMapLoc; }
	
	// Lab2 Body Methods
	public Lab3(String fileName) { this.fileName = fileName; }
	
	protected String[] splitWords(String line, String split) {
		line = line.trim();
		line = line.replace(",", "");
		line = line.replace(".", "");
		return line.split(split);
	}
}

class MyThread1 extends Lab3 implements Runnable {
	
	public MyThread1(String fileName, Map<String, Integer> hashMap)
		{ super(fileName); setHashMap(hashMap); }

	@Override
	public void run() {
		String[] words = null ;
		System.out.println("Thread parsing " + getFileName() + "...");
		
		try { words = splitWords(new Scanner(new File(getFileName())).nextLine(), " "); }
		catch(Exception e) {}
		
		/* It prevents synchronization problem of hashtable which is used as shared by threads*/
		for(int i=0; i<words.length ; i++) {
			if(this.getHashMap().get(words[i])==null)
				this.getHashMap().put(words[i], 1);
			else 
				this.getHashMap().put(words[i], getHashMap().get(words[i]) + 1);
		}
	}
}

class MyThread2 extends Lab3 implements Runnable {
	public MyThread2(String fileName, Map<String, String> hashMapLoc) 
		{ super(fileName); setHashMapLoc(hashMapLoc); }

	@Override
	public void run() {
		String[] words = null ;
		System.out.println("Thread parsing " + getFileName() + "...");
		
		try { words = splitWords(new Scanner(new File(getFileName())).nextLine(), " "); }
		catch(Exception e) {}
		
		/* It prevents synchronization problem of hashtable which is used as shared by threads*/
		for(int i=0; i<words.length ; i++) {
			if(this.getHashMapLoc().get(words[i])==null)
				this.getHashMapLoc().put(words[i], getFileName());
			else if(! getHashMapLoc().get(words[i]).contains(getFileName()))
				getHashMapLoc().put(words[i], getHashMapLoc().get(words[i]) + ", " + getFileName());
		}
	}
}