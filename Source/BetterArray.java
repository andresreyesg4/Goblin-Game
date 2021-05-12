
// TO DO: add your implementation and JavaDoc

/**
 * BetterArray is used as a generic dynamic array which increases it's capacity when needed more space.
 * In the case where after removing 1/4 of its current capacity, it will shrink to half it's capacity.
 * BetterArray is able to hold any type of data, whether its Integers, Strings, or an Object.
 * Keeps track of its size every time an element is added or appended.
 *
 * @author Andres Reyes.
 * @param <T> Holds the type of any data to be used with a BetterArray.
 */
public class BetterArray<T> {

	/**
	 * DEFAULT_CAPACITY is set to the minimum capacity which is 2.
	 */
	private static final int DEFAULT_CAPACITY = 2;

	/**
	 * data is the underlying array that holds the data of a BetterArray.
	 */
	private T[] data;

	// ADD MORE PRIVATE MEMBERS HERE IF NEEDED!

	/**
	 * holds the number of elements in the data array.
	 */
	private int totalElements; //private member used to keep track of the number of elements in the array.

	/**
	 * Default constructor sets the data's capacity to default which is 2.
	 * Sets totalElements to 0.
	 */
	@SuppressWarnings("unchecked")
	public BetterArray() {
		//constructor.
		//initial capacity of the array should be DEFAULT_CAPACITY.
		data = (T[])new Object[DEFAULT_CAPACITY];
		totalElements = 0;
	}

	/**
	 * Second constructor receives an int as parameter.
	 * checks if it is > 2.
	 * if it is valid, then totalElements is set to 0 and the data's specific capacity.
	 *
	 * @param initialCapacity holds initial capacity of a new BetterArray.
	 */
	@SuppressWarnings("unchecked")
	public BetterArray(int initialCapacity) {
		// constructor
		// set the initial capacity of the smart array as initialCapacity.
		// throw IllegalArgumentException if initialCapacity is smaller than 1.
		if(initialCapacity < 1)
		{
			throw new IllegalArgumentException();
		}else
		{
			data = (T[]) new Object[initialCapacity];
			totalElements = 0;
		}
	}


	/**
	 * Used to report the current value of totalElements.
	 * which holds the number of elements inside the BetterArray.
	 *
	 * @return the number of elements in the BetterArray.
	 */
	public int size() {
		//report number of elements in the smart array.
		// O(1).
		return totalElements;
	}

	/**
	 * The capacity method is used to report the current capacity of the BetterArray.
	 * Capacity is the value of the data array's max length before the next resize of the BetterArray.
	 *
	 * @return the data's max length.
	 */
	public int capacity() {
		//report max number of elements before the next expansion.
		// O(1).
		if(data.length == DEFAULT_CAPACITY)
		{
			return DEFAULT_CAPACITY;
		}else
		{
			return data.length;
		}
	}

	/**
	 * The append method is used to return true if an element is added at the end of the BetterArray.
	 * It returns false if there was an error appending an element.
	 * totalElements is increased by one when an element is appended.
	 *
	 * @param value holds the data to be appended.
	 * @return true if appending at the end was successful, or false if not.
	 */
	@SuppressWarnings("unchecked")
	public boolean append(T value) {
		// add an element to the end.
		// return true.
		// double capacity if no space available.
		// amortized O(1).

		// boolean successful = true;
		if(totalElements <= data.length){
			if(data[totalElements - 1] != null && totalElements == data.length){
				// it is at full capacity.
				T[] newData = (T[]) new Object[data.length * 2];
				System.arraycopy(data, 0, newData, 0, data.length);
				newData[totalElements] = value;
				data = newData;
				totalElements++;
				return true;
			}else if(data[totalElements] == null){
				data[totalElements] = value;
				totalElements++;
				return true;
			}
		}

		return false;
		// int index = -1;
		// for(int i = 0; i < data.length; i++)
		// {
		// 	if(data[i] == null)
		// 	{
		// 		index = i;
		// 		break;
		// 	}
		// }
		// // index will equal to -1 when there is an empty spot available in the array.
		// if(index ==-1)
		// {
		// 	T[] newData = (T[]) new Object[data.length*2];
		// 	System.arraycopy(data, 0, newData, 0, data.length);
		// 	newData[data.length] = value;
		// 	data = newData;
		// 	totalElements++;    //increment the number of elements.
		// 	successful = true;
		// }else
		// {
		// 	data[index] = value;
		// 	totalElements++;    //increment the number of elements.
		// 	successful = true;
		// }

		// return successful;
	}

