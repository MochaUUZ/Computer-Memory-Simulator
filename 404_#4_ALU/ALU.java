/**
 * This is the implementation file of the ALU.
 * @author Sheng hao Dong
 *
 */
public class ALU {
	
	/**
	 * This is the main method that will be the ALU. 
	 * @param operation Based on the operation, this method will do different stuff to a and b
	 * @param a A longword(binary number) representation of a number
	 * @param b A longword(binary number) representation of a number
	 * @return a A longword(binary number) representation of the result of the operation
	 */
	public static longword doOp(bit[] operation, longword a, longword b)
	{
		// Initializing the data I will be using.
		Impl_Longword longword_OpA = new Impl_Longword();
		Impl_Longword longword_OpB = new Impl_Longword();

		// Copying a and b into separate data so I don't accidentally touches them.
		longword_OpA.original = a;
		longword_OpB.original = b;
		
		// Will be returning this.
		longword result = new longword();
		
		// My though is this, I will check the right most digit of operation, and then going into the digits to the left.
		// Based on the value on each digit, the choice gets narrower, when there is only one unique choice left, 
		// I know that is the operator I will be using and start implement that operation.
		// xxx1
		if(operation[operation.length-1].getValue() == 1)
		{
			// xx11
			if(operation[operation.length-2].getValue() == 1)
			{
				//x111
				if(operation[1].getValue() == 1)
				{
					//1111
					if(operation[0].getValue() == 1)
					{
						// 1111 - subtract
						System.out.println("\n1111 - subtract");
						System.out.println(longword_OpA.getSigned() + " - " + longword_OpB.getSigned() + " = " + (longword_OpA.getSigned()-
								longword_OpB.getSigned()));
						System.out.println();
						printBit(a.getArray());
						printBit(b.getArray());
						System.out.println("____________________________________");
						result = rippleAdder.subtract(a, b);
					// 0111
					}else
					{
						// 0111 - multiply
						System.out.println("\n0111 - multiply\n");
						System.out.println(longword_OpA.getSigned() + " x " + longword_OpB.getSigned() + " = " 
						+ longword_OpA.getSigned()*longword_OpB.getSigned());
						System.out.println();
						printBit(a.getArray());
						printBit(b.getArray());
						System.out.println("____________________________________");
						result = Multiplier.multiply(a, b);
					}
				// x011 (there is only one choice that have -011 as the last three digits)
				}else
				{
					// 1011 - not
					System.out.println("\n1011 - not");
					printBit(a.getArray());
					System.out.println("____________________________________");
					result = longword_OpA.not();
				}
			// xx01
			}else
			{
				// x101 (refer to the top for x011)
				if(operation[1].getValue() == 1)
				{
					// 1101 - right shift
					System.out.println("1101 - right shift");
					printBit(a.getArray());
					System.out.println("____________________________________");
					result = longword_OpA.rightShift(longword_OpB.getSigned());
				// x001 (refer to the top for x011)
				}else
				{
					// 1001 - or
					System.out.println("\n1001 - or");
					printBit(a.getArray());
					printBit(b.getArray());
					System.out.println("____________________________________");
					result = longword_OpA.or(b);
				}
			}
		// xxx0
		}else
		{
			// xx10
			if(operation[operation.length-2].getValue() == 1)
			{
				// x110 (refer to the top for x011)
				if(operation[1].getValue() == 1)
				{
					// 1110 - add
					System.out.println("\n1110 - add");
					System.out.println(longword_OpA.getSigned() + " + " + longword_OpB.getSigned() + " = " 
					+ (longword_OpA.getSigned()+longword_OpB.getSigned()));
					System.out.println();
					printBit(a.getArray());
					printBit(b.getArray());
					System.out.println("____________________________________");
					result =rippleAdder.add(a, b);
				// x010 (refer to the top for x011)
				}else
				{
					// 1010 - xor
					System.out.println("\n1010 - xor");
					printBit(a.getArray());
					printBit(b.getArray());
					System.out.println("____________________________________");
					result = longword_OpA.xor(b);
				}
			// xx00 
			}else
			{
				// x100 (refer to the top for x011)
				if(operation[1].getValue() == 1)
				{
					// 1100 - left shift
					System.out.println("1100 - left shift");
					printBit(a.getArray());
					System.out.println("____________________________________");
					result = longword_OpA.leftshift(longword_OpB.getSigned());
					// x000 (refer to the top for x011)
				}else
				{
					// 1000 - and
					System.out.println("\n1000 - and");
					printBit(a.getArray());
					printBit(b.getArray());
					System.out.println("____________________________________");
					result = longword_OpA.and(b);
				}
			}
		}
		
		// And then return the result. Which will be unique because there should be only one operation gets actived. 
		return result;
	}
	
	/**
	 * This is the helper method which will print out the bit[]
	 * @param toPrint The bit[] which will be print out.
	 */
	public static void printBit(bit[] toPrint)
	{
		int count = 0;
		for(int i = 0; i < toPrint.length; i++)
		{
			System.out.print(toPrint[i].getValue());
			count++;
			if(count == 8)
			{
				System.out.print(",");
				count = 0;
			}
		}
		System.out.println();
	}

}
