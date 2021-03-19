/**
 * This is the testing file for the implementation of multiplier.
 * @author Sheng hao Dong
 *
 */
public class multiplier_test {
	
	/**
	 * This is the method to test the implementation of multiplier.
	 */
	public void runTests()
	{
		// the test data
		int[] testData1 = {	200, -170,  192, -2};
		int[] testData2 = {	3, -256, -738, 1};
		
		Impl_Longword theA = new Impl_Longword();
		Impl_Longword theB = new Impl_Longword();
		Impl_Longword result = new Impl_Longword();
		
		for(int i = 0; i < testData1.length ; i++)
		{
		System.out.println("Now testing:" + testData1[i] + " x " + testData2[i]);
		theA.set(testData1[i]);
		theB.set(testData2[i]);
		result.original = Multiplier.multiply(theA.original, theB.original);
		System.out.println("Result: " + result.toString());
		System.out.println("Result: " + result.getSigned() + "\n");
		}
	}
}
