/**
 * This is the implementation file for the interface "ILongword.java"
 * @author Sheng hao dong
 *
 */
public class Impl_Longword implements ILongword {
	// This will serve as the main storage for longword
	longword original = new longword();
	
	/**
	 * getBit(int i)
	 * this method return the value of the bit located in i'th index of the array.
	 * @param i the index of the array
	 * @return he bit at index i
	 */
	public bit getBit(int i) {
		return original.getArray()[i];
	}

	/**
	 * setBit(int i, bit value)
	 * this method set the bit, located at index i to another bit
	 * @param i the index of the array
	 * @param value the bit that will be replacing
	 */
	public void setBit(int i, bit value) {
		
		bit other[] = original.getArray();
		other[i].setValue(value.getValue());
		original.setArray(other);
	}

	/**
	 * and(longword other)
	 * This method perform the and operation to two longword.
	 * @param other this is the other longword that will be comparing
	 * @return a new longword that is the result of the and operation 
	 */
	public longword and(longword other) {
		Impl_Bit ff = new Impl_Bit();
		int index = 0;
		bit holder[] = fillbit();
		
		
		while(index <= original.getArray().length -1)
		{
			ff.set(original.getArray()[index].getValue());
			bit temp = ff.and(other.getArray()[index]);
			holder[index] = temp;
			index++;
		}
		longword result = new longword();
		result.setArray(holder);
		
		return result;
	}

	/**
	 * or(longword other)
	 * This method perform the or operation to two longword.
	 * @param other this is the other longword that will be comparing
	 * @return a new longword that is the result of the or operation 
	 */
	public longword or(longword other) {
		
		Impl_Bit ff = new Impl_Bit();
		int index = 0;
		bit holder[] = fillbit();
		
		while(index < original.getArray().length)
		{
			ff.set(original.getArray()[index].getValue());
			bit temp = ff.or(other.getArray()[index]);
			holder[index] = temp;
			index++;
		}
		longword result = new longword();
		result.setArray(holder);
		
		return result;
	}

	/**
	 * xor(longword other)
	 * This method perform the xor operation to two longword.
	 * @param other this is the other longword that will be comparing
	 * @return a new longword that is the result of the xor operation 
	 */
	public longword xor(longword other) {
		Impl_Bit ff = new Impl_Bit();
		int index = 0;
		bit holder[] = fillbit();
		
		while(index < original.getArray().length)
		{
			ff.set(original.getArray()[index].getValue());
			bit temp = ff.xor(other.getArray()[index]);
			holder[index] = temp;
			index++;
		}
		longword result = new longword();
		result.setArray(holder);
		
		return result;
	}

	/**
	 * not()
	 * This method perform the not operation to a longword.
	 * @return a new longword that is the result of the not operation 
	 */
	public longword not() {
		Impl_Bit ff = new Impl_Bit();
		int index = 0;
		bit holder[] = fillbit();
		
		while(index < original.getArray().length)
		{
			ff.set(original.getArray()[index].getValue());
			holder[index] = ff.not();
			index++;
		}
		longword result = new longword();
		result.setArray(holder);
		
		return result;
	}

	/**
	 * rightShift(int amount)
	 * this method perform the right shift operation to a longword
	 * @param amount the amount of shift 
	 * @return a new longword that is the result of the shift operation
	 */
	public longword rightShift(int amount) {
		bit[] holder = fillbit();
		bit replace = new bit();
		replace.setValue(0);
		
		for(int i = 0; i < amount; i++)
		{
			holder[i] = replace;
		}
		
		int hello = 0;
		for(int j = amount ; hello < original.getArray().length - amount; j++)
		{
			holder[j] = original.getArray()[hello];
			hello++;
		}
		longword result = new longword();
		result.setArray(holder);
		
		return result;
	}

	/**
	 * leftshift(int amount)
	 * this method perform the left shift operation to a longword
	 * @param amount the amount of shift 
	 * @return a new longword that is the result of the shift operation
	 */
	public longword leftshift(int amount) {
		bit[] holder = fillbit();
		bit replace = new bit();
		replace.setValue(0);
		
		for( int i = holder.length-1; i >= holder.length-amount ;i-- )
		{
			holder[i] = replace;
		}
		
		int hello = original.getArray().length-1;

		for (int j = holder.length-amount-1; j >= 0 ; j--)
		{
			holder[j] = original.getArray()[hello];
			hello--;
		}
		longword result = new longword();
		result.setArray(holder);
		
		return result;
	}

