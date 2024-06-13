
public class Stack {
	private int top;
	private Object[] elements;
	
	Stack(int size) {	
		elements = new Object[size];
		top = -1;
	}
	
	public void push(Object element) {
		if(!isFull()) {
			top++;
			elements[top] = element;
		}
		else {
			System.out.println("Stack overflow");
		}
	}
	
	public Object pop() {
		if(!isEmpty()) {
			Object temp = elements[top];
			elements[top] = null;
			top--;			
			return temp;
		}
		else {
			System.out.println("There is no element in stack");
			return null;
		}		
	}
	
	public Object peek() {
		if(!isEmpty()) {
			return elements[top];
		}		
		else {
			System.out.println("There is no element in stack");
			return null;
		}
	}
	
	public int size() {
		return top + 1;
	}
		
	public boolean isEmpty() {
		return (top == -1);
	}
	
	public boolean isFull() {
		return (top == elements.length - 1);
	}
}
