package Lab5;
import java.io.File;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.concurrent.locks.Lock;
/* !! IMPORTANT -- ADD "file1", "file2", and "file3" as an argument, copy-paste to the Java project as well !! */
import java.util.concurrent.locks.ReentrantLock;

public class Test {
	
	public static void main(String[] args) {
		
		String query; /* A String For User Input */ 
		
		// #Q-1
		Hashtable<String, Integer> DS1 = new Hashtable<String, Integer>(); /* HashTable Array To Store The Results */
		Hashtable<String, String> DS2 = new Hashtable<String, String>(); /* HashTable Array To Store The Results */
		Thread[] thr = new Thread[args.length]; /* Thread Array for Word Counter */
		
		
		for(int i=0; i<args.length; i++) { /* Creating and starting the threads for distinct word counter */
			thr[i]=new Thread(new MyThread1(args[i], DS1, DS2));
			thr[i].start();
		}
		
		// For second part **It's faster than the first(above) one.
		// Because shorter we use the lock, faster operations we get.
		/*for(int i=0; i<args.length; i++) { 
			thr[i]=new Thread(new MyThread2(args[i], DS1, DS2));
			thr[i].start();
		}*/
		
		for(int i=0; i<args.length; i++) { /* Waiting the threads to finish their job before to continue */
			try { thr[i].join(); } 
			catch (InterruptedException e) { e.printStackTrace(); }
		}
		System.out.println("Threads are complete. Aggregating results...");
		
		
		System.out.println("\nThere are " + DS1.size() + " distinct words in files file1, file2, file3.");
		System.out.println("\nThere are " + DS2.size() + " distinct words in files file1, file2, file3.");
		
		/* Query a word how many time(s) appears */
		System.out.print("\nSearch a word to query how many times there are(case sensitive!) : ");
		query = new Scanner(System.in).nextLine();
		
		if(DS1.get(query)!=null) 
			System.out.println("\nThe word '" + query + "' appears " + DS1.get(query) + " time(s)...\n");
		else 
			System.out.println("\nThe word '" + query + "' does not exist in these files...\n");
		
		
		
		// #Q-2
		
		/* Query a word where appears in the files */
		System.out.print("\nSearch a word to query in which file(s) contains(case sensitive!) : ");
		query = new Scanner(System.in).nextLine();
		
		if(DS2.get(query)!=null) 
			System.out.println("\nThe word '" + query + "' appears in " + DS2.get(query) + ".\n");
		else 
			System.out.println("\nThe word '" + query + "' does not exist in these files.\n");
	}
}


/*Class For Thread to Count The Words In The Files And 
 * To Keep The Names of The Files Where The Words Appear In*/
class Lab5 {
	
	//attributes
	private String fileName;
	private Hashtable<String, Integer> hashMap;
	private Hashtable<String, String> hashMapLoc;
	protected final Lock lock = new ReentrantLock(); 
		
	//setters-getters
	public void setFileName(String fileName) { this.fileName = fileName;}
	public void setHashMap(Hashtable<String, Integer> hashMap) { this.hashMap = hashMap; }
	public void setHashMapLoc(Hashtable<String, String> hashMapLoc) { this.hashMapLoc = hashMapLoc; }
	public String getFileName() { return fileName; }
	public Hashtable<String, Integer> getHashMap() { return hashMap; }
	public Hashtable<String, String> getHashMapLoc() { return hashMapLoc; }
	
	public Lab5(String fileName, Hashtable<String, Integer> hashMap, Hashtable<String, String> hashMapLoc) { 
		setFileName(fileName); setHashMap(hashMap); setHashMapLoc(hashMapLoc);
	}
	
	protected String[] splitWords(String line, String split) {
		line = line.trim();
		line = line.replace(",", "");
		line = line.replace(".", "");
		return line.split(split);
	}
}

class MyThread1 extends Lab5 implements Runnable { 
	
	public MyThread1(String fileName, Hashtable<String, Integer> hashMap, Hashtable<String, String> hashMapLoc) { 
		super(fileName, hashMap, hashMapLoc);
	}
	
	@Override
	public void run() {
		String word = "";
		String[] words = null;
		Scanner scan;
		System.out.println("Thread parsing " + getFileName() + "...");
		
		try {
			scan = new Scanner(new File(getFileName()));
			while (scan.hasNextLine()) { word += scan.nextLine(); }
			words = splitWords(word, " ");
			scan.close();
		} catch(Exception e) {}
		
		
		/*Longer Time We Use The Lock */
		super.lock.lock();
		try {
			
			for(int i=0; i<words.length ; i++) {
				if(this.getHashMap().get(words[i])==null)
					this.getHashMap().put(words[i], 1);
				else 
					this.getHashMap().put(words[i], getHashMap().get(words[i]) + 1);
			}
			
			for(int i=0; i<words.length ; i++) {
				if(this.getHashMapLoc().get(words[i])==null)
					this.getHashMapLoc().put(words[i], getFileName());
				else if(! getHashMapLoc().get(words[i]).contains(getFileName()))
					getHashMapLoc().put(words[i], getHashMapLoc().get(words[i]) + ", " + getFileName());
			}
		} catch (Exception e) { }
		finally { lock.unlock(); }
	}
}

class MyThread2 extends Lab5 implements Runnable { 
	
	public MyThread2(String fileName, Hashtable<String, Integer> hashMap, Hashtable<String, String> hashMapLoc) { 
		super(fileName, hashMap, hashMapLoc);
	}
	
	@Override
	public void run() {
		String word = "";
		String[] words = null;
		Scanner scan;
		System.out.println("Thread parsing " + getFileName() + "...");
		
		try {
			scan = new Scanner(new File(getFileName()));
			while (scan.hasNextLine()) { word += scan.nextLine(); }
			words = splitWords(word, " ");
			scan.close();
		} catch(Exception e) {}
		
		
		/*Shorter Time We Use The Lock */
		lock.lock();
		try {
			
			for(int i=0; i<words.length ; i++) {
				if(this.getHashMap().get(words[i])==null)
					this.getHashMap().put(words[i], 1);
				else 
					this.getHashMap().put(words[i], getHashMap().get(words[i]) + 1);
			}
		} catch (Exception e) { }
		finally { lock.unlock(); }
			
		lock.lock();
		try {
			for(int i=0; i<words.length ; i++) {
				if(this.getHashMapLoc().get(words[i])==null)
					this.getHashMapLoc().put(words[i], getFileName());
				else if(! getHashMapLoc().get(words[i]).contains(getFileName()))
					getHashMapLoc().put(words[i], getHashMapLoc().get(words[i]) + ", " + getFileName());
			}
		} catch (Exception e) { }
		finally { lock.unlock(); }
	}
}
