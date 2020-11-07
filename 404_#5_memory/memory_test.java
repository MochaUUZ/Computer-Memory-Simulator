/**
 * This is the test file for the memory. This will test for the 
 * read and write method of memory.
 * @author Sheng hao Dong
 */
public class memory_test {
	
	/**
	 * This is the method which will run the testing for the memory.
	 * @throws Exception This will happen when the index is out of bound of memory.
	 */
	public void run_tests() throws Exception
	{
		// Initialize the variable I will need for testing
		memory testing = new memory();
		Impl_Longword allOne = new Impl_Longword();
		allOne.set(-1);
		int[] testData = {0, 55, 235, 255} ;
		
		// Initialize the memory to all 0's
		testing.initilize();
		
		// number will be the longword rep. of index.
		Impl_Longword number = new Impl_Longword();
		
		// result will hold the returning from the method.
		longword result = new longword();
		
		for(int i = 0; i < testData.length; i++)
		{
			// This loop will try to read from the memory from an index
			// Then will write to that index
			// Then will print the same index to see if the data is changed. 
			System.out.println("-----------------------------------------\n");
			System.out.println("Printing longword starting at bit index: " + (testData[i]*32) );
			number.set(testData[i]);
			result = testing.read(number.original);
		
			printBit(result.getArray());
			
			System.out.println("Now changing longword starting at bit index: " + (testData[i]*32) + " to all 1's\n");
			testing.write(number.original, allOne.original);
			
			System.out.println("Printing longword starting at bit index: " + (testData[i]*32));
			result = testing.read(number.original);
			printBit(result.getArray());
		}
	}

	/**
	 * This is a helper method i created to help me print out the longword.
	 * @param bitA A bit array which exist inside the longword class.
	 */
	public static void printBit(bit[] bitA)
	{
		// A simply loop that goes around the array. 
		for(int i = 0; i < bitA.length; i++)
		{
			System.out.print(bitA[i].getValue() + ", ");
		}
		System.out.println("\n");
	}
}