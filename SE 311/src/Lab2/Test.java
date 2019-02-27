package Lab2;

public class Test {

	public static void main(String[] args) {
		
		Iterator it = new Iterator(4);
		it.getParts()[0] = new Motherboard("Motherboard", "ABC", 123, new PricePolicy(), 
				new Brand("Samsung"));
		it.getParts()[1] = new Memory("Memory", "DEF", 456, new PricePolicy(), 
				new Brand("Intel"), "16 GB");
		it.getParts()[2] = new HardDisk("Hard Disk", "GHI", 259, new PricePolicy(), 
				new Brand("Western Digital"), 
				"1 TB", "2.5 inc");
		
		it.display();
		System.out.println("Total Price : " + it.totalPrice() + "\n\n");
		
		
		it.getParts()[3] = new OpticalDiskDriver("Optical Disk Driver","ABC", 78, 
				new PricePolicy(), new Brand("HTC"));
		
		it.getParts()[0].getPricePolicy().setPricePolicy(1.50);
		it.getParts()[1].getPricePolicy().setPricePolicy(1.10);
		it.getParts()[2].getPricePolicy().setPricePolicy(0.92);
		it.getParts()[3].getPricePolicy().setPricePolicy(2);
		
		System.out.println("\nNew Part(s) Added And Some Changes Are Done...\n");
		
		it.display(); 
		System.out.println("Total Price : " + it.totalPrice() + "\n\n");
	}
}

abstract class Part {
	
	//attributes
	private String name;
	private String model;
	private double price;
	private PricePolicy pricePolicy;
	private Brand brand;
	
	//setter-getters
	public void setName(String name) { this.name = name; }
	public void setModel(String model) { this.model = model; }
	public void setPrice(double price) { this.price = price; }
	public void setPricePolicy(PricePolicy pricePolicy) { this.pricePolicy = pricePolicy; }
	public void setBrand(Brand brand) { this.brand = brand; }
	public String getName() { return this.name; }
	public String getModel() { return this.model; }
	public double getPrice() { return pricePolicy.getPrice(price); }
	public PricePolicy getPricePolicy() { return this.pricePolicy; }
	public Brand getBrand() { return this.brand; }
	
	//body of Part Class
	public Part(String name, String model, double price, PricePolicy pricePolicy, Brand brand) {
		this.name = name;
		this.model = model;
		this.price = price;
		this.pricePolicy = pricePolicy;
		this.brand = brand;
	}
	
	public void print() {
		System.out.println("Name : " + getName());
		System.out.println("Model : " + getModel());
		System.out.println("Price : " + getPrice());
		System.out.println("Price Policy : " + getPricePolicy().getFactor());
		System.out.println("Brand : " + getBrand().getBrandName());
	} 
}

class PricePolicy {
	
	private double factor = 1;
	
	public void setPricePolicy (double factor) { this.factor = factor; }
	public double getPrice(double price) { return price * this.factor; }
	public double getFactor() { return this.factor; }
	
}

class Motherboard extends Part {

	public Motherboard(String name, String model, double price, PricePolicy pricePolicy, Brand brand) {
		super(name, model, price, pricePolicy, brand);
	}
}

class Memory extends Part {
	
	private String capacity;
	
	public String getCapacity() { return this.capacity; }
	
	public Memory(String name, String model, double price, PricePolicy pricePolicy, Brand brand, String capacity) {
		super(name, model, price, pricePolicy, brand); this.capacity = capacity;
	}
	
	@Override
	public void print() {
		super.print();
		System.out.println("Capacity : " + getCapacity());
	}
}

class HardDisk extends Part {

	private String capacity;
	private String size;
	
	public String getCapacity() { return this.capacity; }
	public String getSize() { return this.size; }
	
	public HardDisk(String name, String model, double price, PricePolicy pricePolicy, Brand brand, 
			String capacity, String size) {
		super(name, model, price, pricePolicy, brand); this.capacity = capacity; this.size = size;
	}
	
	@Override
	public void print() {
		super.print();
		System.out.println("Capacity : " + getCapacity());
		System.out.println("Size : " + getSize());
	}
}

class OpticalDiskDriver extends Part{

	public OpticalDiskDriver(String name, String model, double price, PricePolicy pricePolicy, Brand brand) {
		super(name, model, price, pricePolicy, brand);
	}
}

class Brand {
	
	private String brandName;

	public String getBrandName() { return brandName; }

	public Brand(String brandName) {
		this.brandName = brandName;
	}
}

class Iterator {
	
	private Part[] parts;
	
	public Part[] getParts() { return this.parts; }
	
	public Iterator(int numberOfParts) { parts = new Part[numberOfParts]; }
	
	public double totalPrice() { /* A Method That Calculates Total Price of An Array of Parts */
		double total = 0.0;
		try { for (int i = 0; i < parts.length; i++) { total += parts[i].getPrice(); } }
		catch(NullPointerException e) { /*Do Nothing */ }
		return total;
	}
	
	public void display() {
		System.out.println(" --- *Computer Parts* ---\n");
		try{
			for(int i=0; i < parts.length; i++) { parts[i].print(); System.out.println(); }
		} catch(NullPointerException e) {}
	}
	
}
