/**
 * this is the interface for the bit class. 
 * @author Sheng hao Dong
 * @version 1.0
 */

public interface I_Bit {
	// Sets the value of the bit.
	void set(int value);
	
	//changes the value from "0 to 1" or "1 to 0".
	void toggle(); 
	
	// sets the bit to 1.
	void set();
	
	// sets the bit to 0.
	void clear();
	
	// returns the current value.
	int getValue();
	
	// performs "and" on two bits and returns a new bit set to the result.
	bit and(bit other);
	
	// performs "or" on two bits and returns a new bit.
	bit or(bit other);

	// performs "xor" on two bits and returns a new bit set to the result.
	bit xor(bit other);
	
	// perform "not" on the existing bit, returning the result as a new bit.
	bit not();
	
	// return "0" or "1"
	@Override
	String toString();
}
