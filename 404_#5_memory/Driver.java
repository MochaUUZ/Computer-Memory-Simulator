/**
 * This is the driver class for running the testing of memory class.
 * @author Sheng hao Dong
 *
 */
public class Driver {
	/**
	 * This is the driver method for running the testing of memory class.
	 * @param args A string[] which will command to run the java application.
	 * @throws Exception This happen when the index is out of bound.
	 */
	public static void main(String[] args) throws Exception
	{
		memory_test hello = new memory_test();
		hello.run_tests();
	}

}
