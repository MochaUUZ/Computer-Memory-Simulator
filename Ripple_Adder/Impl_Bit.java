import java.lang.Math;

/**
 * This is the implementation of the interface file, I_bit.java 
 *  
 * @author Sheng hao Dong
 * @version 1.0 
 */
public class Impl_Bit implements I_Bit {
	
	// This is to store each instances of the bit class
	bit bitHolder = new bit();
	
	/**
	 * set(int value)
	 * This method set the bit into user specify value. 
	 * p.s ~~please dont use number other than 0 or 1 as this is bit-rep. ~~
	 * @param value a value the user input.
	 * @return Nothing
	 * 
	 */
	public void set(int value)
	{
		bitHolder.setValue(value);
	}
	
	/**
	 * toggle()
	 * This method switch the bit from "on" to "off" or vice-versa. 
	 * @return Nothing
	 */
	public void toggle()
	{
		bitHolder.setValue(Math.abs(bitHolder.getValue() - 1 ));
		
	}

	/**
	 * set()
	 * This method set the bit to 1.
	 * @return Nothing
	 */
	public void set()
	{
		bitHolder.setValue(1);
	}
	
	/**
	 * clear()
	 * This method set the bit to 0.
	 * @return Nothing
	 */
	public void clear()
	{
		bitHolder.setValue(0);
	}
	
	/**
	 * getValue()
	 * This method get the current value of the bit and return the value as int.
	 * @return the value of the bit.
	 */
	public int getValue()
	{
		return bitHolder.getValue();
	}
	
	/**
	 * and(bit other)
	 * This method compare two bit using the "and operator" and return the result as a bit.
	 * @param other the other bit that is comparing to current bit.
	 * @return A bit-rep of the result from the operator.
	 */
	public bit and(bit other)
	{
		bit andBit = new bit();
		
		if(bitHolder.getValue() == other.getValue())
		{
			if(bitHolder.getValue() == 0)
			{
				andBit.setValue(0);
			}else {
			andBit.setValue(1);
			}
		}else
		{
			andBit.setValue(0);
		}
		
		return andBit;
	}
	
	/**
	 * or(bit other)
	 * This method compare two bit using the "or operator" and return the result as a bit.
	 * @param other the other bit that is comparing to current bit.
	 * @return A bit-rep of the result from the operator.
	 */
	public bit or(bit other)
	{
		bit orBit = new bit();
		
		if(bitHolder.getValue() == 1)
		{
			orBit.setValue(1);
		}else if(other.getValue() == 1)
		{
			orBit.setValue(1);
		}else
		{
			orBit.setValue(0);
		}
		
		return orBit;
	}
	
	/**
	 * xor(bit other)
	 * This method compare two bit using the "xor operator" and return the result as a bit.
	 * @param other the other bit that is comparing to current bit.
	 * @return A bit-rep of the result from the operator.
	 */
	public bit xor(bit other)
	{
		bit xorBit = new bit();
		
		if(bitHolder.getValue() == other.getValue())
		{
			xorBit.setValue(0);
		}else
		{
			xorBit.setValue(1);
		}
		
		return xorBit;
	}
	
	/**
	 * not()
	 * This method do the "not operator" to the current bit and return the result as a bit.
	 * @return A bit-rep of the result from the operator.
	 */
	public bit not()
	{
		bit notBit = new bit();
		
		notBit.setValue(Math.abs(bitHolder.getValue() - 1));
		
		return notBit;
	}
	
	/**
	 * toString()
	 * This method make the int as a string-rep. 
	 * @return A string-rep of the int. 
	 */
	public String toString()
	{
		return Integer.toString(bitHolder.getValue());
	}
}