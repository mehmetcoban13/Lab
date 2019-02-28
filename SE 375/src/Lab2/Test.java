package Lab2;
import java.io.File;
import java.util.Hashtable;
import java.util.Scanner;
/* !! IMPORTANT -- ADD "file1", "file2", and "file3" as an argument, copy-paste to the Java project as well !! */

public class Test {
	
	public static void main(String[] args) {
		
		String query; /* A String For User Input */ 
		
		// #Q-1
		Hashtable<String, Integer> [] DS1 = new Hashtable[args.length+1]; /* HashTable Array To Store The Results */
		Thread[] thr1 = new Thread[args.length]; /* Thread Array for Word Counter */
		DS1[0] = new Hashtable<String, Integer>(); /* For Aggregation of Results */
		
		for(int i=0; i<args.length; i++) { /* Creating and starting the threads for distinct word counter */
			DS1[i+1] = new Hashtable<String, Integer>();
			thr1[i]=new Thread(new MyCtrThread(args[i], DS1[i+1]));
			thr1[i].start();
		}
		
		for(int i=0; i<args.length; i++) { /* Waiting the threads to finish their job before to continue */
			try { thr1[i].join(); } 
			catch (InterruptedException e) { e.printStackTrace(); }
		}
		System.out.println("Threads are complete. Aggregating results...");
		
		for(int i=1; i< DS1.length; i++) { /* Aggregating The Results of Threads to One HashTable */
			DS1[i].forEach((k, v) -> {
				if(DS1[0].get(k) == null)
					DS1[0].put(k, v);
				else
					DS1[0].put(k, (v + DS1[0].get(k)));
	        });
		}
		System.out.println("\nThere are " + DS1[0].size() + " distinct words in files file1, file2, file3.");
		
		/* Query a word how many time(s) appears */
		System.out.print("\nSearch a word to query how many times there are(case sensitive!) : ");
		query = new Scanner(System.in).nextLine();
		
		if(DS1[0].get(query)!=null) 
			System.out.println("\nThe word '" + query + "' appears " + DS1[0].get(query) + " time(s)...\n");
		else 
			System.out.println("\nThe word '" + query + "' does not exist in these files...\n");
	
		System.out.println();
		
		
		// #Q-2
		Hashtable<String, String> [] DS2 = new Hashtable[args.length+1]; /* HashTable Array To Store The Results */
		Thread[] thr2 = new Thread[args.length]; /* Thread Array for Word Counter */
		DS2[0] = new Hashtable<String, String>(); /* For Aggregation of Results */
		
		for(int i=0; i<args.length; i++) { /* Creating and starting the threads for distinct word counter */
			DS2[i+1] = new Hashtable<String, String>();
			thr2[i]=new Thread(new MyLocationThread(args[i], DS2[i+1]));
			thr2[i].start();
		}
		
		for(int i=0; i<args.length; i++) { /* Waiting the threads to finish their job before to continue */
			try { thr2[i].join(); } 
			catch (InterruptedException e) { e.printStackTrace(); }
		}
		System.out.println("Threads are complete. Aggregating results.");
		
		for(int i=1; i< DS2.length; i++) { /* Aggregating The Results of Threads to One HashTable */
			DS2[i].forEach((k, v) -> {
				if(DS2[0].get(k) == null)
					DS2[0].put(k, v);
				else 
					DS2[0].put(k, (DS2[0].get(k) + ", " + v));
	        });
		}
		
		System.out.println("\nThere are " + DS2[0].size() + " distinct words in files file1, file2, file3.");
		
		/* Query a word where appears in the files */
		System.out.print("\nSearch a word to query in which file(s) contains(case sensitive!) : ");
		query = new Scanner(System.in).nextLine();
		
		if(DS2[0].get(query)!=null) 
			System.out.println("\nThe word '" + query + "' appears in " + DS2[0].get(query) + ".\n");
		else 
			System.out.println("\nThe word '" + query + "' does not exist in these files.\n");
	}
}

class Lab2 {
	
	//attributes
	private String fileName;
	private Hashtable<String, Integer> hashMap;
	private Hashtable<String, String> hashMapLoc;
	
	//setters-getters
	public void setHashMap(Hashtable<String, Integer> hashMap) { this.hashMap = hashMap; }
	public void setHashMapLoc(Hashtable<String, String> hashMapLoc) { this.hashMapLoc = hashMapLoc; }
	public String getFileName() { return fileName; }
	public Hashtable<String, Integer> getHashMap() { return hashMap; }
	public Hashtable<String, String> getHashMapLoc() { return hashMapLoc; }
	
	// Lab2 Body Methods
	public Lab2(String fileName) { this.fileName = fileName; }
	
	protected String[] splitWords(String line, String split) {
		line = line.trim();
		line = line.replace(",", "");
		line = line.replace(".", "");
		return line.split(split);
	}
}

class MyCtrThread extends Lab2 implements Runnable {
	
	public MyCtrThread(String fileName, Hashtable<String, Integer> hashMap)
		{ super(fileName); setHashMap(hashMap); }

	@Override
	public void run() {
		String[] words = null ;
		System.out.println("Thread parsing " + getFileName() + "...");
		
		try { words = splitWords(new Scanner(new File(getFileName())).nextLine(), " "); }
		catch(Exception e) {}
		
		for(int i=0; i<words.length ; i++) {
			if(this.getHashMap().get(words[i])==null)
				this.getHashMap().put(words[i], 1);
			else
				this.getHashMap().put(words[i], this.getHashMap().get(words[i]) + 1);
		}
	}
}

class MyLocationThread extends Lab2 implements Runnable {

	public MyLocationThread(String fileName, Hashtable<String, String> hashMapLoc) 
		{ super(fileName); setHashMapLoc(hashMapLoc); }

	@Override
	public void run() {
		String[] words = null ;
		System.out.println("Thread parsing " + getFileName() + "...");
		
		try { words = splitWords(new Scanner(new File(getFileName())).nextLine(), " "); }
		catch(Exception e) {}
		
		for(int i=0; i<words.length ; i++) {
			if(this.getHashMapLoc().get(words[i])==null)
				this.getHashMapLoc().put(words[i], getFileName());
		}
	}
}