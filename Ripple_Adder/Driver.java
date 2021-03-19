/**
 * This is the driver class for the ripple adder program.
 * @author Sheng hao Dong
 *
 */
public class Driver {
	
	/**
	 * This is the method that call the argument to start the program
	 * @param args The command to start the program
	 */
	public static void main(String[] args) {
		// Assignment 3
		rippleAdder_Test start = new rippleAdder_Test();
		start.runTest();

		// Assignment 2
		Longword_Testing start1 = new Longword_Testing();
		start1.wordTesting();
		
		// Assignment 1
		bit_test start2 = new bit_test();
		start2.runTest();
	}
}