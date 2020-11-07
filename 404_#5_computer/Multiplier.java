/**
 * This the is class that implement multiple of binary number.
 * @author Sheng hao Dong
 *
 */
public class Multiplier extends rippleAdder {
	
	/**
	 * This method do the multiplication operator to two longword
	 * @param a the first longword
	 * @param b the second longword
	 * @return a longword representation of the multiplication result.
	 */
	public static longword multiply(longword a, longword b)
	{
		// placeholder is the "zero" when we do the traditional way of multiplication in paper.
		int placeHolder = 0;
		
		// We will keep on adding the longword "adding" to result until there is no more digits in "b".
		Impl_Longword result = new Impl_Longword();
		Impl_Longword adding = new Impl_Longword();
		
		// I first initialize the result to 64 bit array, all the value is zero. Ready to be add!
		result.original.setArray(result.fillbit());;
		
		//This loop goes over all the bit in b, and only adds when it encounters a value of "1". After each loop, placeholder is add 1 no matter what. 
		for(int i = b.getArray().length-1; i>= 0; i--)
		{
			if(b.getArray()[i].getValue() == 0)
			{
				// do nothing.
			}
			else if(b.getArray()[i].getValue() == 1)
			{
				adding.original.setArray(a.getArray());
				result.original = rippleAdder.add(result.original, adding.leftshift(placeHolder));
			}
			
			placeHolder++;
		}
		return result.original;
	}
}