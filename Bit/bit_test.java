import java.lang.Math;

/**
 * This is the testing file for the bit class.
 * @author Sheng hao Dong
 * @version 1.0
 */
public class bit_test {
	/**
	 * This is the testing method for the bit class.
	 */
	public void runTest() 
	{
		Impl_Bit bitTesting = new Impl_Bit(); 
		int Expresult; 
		for(int i = 0; i < 2; i++)
		{
			// printing out the current bit. 
			System.out.println("----------------------TEST #" + i + "-------------------");
			System.out.println("Current bit value: " + bitTesting.getValue() + "\n");
			
			// Testing set() method
			bitTesting.set();
			System.out.println("Set() method... \n" + "Expected bit value: 1 \n" + "Current bit value: " + bitTesting.getValue() + "\n");
			
			// Testing toggle() method
			int expec = Math.abs(bitTesting.getValue() - 1);
			bitTesting.toggle();
			System.out.println("toggle() ...\n" + "Expected bit value: " + expec  + "\nCurrent bit value: " + bitTesting.getValue() + "\n");
			
			// Testing clear() method
			bitTesting.clear();
			System.out.println("clear() ...\n" + "Expected bit value: 0\n" + "Current bit value: " + bitTesting.getValue() + "\n");
			
			// Testing set(int value) method
			bitTesting.set(i);
			System.out.println("Set(int value) method... \n" + "Expected bit value: " + i + "\nCurrent bit value: " + bitTesting.getValue() + "\n");
			
			// Testing and(bit other) method and getValue() method
			bit otherBit = new bit();
			otherBit.setValue(1);
			int theResult = bitTesting.and(otherBit).getValue();
			if(i == 0)
			{
				Expresult = 0;
			}else
			{
				Expresult = 1;
			}
			System.out.println("and(bit other)method...\n" + "Current bit value: " + bitTesting.getValue() + "\nother bit value: " + otherBit.getValue() 
			+ "\nExpected result: " + Expresult + "\nActual result: " + theResult + "\n" );
			
			// Testing or(bit other) method 
			theResult = bitTesting.or(otherBit).getValue();
			System.out.println("or(bit other) ...\n" + "Current bit value: " + bitTesting.getValue() + "\nother bit value: " + otherBit.getValue()
			+ "\nExpected result: 1\n" + "Actual result: " + theResult + "\n");
			
			// Testing xor(bit other) method
			theResult = bitTesting.xor(otherBit).getValue();
			if(i == 0)
			{
				Expresult = 1; 
			}else
			{
				Expresult = 0;
			}
			System.out.println("xor(bit other) ...\n" + "Current bit value: " + bitTesting.getValue() + "\nother bit Value: " + otherBit.getValue() 
			+ "\nExpected result: " + Expresult + "\nActual result: " + theResult + "\n");
			
			// Testing not() method 
			System.out.println("not() ...\n" + "Current bit value: " + bitTesting.getValue() + "\nExpected value: " + Math.abs(bitTesting.getValue() - 1) + "\nActual result: " + bitTesting.not().getValue() + "\n");
			
			// Testing toString() method
			String aString = bitTesting.toString();
			System.out.println("toString() ...\n" + "Current bit value(int): " + bitTesting.getValue() + "\nCurrent bit value(string): " + aString + "\n");
		}
		
		
	}
}