package Lab4;
import java.util.ArrayList;

// This is the "Component". (i.e tree node.)
interface Campus {
	void Add(Campus d);
	void Remove(Campus d);
	void Display(int indent);
	
	public void setID(String id);
	public String getID();
	public void setCapacity(String id, int capacity);
	public int getMax();
}
//This is the "Leaf".
class Classroom implements Campus {
	private String id;
	private int capacity;
	
	public void setID(String id) { this.id = id; }
	public void setCapacity(String id, int capacity) { 
		if(this.id.equals(id))
			this.capacity = capacity;
	}
	public String getID() { return this.id ;}
	public int getCapacity() { return capacity; }
	public int getMax() { return this.capacity; }
	
	public Classroom(String id, int capacity) {this.id = id; this.capacity = capacity; }
	public void Add(Campus c) {
		System.out.println("Cannot add to a classroom.");
	}
	public  void Remove(Campus c) {
		System.out.println("Cannot remove from a classroom.");
	}
	public void Display(int indent) {
		for(int i = 1;i <= indent;i++) 	System.out.print("-");
		System.out.println(" "  + id);
	}
}
// This is the "Composite"
class Composite implements Campus {
	private String id;
	private	ArrayList<Campus> elements = new ArrayList<Campus>();
	
	public void setID(String id) { this.id = id; }
	public String getID() { return this.id; }
	public Composite(String id) { this.id = id; }
	public void Add(Campus d) {elements.add(d);};
	public void Remove(Campus d) {
		for (int i = 0; i< elements.size(); i++) {
			if (elements.get(i).getID().equals(d.getID())) {
				elements.remove(i);
				return;
			}
		}
	}
	public	void Display(int indent) {
		for(int i = 1;i <= indent;i++) System.out.print("-");
		System.out.println( "+ " + getID());

		// Display each child element on this node
		for (int i= 0; i< elements.size(); i++) {
			elements.get(i).Display(indent+2);
		}
	}
	public int getMax() {
		int max=0;
		for(Campus c : elements) {
			if (c.getMax() > max)
				max = c.getMax();
		}
		return max;
	}
	
	public void setCapacity(String id, int capacity) {
		for (int i = 0; i< elements.size(); i++) {
			elements.get(i).setCapacity(id, capacity);
		}
	}
}
//This is the "client"
public class CompositePattern {
	public static void main(String[] args) {
		
		//Campus
		Campus campus = new Composite("Campus");
		
		//Buildings
		Campus EBlock = new Composite("E Block");
		Campus MBlock = new Composite("M Block");
		
		//Floors of "E Block" Building
		Campus floor1 = new Composite("Floor 1");
		floor1.Add(new Classroom("E101", 32));
		floor1.Add(new Classroom("E102", 24));
		
		Campus floor2 = new Composite("Floor 2");
		floor2.Add(new Classroom("E203", 28));
		floor2.Add(new Classroom("M207", 35));
		
		EBlock.Add(floor1);
		EBlock.Add(floor2);
		campus.Add(EBlock);
		
		//Floors of "M Block" Building
		Campus floor3 = new Composite("Floor 3");
		floor3.Add(new Classroom("M304", 68));
		floor3.Add(new Classroom("M305", 65));
		
		Campus floor4 = new Composite("Floor 4");
		floor4.Add(new Classroom("M404", 58));
		floor4.Add(new Classroom("M405", 45));
		
		MBlock.Add(floor3);
		MBlock.Add(floor4);
		campus.Add(MBlock);
		
		campus.Display(1);
		
		System.out.println(campus.getMax());
		campus.setCapacity("M405", 165);
		System.out.println(campus.getMax());
		
	}
}