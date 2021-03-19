/**
 * This is the driver method which will starts the testing file.
 * @author mochauuz
 *
 */
public class Driver {
	
	/**
	 * This is the method which contains the command line argument that will start the java application.
	 * @param args the command that will starts the java application.
	 */
	public static void main(String[] args)
	{
		// I create an instance of the ALU_Test which is a test file for java.
		ALU_Test hi = new ALU_Test();
		
		// I go into the file and call the method call runTest() which is where all the testing is done. 
		hi.runTest();
	}

}
