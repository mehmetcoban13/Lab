package Lab5;
import java.util.ArrayList;

//
//The classes and/or objects participating in this pattern are:
//
//1. Command  (Command)
//		- declares an interface for executing an operation.
//2. ConcreteCommand  (CalculatorCommand)
//		- defines a binding between a Receiver object and an action.
//		- implements Execute by invoking the corresponding operation(s) on Receiver
//3. Client  (Calculator Application)
//		- creates a ConcreteCommand object and sets its receiver.
//4. Invoker  (User)
//		- asks the command to carry out the request
//5. Receiver  (Calculator)
//		- knows how to perform the operations associated with carrying out
//		  a request. Any class may serve as a Receiver.
//
//

//"Command"
//
interface Command {
	public void Execute();
	public void UnExecute();
}
//"ConcreteCommand"
//
/*class CalculatorCommand implements Command {
	
	private Calculator _calculator;
	private char _operator;
	private int _operand;
	
	// Constructor
	public CalculatorCommand(Calculator calculator, char op, int operand) { 
		_calculator = calculator;
		_operator = op;
		_operand = operand;
	}
	public void Execute() {
		_calculator.Action(_operator, _operand);
	}
	public void UnExecute() {
		_calculator.Action(Undo(_operator), _operand);
	}

	// Private helper function. Needed to get the inverse operation.
	private char Undo(char _operator) {
		switch (_operator) {
			case '+': return '-';
			case '-': return '+';
			case '*': return '/';
			case '/': return '*';
			default:  return ' ';
		}
	}
}*/

class Circumference implements Command {
	
	private Calculator _calculator;
	private double radius;
	
	public Circumference(Calculator calculator, double radius) { 
		_calculator = calculator;
		this.radius = radius;
	}
	
	public void Execute() {
		_calculator.Action('*', radius);
	}
	public void UnExecute() {
		_calculator.Action('-', radius);
	}
}

class AdditionCommand implements Command {
	
	private Calculator _calculator;
	private double _operand;
	
	public AdditionCommand(Calculator calculator, double operand) { 
		_calculator = calculator;
		_operand = operand;
	}
	
	public void Execute() {
		_calculator.Action('+', _operand);
	}
	public void UnExecute() {
		_calculator.Action('-', _operand);
	}
}

class SubtractionCommand implements Command {
	
	private Calculator _calculator;
	private double _operand;
	
	public SubtractionCommand(Calculator calculator, double operand) { 
		_calculator = calculator;
		_operand = operand;
	}
	
	public void Execute() {
		_calculator.Action('-', _operand);
	}
	public void UnExecute() {
		_calculator.Action('+', _operand);
	}
}

class MultiplicationCommand implements Command {
	
	private Calculator _calculator;
	private double _operand;
	
	public MultiplicationCommand(Calculator calculator, double operand) { 
		_calculator = calculator;
		_operand = operand;
	}
	
	public void Execute() {
		_calculator.Action('*', _operand);
	}
	public void UnExecute() {
		_calculator.Action('/', _operand);
	}
}

class DivisionCommand implements Command {
	
	private Calculator _calculator;
	private double _operand;
	
	public DivisionCommand(Calculator calculator, double operand) { 
		_calculator = calculator;
		_operand = operand;
	}
	
	public void Execute() {
		_calculator.Action('/', _operand);
	}
	public void UnExecute() {
		_calculator.Action('*', _operand);
	}
}

// "Receiver"
//
class Calculator {
	
	private double current_value;
	
	public Calculator() { 
		current_value = 0; 
	}
	public void Action(char _operator, double operand) {
		switch (_operator) {
			case '+': current_value += operand; break;
			case '-': current_value -= operand; break;
			case '*': current_value *= operand; break;
			case '/': current_value /= operand; break;
		}
		System.out.println("Current value " + current_value + " (following "
				+ _operator + " " + operand + ")");
	}
}

// "Invoker"
class User {
	
	// Initializers.
	private int current;
	private ArrayList<Command> _commands = new ArrayList<Command>();
	
	public User() {	current = 0; }
	
	public void Redo(int levels) {
		System.out.println("\n---- Redo " + levels + " levels ");
		// Perform redo operations
		for (int i = 0; i < levels; i++) {
			if (current < _commands.size()) {
				_commands.get(current++).Execute();
			}
		}
	}

	void Undo(int levels) {
		System.out.println("\n---- Undo " + levels + " levels ");
		// Perform undo operations
		for (int i = 0; i < levels; i++) {
			if (current > 0) {
				_commands.get(--current).UnExecute();
			}
		}
	}
	
	void Compute(Command command) {
		command.Execute();
		// Add command to undo list
		_commands.add(command);
		current++;
	}
}

public class CommandPattern {
	public static void main(String[] args) {

		// Create user and let her compute
		Command command = null;
		User user = new User();
		Calculator calculator = new Calculator();
		
		command = new AdditionCommand(calculator, 100);
		user.Compute(command);
		
		command = new SubtractionCommand(calculator, 50);
		user.Compute(command);
		
		user.Compute(new MultiplicationCommand(calculator, 10));
		
		user.Compute(new DivisionCommand(calculator, 2));

		// Undo 4 commands
		user.Undo(4);
		// Redo 2 commands
		user.Redo(2);
	}
}