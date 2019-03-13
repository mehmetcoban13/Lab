package Lab4;
import java.util.ArrayList;

// This is the "Component". (i.e tree node.)
interface CarPart {
	public String getName();
	public double getPrice();
	void Display(int indent);
	public double totalPrice();
}
//This is the "Leaf".
class Leaf implements CarPart {
	private String name;
	private double price;
	
	public void setPrice(double price) { this.price = price; }
	public String getName() { return name;}
	public double getPrice() { return price; }
	
	public Leaf(String name, double price) {
		this.name = name;
		this.price = price;
	}
	public void Display(int indent) {
		for(int i = 1;i <= indent;i++) 	System.out.print("-");
		System.out.println(" "  + name);
	}
	@Override
	public double totalPrice() { return price; }
}
// This is the "Composite"
class Composite implements CarPart {
	private	ArrayList<CarPart> elements = new ArrayList<CarPart>();
	private String name;
	private double price;
	
	public String getName() { return name;}
	public double getPrice() { 
		for(CarPart cP : elements) { price += cP.totalPrice(); }
		return price;
	}
	
	public Composite(String name) { this.name = name; price = 0.0; }
	public void Add(CarPart d) {elements.add(d); }
	public void Remove(CarPart d) {
		for (int i = 0; i< elements.size(); i++) {
			if (elements.get(i).getName() == d.getName()) {
				elements.remove(i);
				return;
			}
		}
	}
	public	void Display(int indent) {
		for(int i = 1;i <= indent;i++) System.out.print("-");
		System.out.println( "+ " + getName());

		// Display each child element on this node
		for (int i= 0; i< elements.size(); i++) {
			elements.get(i).Display(indent+2);
		}
	}
	@Override
	public double totalPrice() {
		return getPrice();
	}
}
//This is the "client"
public class CompositePattern {
	public static void main(String[] args) {
	
		// Create a tree structure
		CarPart car = new Composite("Car");
		Composite carParts = new Composite("Car Parts");
		carParts.Add(new Leaf("Red Line", 5));
		carParts.Add(new Leaf("Blue Circle", 5));
		carParts.Add(new Leaf("Green Box", 5));

		Composite comp = new Composite("Two Circles");
		comp.Add(new Leaf("Black Circle", 5));
		comp.Add(new Leaf("White Circle",5));
		carParts.Add(comp);
		
		((Composite) car).Add(carParts);
		
		// Recursively display nodes
		car.Display(1);
		System.out.println(car.totalPrice());
	}
}