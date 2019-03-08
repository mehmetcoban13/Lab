package Lab4;
import java.io.File;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* !! IMPORTANT -- Please Add "file1", "file2", and "file3" as an argument, copy-paste these files inside the Java project as well !! */

public class Test {
	
	public static void main(String[] args) {
		
		String query; /* A String For User Input */ 
		
		
		// #Q-1
		ConcurrentHashMap<String, Integer> DS1 = new ConcurrentHashMap<String, Integer>();
		
		ExecutorService executor1 = Executors.newFixedThreadPool(10);
		
		for(int i=0; i<args.length; i++) { 
				executor1.submit(new MyThread1(args[i], DS1));
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
		
		
		
		// #Q-2
		ConcurrentHashMap<String, String> DS2 = new ConcurrentHashMap<String, String>();
		
		ExecutorService executor2 = Executors.newFixedThreadPool(10);
		
		for(int i=0; i<args.length; i++) { 
			executor2.submit(new MyThread2(args[i], DS2));
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


abstract class Lab4 {
	
	//attributes
	private String fileName;
	
	//setters-getters
	public String getFileName() { return fileName; }
	
	// Lab2 Body Methods
	public Lab4(String fileName) { this.fileName = fileName; }
	
	protected String[] splitWords(String line, String split) {
		line = line.trim();
		line = line.replace(",", "");
		line = line.replace(".", "");
		return line.split(split);
	}
}

/*Class For Thread to Count The Words In The Files*/
class MyThread1 extends Lab4 implements Runnable {
	private ConcurrentHashMap<String, Integer> hashMap;
	
	public MyThread1(String fileName, ConcurrentHashMap<String, Integer> hashMap)
		{ super(fileName); this.hashMap = hashMap; }

	@Override
	public void run() {
		String[] words = null ;
		System.out.println("Thread parsing " + getFileName() + "...");
		
		try { words = splitWords(new Scanner(new File(getFileName())).nextLine(), " "); }
		catch(Exception e) {}
		
		for(int i=0; i<words.length ; i++) {
			if(hashMap.computeIfPresent(words[i], (key, val) -> val +1) ==null)
				hashMap.computeIfAbsent(words[i], k -> 1);
		}
	}
}

/*Class For Thread To Keep The Names of The Files Where The Words Appear In*/
class MyThread2 extends Lab4 implements Runnable {
	private ConcurrentHashMap<String, String> hashMap;
	
	public MyThread2(String fileName, ConcurrentHashMap<String, String> hashMap) 
		{ super(fileName); this.hashMap = hashMap; }

	@Override
	public void run() {
		String[] words = null ;
		System.out.println("Thread parsing " + getFileName() + "...");
		
		try { words = splitWords(new Scanner(new File(getFileName())).nextLine(), " "); }
		catch(Exception e) {}
		
		for(int i=0; i<words.length ; i++) {
			if(! hashMap.computeIfAbsent(words[i], k -> getFileName()).contains(getFileName()))
				hashMap.compute(words[i], (k, v) -> v + ", " + getFileName());
		}
	}
}
