/**
 * This is the implementation of the memory file.
 * @author Sheng hao Dong
 */
public class memory {
	// A rep of memory.
	private bit[] memory = new bit[8192];
	
	/**
	 * This will be the method to initialize the memory.
	 */
	public void initilize()
	{
		// A simply loop
		for(int i = 0; i < memory.length; i++)
		{
			bit aBit = new bit();
			aBit.setValue(0);
			memory[i] = aBit;
		}
	}
	
	/**
	 * This is the read method which will read from the memory.
	 * @param address An index
	 * @return A longword rep of the reading
	 * @throws Exception When you try to access a index that is out of bound of memory.
	 */
	public longword read(longword address) throws Exception
	{	
		Impl_Longword theAddress = new Impl_Longword();
		
		bit[] forResult = new bit[32];
		longword result = new longword();
		
		theAddress.original = address;
		
		int value = theAddress.getSigned();
		if(value < 0 || value >= 1024)
		{
			System.out.println("\n\nERROR: The address is out of bound. Please enter address from 0 ~ (8192/32)-1\n\n");
			throw new Exception();
		}
		
		if(value >= 0 || value <= 1023)
		{
			for(int i = 0; i < address.getArray().length; i++)
			{
				forResult[i] = memory[value*8+i];
			}
		}
		result.setArray(forResult);
		return result;
	}
	
	/**
	 * This is the write method of memory. This will write the longword of the memory.
	 * @param address The index
	 * @param value The desire longword to replace.
	 */
	public void write(longword address, longword value)
	{
		Impl_Longword theAddress = new Impl_Longword();
		Impl_Longword theValue = new Impl_Longword();
		
		theAddress.original = address;
		theValue.original = value;
		
		int index = theAddress.getSigned();		
		
		if(index < 0 || index >= 1021)
		{
			System.out.println("ERROR: the address is out of bound. Please enter address from 0 ~ (8192/32)-1");
			return;
		}
		
		if(index >= 0 && index <= 1020)
		{
			for(int i = 0; i < value.getArray().length ; i++)
			{
			memory[index*8+i] = theValue.getBit(i);
			}
		}
	}
	
	/**
	 * A getter for the memory.
	 * @return The memory
	 */
	public bit[] getter()
	{
		return this.memory;
	}
	
}
