/**
 * This is the testing file for the ripple adder class.
 * @author Sheng hao Dong
 *
 */
public class rippleAdder_Test {

	/**
	 * This the method that will hold all the testing for ripple adder.
	 */
	public void runTest()
	{
		// The testing data 
		int[] test_data = 	{550,	-355,	1028,	-5500	};
		int[] test_data2 = 	{50	,	-45,	-528,	500		};
		
		// The storage created for testing
		Impl_Longword longA = new Impl_Longword(); 
		Impl_Longword longB = new Impl_Longword();
		Impl_Longword result = new Impl_Longword();
		bit[] testing = new bit[32];
		
		System.out.println("Assignment: Ripple Adder\nAdd\n");
		// The loop for testing the add method
		for(int i = 0; i < test_data.length; i++)
		{
			longA.set(test_data[i]);
			System.out.println("  " + longA.toString() + "  (" + longA.getSigned() + ")");
			System.out.print("+ ");
			longB.set(test_data2[i]);
			System.out.println(longB.toString() + "  (" + longB.getSigned() + ")");
			System.out.println("______________________________________");
			testing = rippleAdder.add(longA.original, longB.original).getArray();
			System.out.print("  ");
			result.original.setArray(testing);
			System.out.println(result.toString() + "  (" + result.getSigned() + ")");
			System.out.println();
		}
		
		System.out.println("\n\nSubtract");
		
		// The loop for testing the subtract method
		for(int j = 0; j < test_data.length; j++)
		{
			longA.set(test_data[j]);
			System.out.println("  " + longA.toString() + "  (" + longA.getSigned() + ")");
			System.out.print("- ");
			longB.set(test_data2[j]);
			System.out.println(longB.toString() + "  (" + longB.getSigned() +")");
			System.out.println("______________________________________");
			testing = rippleAdder.subtract(longA.original, longB.original).getArray();
			System.out.print("  ");
			result.original.setArray(testing);
			System.out.println(result.toString() + "  (" + result.getSigned() + ")");
			System.out.println();
		}
	}
}