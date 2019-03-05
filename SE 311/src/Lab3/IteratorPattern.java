package Lab3;
import java.util.ArrayList;
//
//Iterator pattern:
//
//Provide a way to access the elements of an aggregate object
//sequentially without exposing its underlying representation.
//
//The classes and/or objects participating in this pattern are:

//1. Iterator  (AbstractIterator)
//		defines an interface for accessing and traversing elements.
//2. ConcreteIterator  (Iterator)
//		implements the Iterator interface.
//		keeps track of the current position in the traversal of the aggregate.
//3. Aggregate  (AbstractCollection)
//		defines an interface for creating an Iterator object
//4. ConcreteAggregate  (Collection)
//		implements the Iterator creation interface to return an instance of the proper ConcreteIterator
//

public class IteratorPattern {	
	static void printAggregate(AbstractIterator i) {
		System.out.println("\nIterating over collection:");
		for(i.First();  !i.IsDone(); i.Next()) {
			System.out.println(i.CurrentItem().getName());
		}
		System.out.println();
	}
	public static void main(String[] args)  {
		// Create Aggregate.
		AbstractAggregate aggregate = new Collection();
		
		// Create Iterator
		AbstractIterator iterator = aggregate.CreateIterator("odd", 4);;
		
		aggregate.add(new Item("0"));
		aggregate.add(new Item("1"));
		aggregate.add(new Item("2"));
		aggregate.add(new Item("3"));
		aggregate.add(new Item("4"));
		aggregate.add(new Item("5"));
		aggregate.add(new Item("6"));
		aggregate.add(new Item("7"));
		aggregate.add(new Item("8"));
		
		// Traverse the Aggregate.
		printAggregate(iterator);
	}
}

//
//Our concrete collection consists of Items.
//

class Item {
	public Item(String name) { _name = name; }
	public String getName() { return _name;}
	private String _name;
}

//
//This is the abstract "Iterator".
//		AbstractIterator
//

interface  AbstractIterator {
	void First();
	void Next();
	Boolean IsDone () ;
	Item CurrentItem() ;
}

//
//This is the "concrete" Iterator for collection.
//		CollectionIterator
//

class CollectionIterator implements AbstractIterator {
	public void First() {_current = _collection.getIndex();}
	public void Next()  {_current++; }
	public Item CurrentItem() { return (IsDone()?null:_collection.get(_current)); }
	public Boolean IsDone() {	return _current >= _collection.getCount(); }
	public CollectionIterator(Collection collection) {
		_collection = collection; 
		_current = collection.getIndex();
	}
	private Collection _collection;
	private int _current;
}


//
//This is the abstract "Aggregate".
//			AbstractAggregate
//

interface AbstractAggregate {
	public AbstractIterator CreateIterator(String typ, int ind);
	public void add(Item it); 		// Not needed for iteration.
	public int getCount (); // Needed for iteration.
	public Item get(int idx); // Needed for iteration.
}

//
//This is the concrete Aggregate.
//			Collection
//

class Collection implements AbstractAggregate {
	private	 ArrayList<Item> _items = new ArrayList<Item>();
	private String type;
	private int index;
	public	CollectionIterator CreateIterator(String type, int index){
		setType(type);
		setIndex(index);
		return new CollectionIterator(this);
	}
	
	public String getType() { return type; }
	public void setType(String type) { this.type = type; }
	public int getIndex() { return index; }
	public void setIndex(int index) { 
		if(index>= getCount()) {
			throw new ArrayIndexOutOfBoundsException("You should enter a valid index !");
		}
		this.index = index; 
	}
	
	public int getCount () {return _items.size(); }
	public void add(Item item) {
		if(type.equals("odd")) {
			if(Integer.parseInt(item.getName()) % 2 == 0) {
				try { throw new Exception(); } 
				catch (Exception e) { System.out.println(item.getName() + " is invalid number !"
						+ " It should be an odd number..."); }
				return;
			}
		}
		_items.add(item);
	}
	public Item get(int index) { return _items.get(index);}
}