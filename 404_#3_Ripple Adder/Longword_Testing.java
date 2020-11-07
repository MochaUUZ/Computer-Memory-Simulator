/**
 * This is the testing file for testing the interface for longword.
 * @author Sheng hao Dong
 *
 */
public class Longword_Testing {
	
	/**
	 * This is the testing method. 
	 */
	public void wordTesting() {
		
		System.out.println("Assignment#2: Longword");
		// to change the test data. Please use number according to the range of int. 
		int[] testData = {45, -500, -528};
		
		// Initializing every data I will need for testing. 
		Impl_Longword hello = new Impl_Longword();
		Impl_Longword mask = new Impl_Longword();
		mask.set(888888);
		bit one = new bit();
		
		// I use a loop to go through every testdata(s).
		for(int i = 0 ; i < testData.length ; i++)
		{			
			System.out.println("---------------------------------------------");
			// Testing set() method. 
			hello.set(testData[i]);
			
			// Testing toString().
			System.out.println("Testing " + i + "\n\nDecimal: " + testData[i] + "\nBinary: " + hello.toString());
			
			// Testing getBit(int i)
			System.out.println("\n1)Testing getBit(int i)");
			System.out.println("Getting the value in index 0...  " + hello.getBit(0).getValue());
			
			// Testing setBit(int i, bit value)
			System.out.println("\n2)Testing setBit(int i, bit value)");
			System.out.println("hello: " + hello.toString());
			System.out.println("~~setting index 0 bit to 1...~~");
			one.setValue(1);
			hello.setBit(0, one);
			System.out.println("hello: " + hello.toString());
			System.out.println("~~changing bit back...~~");
			one.setValue(0);
			hello.setBit(0, one);
			
			// Testing and(longword other) 
			System.out.println("\n3) Testing and(longword other)");
			System.out.println("hello: " + hello.toString());
			System.out.println("other: " + mask.toString() + "\n");
			System.out.print("and:   ");
			hello.printBit(hello.and(mask.original).getArray());
			
			// Testing or(longword other)
			System.out.println("\n4) Testing or(longword other)");
			System.out.println("hello: " + hello.toString());
			System.out.println("other: " + mask.toString() + "\n");
			System.out.print("or:    ");
			hello.printBit(hello.or(mask.original).getArray());
			
			// Testing xor(longword other)
			System.out.println("\n5) Testing xor(longword other)");
			System.out.println("hello: " + hello.toString());
			System.out.println("other: " + mask.toString() + "\n");
			System.out.print("xor:   ");
			hello.printBit(hello.xor(mask.original).getArray());
			
			// Testing not()
			System.out.println("\n6) Testing not()");
			System.out.println("hello: " + hello.toString());
			System.out.print("not:   ");
			hello.printBit(hello.not().getArray());
			
			// Testing rightShift(int amount)
			System.out.println("\n7) Testing rightShift(int amount)");
			System.out.println("hello: " + hello.toString());
			System.out.println("shift right...8");
			System.out.print("hello: ");
			hello.printBit(hello.rightShift(8).getArray());
			
			// Testing leftShift(int amount)
			System.out.println("\n8) Testing leftShift(int amount)");
			System.out.println("hello: " + hello.toString());
			System.out.println("shift left...8");
			System.out.print("hello: ");
			hello.printBit(hello.leftshift(8).getArray());
			
			// Testing getUnsigned()
			System.out.println("\n9) Testing getUnsigned()");
			System.out.println("hello: " + hello.toString());
			System.out.println("Unsigned number: " + hello.getUnsigned());
			
			// Testing getSigned()
			System.out.println("\n10 Testing getSigned()");
			System.out.println("~~Changing first bit to 1...~~\n");
			one.setValue(1);
			hello.setBit(0, one);
			System.out.println("hello: " + hello.toString());
			System.out.println("Signed number: " + hello.getSigned());
			System.out.println("\n~~Changing the first bit back...~~");
			
			
			// Testing copy(longword other)
			System.out.println("\n11 Testing copy(longword other)");
			System.out.println("hello: " + hello.toString());
			System.out.println("mask:  " + mask.toString());
			System.out.println("\nCopying mask to hello...\n");
			hello.copy(mask.original);
			System.out.println("hello: " + hello.toString());
			System.out.println("mask:  " + mask.toString());
			
			// The end, thank you
			System.out.println("\nEnd of Testing.\nThank You!");
		}
	}
}