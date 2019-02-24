
public class Test {

	public static void main(String[] args) {
		
		Part[] computerParts = new Part[4];
		computerParts[0] = new Motherboard("ABC", 123, new PricePolicy(), new Brand("Samsung"));
		computerParts[1] = new Memory("DEF", 456, new PricePolicy(), new Brand("Intel"), "16 GB");
		computerParts[2] = new HardDisk("GHI", 259, new PricePolicy(), new Brand("Western Digital"), 
				"1 TB", "2.5 inc");
		
		System.out.println(" --- *Computer Parts* ---\n");
		
		try{
			for(int i=0; i < computerParts.length; i++) {
				computerParts[i].print(); System.out.println();
			}
		} catch(NullPointerException e) {}
			
		System.out.println("Total Price : " + totalPrice(computerParts) + "\n\n");
		
		
		
		computerParts[3] = new OpticalDiskDriver("ABC", 78, new PricePolicy(), new Brand("HTC"));
		computerParts[1].getPricePolicy().setPrice(1.10);
		computerParts[2].getPricePolicy().setPrice(0.92);
		
		System.out.println("\nNew Part(s) Added And Some Changes Are Done...\n");
		
		System.out.println(" --- *Computer Parts* ---\n");
		
		try{
			for(int i=0; i < computerParts.length; i++) {
				computerParts[i].print(); System.out.println();
			}
		} catch(NullPointerException e) {}
		
		System.out.println("New Total Price : " + totalPrice(computerParts));
		

	}
	
	public static double totalPrice(Part parts[]) { 
		double total = 0.0;
		try { for (int i = 0; i < parts.length; i++) { total += parts[i].getPrice(); } }
		catch(NullPointerException e) { /*Do Nothing */ }
		return total; 
	}
}

interface Part {
	
	//setter-getters
	public void setPrice(double price);
	public void setPricePolicy(PricePolicy pricePolicy);
	public String getModel();
	public double getPrice();
	public PricePolicy getPricePolicy();
	public Brand getBrand();
	
	public void print();
}

class PricePolicy {
	
	private double factor = 1;
	
	public void setPrice (double factor) { this.factor = factor; }
	public double getPrice(double price) { return price * this.factor; }
	public double getFactor() { return this.factor; }
	
}

class Motherboard implements Part {
	
	private String model;
	private double price;
	private PricePolicy pricePolicy;
	private Brand brand;
	
	public Motherboard(String model, double price, PricePolicy pricePolicy, Brand brand) {
		this.model = model;
		this.price = price;
		this.pricePolicy = pricePolicy;
		this.brand = brand;
	}

	public void setPrice(double price) { this.price = price; }
	public void setPricePolicy(PricePolicy pricePolicy) { this.pricePolicy = pricePolicy; }
	public String getModel() { return this.model; }
	public double getPrice() { return pricePolicy.getPrice(price); }
	public PricePolicy getPricePolicy() { return this.pricePolicy; }
	public Brand getBrand() { return this.brand; }

	@Override
	public void print() {
		System.out.println("Model : " + getModel());
		System.out.println("Price : " + getPrice());
		System.out.println("Price Policy : " + getPricePolicy().getFactor());
		System.out.println("Brand : " + getBrand().getBrandName());
	}
}


class Memory implements Part {
	
	private String model;
	private double price;
	private PricePolicy pricePolicy;
	private Brand brand;
	private String capacity;
	
	public Memory(String model, double price, PricePolicy pricePolicy, Brand brand, String capacity) {
		this.model = model;
		this.price = price;
		this.pricePolicy = pricePolicy;
		this.brand = brand;
		this.capacity = capacity;
	}

	public void setPrice(double price) { this.price = price; }
	public void setPricePolicy(PricePolicy pricePolicy) { this.pricePolicy = pricePolicy; }
	public String getModel() { return this.model; }
	public double getPrice() { return pricePolicy.getPrice(price); }
	public PricePolicy getPricePolicy() { return this.pricePolicy; }
	public Brand getBrand() { return this.brand; }
	public String getCapacity() { return this.capacity; }

	@Override
	public void print() {
		System.out.println("Model : " + getModel());
		System.out.println("Price : " + getPrice());
		System.out.println("Price Policy : " + getPricePolicy().getFactor());
		System.out.println("Brand : " + getBrand().getBrandName());
		System.out.println("Capacity : " + getCapacity());
	}
}

class HardDisk implements Part {
	
	private String model;
	private double price;
	private PricePolicy pricePolicy;
	private Brand brand;
	private String capacity;
	private String size;
	
	public HardDisk(String model, double price, PricePolicy pricePolicy, Brand brand, String capacity,
			String size) {
		this.model = model;
		this.price = price;
		this.pricePolicy = pricePolicy;
		this.brand = brand;
		this.capacity = capacity;
		this.size = size;
	}

	public void setPrice(double price) { this.price = price; }
	public void setPricePolicy(PricePolicy pricePolicy) { this.pricePolicy = pricePolicy; }
	public String getModel() { return this.model; }
	public double getPrice() { return pricePolicy.getPrice(price); }
	public PricePolicy getPricePolicy() { return this.pricePolicy; }
	public Brand getBrand() { return this.brand; }
	public String getCapacity() { return this.capacity; }
	public String getSize() { return this.size; }

	@Override
	public void print() {
		System.out.println("Model : " + getModel());
		System.out.println("Price : " + getPrice());
		System.out.println("Price Policy : " + getPricePolicy().getFactor());
		System.out.println("Brand : " + getBrand().getBrandName());
		System.out.println("Capacity : " + getCapacity());
		System.out.println("Size : " + getSize());
	}
}

class OpticalDiskDriver implements Part {
	
	private String model;
	private double price;
	private PricePolicy pricePolicy;
	private Brand brand;
	
	public OpticalDiskDriver(String model, double price, PricePolicy pricePolicy, Brand brand) {
		this.model = model;
		this.price = price;
		this.pricePolicy = pricePolicy;
		this.brand = brand;
	}

	public void setPrice(double price) { this.price = price; }
	public void setPricePolicy(PricePolicy pricePolicy) { this.pricePolicy = pricePolicy; }
	public String getModel() { return this.model; }
	public double getPrice() { return pricePolicy.getPrice(price); }
	public PricePolicy getPricePolicy() { return this.pricePolicy; }
	public Brand getBrand() { return this.brand; }

	@Override
	public void print() {
		System.out.println("Model : " + getModel());
		System.out.println("Price : " + getPrice());
		System.out.println("Price Policy : " + getPricePolicy().getFactor());
		System.out.println("Brand : " + getBrand().getBrandName());
	}
}

class Brand {
	
	private String brandName;

	public String getBrandName() { return brandName; }

	public Brand(String brandName) {
		this.brandName = brandName;
	}
}