	/**
	 * This method adds an element at a specific index.
	 * If the BetterArray is full, it will double it's capacity and add the element.
	 * If the element is added in the middle or any sport other that the end.
	 * then the elements will be shifter over to the right.
	 *
	 * @param index holds the place to add the element in.
	 * @param value holds the data to add in the index of the BetterArray.
	 */
	@SuppressWarnings("unchecked")
	public void add(int index, T value) {
		// insert value at index, shift elements if needed.
		// double the capacity if no space is available.
		// throw IndexOutOfBoundsException for invalid index.
		// O(N) where N is the number of elements in the array.

		// Note: this method may be used to append items as.
		// well as insert items.

		if((index > data.length) || (index < 0)){
			throw new IndexOutOfBoundsException();

		}else if(value != null){
			//checks if the last index of the BetterArray is not empty.
			//if it is not empty, then double it and add the element.
			if(data[data.length-1] != null){
				ensureCapacity(data.length*2);
			}
			
			// shift over the elements of the array if the spot to insert is being used. 
			if(data[index] != null){ 
				int i = index;
				while(i < totalElements && data[i] != null){
					data[i + 1] = data[i];
					i++;
				}
			}
			// add the element to the specified index.
			data[index] = value;
			totalElements++;



			// //if its not full, then simply just add the element and shift the items over.
			// for(int i = data.length-1; i > index; i--){
			// 	data[i] = data[i-1];
			// }
			// data[index] = value;
			// totalElements++;    //increment the total number of elements.
		}
	}

	/**
	 * This method returns the value at which index is desired.
	 *
	 * @param index place of which value is needed to be returned.
	 * @return The value at the specific index.
	 */
	public T get(int index){
		// return the item at index.
		// throw IndexOutOfBoundsException for invalid index.
		// O(1).

		if((index >= data.length) || (index < 0)){ //replace data.length with totalElements.
			throw new IndexOutOfBoundsException();
		}else{
			return data[index];
		}
	}

	/**
	 * The replace method simply replaces a value at a specific index with a new value.
	 * After the replacement is finished, the method saves the previous value and returns it.
	 * The total number of elements does not increase, since we do not add elements with this method.
	 *
	 * @param index holds the place of where to replace in the BetterArray.
	 * @param value holds the element to replace the other element with.
	 * @return the element that was replaced.
	 */
	public T replace(int index, T value){
		// change item at index to be value	.
		// return old item at index.
		// throw IndexOutOfBoundsException for invalid index.
		// O(1).

		// Note: you cannot add new items with this method.

		T deletion = null;
		if((index >= data.length) || (index < 0)){
			throw new IndexOutOfBoundsException();
		}else{
			//save the element to a variable before deleting.
			deletion = data[index];
			data[index] = value;
		}
		return deletion;
	}

	/**
	 * delete method is use to delete an element at a given index.
	 * After deletion, the method returns the element deleted.
	 * It also checks if the number of elements falls at 1/4 of the capacity.
	 * then it shrinks the capacity to half.
	 *
	 * @param index holds the place of the element to be deleted.
	 * @return the element that was deleted.
	 */
	@SuppressWarnings("unchecked")
	public T delete(int index){
		// remove and return element at position index.
		// shift elements to remove any gap in the array.
		// throw IndexOutOfBoundsException for invalid index.

		// halve capacity if the number of elements falls below 1/4 of the capacity.
		// capacity should NOT go below DEFAULT_CAPACITY.

		// O(N) where N is the number of elements in the list.
		T deletion;

		if(index >= data.length || index < 0 ){
			throw new IndexOutOfBoundsException();
		}else{
			deletion = data[index]; //save the element before deleting.
			data[index] = null;
			totalElements--;    //decrement the total number of elements.

			if(totalElements <= data.length/4){
				//if the total elements is <= 1/4 of capacity.
				T[] newArray = (T[]) new Object[data.length/2];
				System.arraycopy(data, 0, newArray, 0, newArray.length);
				data = newArray;
			}

			for(int i = index; i < data.length; i++){
				// shift over the elements if the deleted is before the end.
				if(((i+1) != data.length) && (data[i+1] != null))
				{
					data[i]= data[i+1];
					data[i+1] = null;
				}
			}
		}
		return deletion;
	}

