/**
 * This is the file that will do all the testing for the ALU
 * @author Sheng hao Dong
 *
 */
public class ALU_Test {
	
	/**
	 * This is the method that will do all the testing for the ALU.
	 */
	public void runTest()
	{
		//Initialize  the data I will be using. 
		long[] testData = {1000, 1001, 1010, 1011, 1100, 1101, 1110, 1111, 111};
		Impl_Longword longword1 = new Impl_Longword();
		Impl_Longword longword2 = new Impl_Longword();
		 
		// Change the test data here. firstT is A, and secondT is B.
		int[] firstT = {200, -10};
		int[] secondT = {-3, 555};
		int shiftAmount = 3;
		
		// The loop  which will go over each ALU's operator for each test data and print it out aesthetically.  
		for(int j = 0; j < firstT.length; j++)
		{
			System.out.println("\n-----------------------------------------------------\n");
			System.out.println("Test " + j + "\nA: " + firstT[j] + "\nB: " + secondT[j]);
		for(int i = 0; i < testData.length; i++)
		{
			longword1.set(firstT[j]);
			longword2.set(secondT[j]);
			if(i == 4 )
			{
				longword2.set(shiftAmount);
				System.out.println("left shift: " + shiftAmount);
				longword1.printBit(ALU.doOp(intTobinary(testData[i]), longword1.original, longword2.original).getArray());
			}else if(i == 5)
			{
				longword2.set(shiftAmount);
				System.out.println("right shift: " + shiftAmount);
				longword1.printBit(ALU.doOp(intTobinary(testData[i]), longword1.original, longword2.original).getArray());
			}else
			{
			longword1.printBit(ALU.doOp(intTobinary(testData[i]), longword1.original, longword2.original).getArray());
			}
			System.out.println();
		}
		}
	}
	
	/**
	 * this is my helper method which will put each digits of a number in an index of array, when look at the 
	 * array as a whole, it will be the same number.
	 * @param theInt this is the number I want to change into array format.
	 * @return a array format of "bit" which will be the exact representation of the number.
	 */
	public bit[] intTobinary(long theInt)
	{
		bit[] result = new bit[4];
		long holder = theInt;

		for(int i = 3; i >= 0; i--)
		{
			bit lastDigit = new bit();
			lastDigit.setValue((int)holder%10);
			result[i] = lastDigit;
			holder = holder / 10;
		}
		return result;
	}

}
