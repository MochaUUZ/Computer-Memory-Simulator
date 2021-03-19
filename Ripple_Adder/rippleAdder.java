/**
 * This class implement the architecture for ripple adder.
 * @author Sheng hao Dong
 *
 */
public class rippleAdder {
	/**
	 * This is the method for adding two longword.
	 * @param a The number that will be added from
	 * @param b The number that will be added to
	 * @return A longword representation of the result
	 */
	public static longword add(longword a, longword b)
	{
		//Declare all the storage I will be using
		Impl_Bit bitA = new Impl_Bit();
		Impl_Bit bitB = new Impl_Bit();
		Impl_Bit tempS = new Impl_Bit();
		Impl_Bit tempCin = new Impl_Bit();
		Impl_Bit tempCin2 = new Impl_Bit();
		
		// Storage for my answer(the S) and carry (the C-in and C-out)
		bit[] answer = new bit[32];
		bit carry = new bit();
		carry.setValue(0);
		
		// The loop for performing ripple adder to each bit of longword starting from the right to left.
		for(int i = a.getArray().length - 1; i >= 0; i--)
		{
			bitA.set(a.getArray()[i].getValue());
			bitB.set(b.getArray()[i].getValue());
			tempS.bitHolder = bitA.xor(bitB.bitHolder);
			answer[i] = tempS.xor(carry);
			
			tempS.bitHolder = bitA.and(bitB.bitHolder);
			tempCin.bitHolder = bitA.xor(bitB.bitHolder);
			tempCin2.bitHolder = tempCin.and(carry);
			carry = tempS.or(tempCin2.bitHolder);
		}
		
		longword result = new longword();
		result.setArray(answer);
		return result;
	}
	
	/**
	 * This is the method that will be use to subtract two number. The logic here is to rewrite subtraction problem 
	 * in the form of addition by reversing the number that will be subtracting. 
	 * @param a The number that will be subtract from.
	 * @param b The number that will be subtracting to. 
	 * @return A longword representation of the result
	 */
	public static longword subtract(longword a, longword b)
	{
		// operation to reversing the second number. 
		Impl_Longword changeB = new Impl_Longword();
		changeB.original = b;
		int num = changeB.getSigned() - 2 * changeB.getSigned();
		changeB.set(num);
		
		// Call the addition method.
		return add(a,changeB.original);
	}
}