	/**
	 * getUnsigned()
	 * This method return the decimal representation of the longword in term of unsigned number.
	 * @return the unsigned number of the binary number in term of long datatype. 
	 */
	public long getUnsigned() {
		bit [] binaryN = original.getArray();
		long decimal = 0;
		int p = 0;
		
		for(int i = binaryN.length-1 ; i >= 0 ; i--)
		{
			int temp = binaryN[i].getValue();
			decimal = (long) (decimal + temp * Math.pow(2, p));
			p++;
		}
		return decimal;
	}

	/**
	 * getSigned()
	 * This method return the decimal representation of the longword in term of signed number.
	 * @return the signed number of the binary number in term of int datatype. 
	 */
	public int getSigned() {
		bit[] binaryN = original.getArray();
		int decimal = 0;
		int p = 0;
		if(binaryN[0].getValue() == 0)
		{
			decimal = (int) getUnsigned();
		}else if(binaryN[0].getValue() == 1)
		{
			bit[] mirror = this.not().getArray();
			for(int i = mirror.length-1 ; i >= 0; i--)
			{
				int temp = mirror[i].getValue();
				decimal = (int) (decimal + temp * Math.pow(2, p));
				p++;
			}
			decimal++;
			decimal = decimal - decimal * 2;
		}
		
		return decimal;
	}

	/**
	 * copy(longword other)
	 * This method copy one longword into another longword
	 * @param other the longword that will be copy to another longword
	 */
	public void copy(longword other) {
		original.setArray(other.getArray());
	}

	/**
	 * set(int value)
	 * This method will initialize the longword.
	 * @param value this is the value that will set the longword to
	 */
	public void set(int value) {
		boolean positive = true;
		bit holder[] = fillbit();
		int pushing;
		int hLength = holder.length-1;
		
		if(value >= 0)
		{
			positive = true;
		}else if(value < 0)
		{
			positive = false;
			value = Math.abs(value) - 1 ;
		}
		
		while(value > 0)
		{
		pushing = value % 2;
		holder[hLength].setValue(pushing);
		value = value / 2;
		hLength--;
		}
		
		if(positive == false)
		{
			for(int i = 0; i < holder.length ; i++)
			{
				holder[i].setValue(Math.abs(holder[i].getValue() - 1));
			}
		}
		
		original.setArray(holder);
	}
	
	/**
	 * toString()
	 * This method will return the longword representation in term of string.
	 * @return a string representation of longword.
	 */
	public String toString()
	{
		int i = 0;
		int count = 0;
		StringBuilder aStr = new StringBuilder();
	
		while(i < original.getArray().length)
		{
			aStr.append(original.getArray()[i].getValue());
			i++;
			count++;
			
			if(count == 8 && i !=original.getArray().length)
			{
				aStr.append(",");
				count = 0;
			}
		}
		
		return aStr.toString();
	}

	/**
	 * fillbit()
	 * This is my self-created helper method. 
	 * It main function is to initialize a bit[] that will be the building block for longword.
	 * @return a bit[] representation of longword
	 */
	public bit[] fillbit()
	{
		bit[] allZero = new bit[32];

		for(int i = 0 ; i < 32 ; i++)
		{
			bit zero = new bit();
			zero.setValue(0);
			allZero[i] = zero;
		}
		return allZero;
	}
	
	/**
	 * printBit(bit[] print)
	 * This is a self-created helper method.
	 * It main function is to print out the bit[]. 
	 * @param toPrint the bit array that will be print out.
	 */
	public void printBit(bit[] toPrint)
	{
		int count = 0;
		for(int i = 0; i < toPrint.length; i++)
		{
			System.out.print(toPrint[i].getValue());
			count++;
			if(count == 8 && i < 30)
			{
				System.out.print(",");
				count = 0;
			}
		}
		System.out.println();
	}
}