	/**
	 * This method returns the index at which the value is first found.
	 *
	 * @param value contains the value the method is searching for.
	 * @return the index where the value was found.
	 */
	public int firstIndexOf(T value){
		// return the index of the first occurrence or -1 if not found.
		// O(n).
		int index = 0;
		int occurrence = -1;
		while(index < data.length){
			if(data[index] == value)
			{
				occurrence = index;
			}
			index++;
		}
		return occurrence;
	}

	/**
	 * This method returns true the new capacity passed in is applied, false otherwise.
	 *
	 * @param newCapacity holds the new capacity of the array.
	 * @return true if successful, false otherwise.
	 */
	@SuppressWarnings("unchecked")
	public boolean ensureCapacity(int newCapacity){
		// change the max number of items allowed before next expansion to newCapacity.

		// capacity should not be changed if:
		//   - newCapacity is below DEFAULT_CAPACITY; or ,
		//   - newCapacity is not large enough to accommodate current number of items.

		// return true if newCapacity gets applied; false otherwise.
		// O(N) where N is the number of elements in the array.

		boolean applied = false;

		if((newCapacity < 2) || (newCapacity < totalElements)){
			return applied;
		}else{
			T[] newData = (T[]) new Object[newCapacity];
			System.arraycopy(data, 0, newData, 0, totalElements);
			// for(int i = 0; i < data.length; i++)
			// {
			// 	newData[i] = data[i];
			// }
			data = newData;
			applied = true;
		}
		return applied;
	}

	/**
	 * This method simply makes a new reference to a BetterArray and copies the data's elements.
	 * and returns that new BetterArray,
	 * @return a copy of the data array.
	 */
	public BetterArray<T> clone() {
		//make a copy of all the current values.
		//don't forget to set the capacity!
		//O(n).
		BetterArray<T> newArray = new BetterArray<>(data.length);
		if(data != null){
			for(int i = 0; i < data.length; i++){
				newArray.add(i, data[i]);
			}
		}else{
			for(int i = 0; i< data.length; i++){
				newArray.append(data[i]);
			}
		}
		return newArray;
	}


	/**
	 * main is used to test all the methods written above.
	 * @param args holds command line arguments.
	 */
	// --------------------------------------------------------
	// example testing code... edit this as much as you want!
	// --------------------------------------------------------
	public static void main(String args[]) {
		//create a smart array of integers
		BetterArray<Integer> nums = new BetterArray<>();
		if ((nums.size() == 0) && (nums.capacity() == 2)){
			System.out.println("Yay 1");
		}

		//append some numbers
		for (int i=0; i<3;i++)
			nums.add(i,i*2);

		if (nums.size()==3 && nums.get(2) == 4 && nums.capacity() == 4 ){
			System.out.println("Yay 2");
		}

		//create a smart array of strings
		BetterArray<String> msg = new BetterArray<>();

		//insert some strings
		msg.add(0,"world");
		msg.add(0,"hello");
		msg.add(1,"new");
		msg.append("!");

		if (msg.get(0).equals("hello") && msg.replace(1,"beautiful").equals("new")
				&& msg.size() == 4 && msg.capacity() == 4 ){
			System.out.println("Yay 3");
		}

		//change capacity
		if (!msg.ensureCapacity(0) && !msg.ensureCapacity(3) && msg.ensureCapacity(20)
				&& msg.capacity() == 20){
			System.out.println("Yay 4");
		}

		//delete and shrinking
		if (msg.delete(1).equals("beautiful") && msg.get(1).equals("world")
				&& msg.size() == 3 && msg.capacity() == 10 ){
			System.out.println("Yay 5");
		}

		//firstIndexOf and clone
		//remember what == does on objects... not the same as .equals()
		BetterArray<String> msgClone = msg.clone();
		if (msgClone != msg && msgClone.get(1) == msg.get(1)
				&& msgClone.size() == msg.size()
				&& msgClone.capacity() == msg.capacity()
				&& msgClone.firstIndexOf("world") == 1
				&& msgClone.firstIndexOf("beautiful") == -1) {
			System.out.println("Yay 6");
		}
	}

	// --------------------------------------------------------
	// DO NOT EDIT ANYTHING BELOW THIS LINE (except to add JavaDocs)
	// --------------------------------------------------------

	/**
	 * this method is used to print the BetterArray in an efficient way.
	 * @return a string with all the data in the BetterArray.
	 */
	public String toString() {
		if(size() == 0) return "";

		StringBuffer sb = new StringBuffer();
		sb.append(get(0));
		for(int i = 1; i < size(); i++){
			sb.append(", ");
			sb.append(get(i));
		}
		return sb.toString();
	}
}
