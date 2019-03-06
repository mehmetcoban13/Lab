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
		System.out.println("Iterating over collection:");
		for(i.First();  !i.IsDone(); i.Next()) {
			System.out.println(i.CurrentItem().getName());
		}
		System.out.println();
	}
	
	public static void main(String[] args) {
		// Create Aggregate.
		AbstractAggregate aggregate = new Collection();
		aggregate.add(new Item("Item 0"));
		aggregate.add(new Item("Item 1"));
		aggregate.add(new Item("Item 2"));
		aggregate.add(new Item("Item 3"));
		aggregate.add(new Item("Item 4"));
		aggregate.add(new Item("Item 5"));
		aggregate.add(new Item("Item 6"));
		aggregate.add(new Item("Item 7"));
		aggregate.add(new Item("Item 8"));
		
		// Create Iterator
		AbstractIterator iterator = aggregate.CreateIterator("regular", 0);
		
		AbstractIterator iterator2 = aggregate.CreateIterator("odd", 1);
		
		// Traverse the Aggregate.
		printAggregate(iterator);
		printAggregate(iterator2);
	}
}

//
//Our concrete collection consists of Items.
//

class Item {
	public Item(String name) { _name = name; }
	public String getName() { return _name; }
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
	private int index;
	private String type;
	private Collection _collection;
	private int _current;
	
	public void First() {
		_current = index;
	}
	public void Next()  {
		if(type.equals("odd"))
			_current +=2;
		else if(type.equals("regular"))
			_current++;
	}
	public Item CurrentItem() { return (IsDone()?null:_collection.get(_current)); }
	public Boolean IsDone() {	return _current >= _collection.getCount(); }
	public CollectionIterator(Collection collection, String type, int index) {
		_collection = collection;
		this.type = type;
		this.index = index;
		_current = index;
	}
}


//
//This is the abstract "Aggregate".
//			AbstractAggregate
//

interface AbstractAggregate {
	public AbstractIterator CreateIterator(String type, int index);
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
	
	public	CollectionIterator CreateIterator(String type, int index) {
		if(index >= getCount() || index<0) {
			throw new ArrayIndexOutOfBoundsException("Invalid Index !");
		}
		if(type.equals("odd") && index%2 == 0) {
			throw new NumberFormatException();
		}
		return new CollectionIterator(this, type, index);
	}
	
	public int getCount () {return _items.size(); }
	public void add(Item item) {_items.add(item);}
	public Item get(int index) { return _items.get(index);}
}