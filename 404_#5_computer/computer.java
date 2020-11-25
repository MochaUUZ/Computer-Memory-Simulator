/**
 * This is the class that represent a computer. 
 * Develop log: 
 * 10/13/2020,	Finish assignment 6, a test is not required.
 * 10/20/2020,	implemented the helper method to calculate binary to decmial. 
 * 10/25/2020,	working on assignment 7. 
 * 10/28/2020,	finished assignment 7.
 * 11/14/2020,	Start implementing assignment 9. Try to do part 1 and get the 
 * 				foundation down for part 2. 
 * 				UPDATE: Don't think I implement part 1 correctly. Will need clarification. 
 * 11/15/2020 	Have a very big confusion on assignment 9 descrption. Can not continue until
 * 				I clear my confusion. 
 * 11/17/2020	The confusion is all crystal clear now. I will be starting to implementing part 2 of 
 * 				assignment 9. 
 * 11/18/2020 	Finish touch up for assignment 9. Added comments which explain the new instruction that
 * 				had been implemented which you can find at top of the method header. Please see the detailed
 * 				comment by going into each method and examine deligiently. 
 * 11/21/2020	Start implementing assignment 10. I will try to first implement push and pop. These
 * 				two shouldn't be hard to implement in my believe. 
 * 11/22/2020	Finished the implement of push and pop. I wrote down the basic step of call and return 
 * 				by comments. 
 * 11/23/2020	Start implementing the command call and return. This takes much less time than I think of.
 * 				I will test this new assignment. I think I will be finishing this assignment early. 
 * 11/24/2020	Final test. Bug test. Renovating the code. 
 * 
 * -----Final Edition-----
 * -----Final Edition-----
 * -----Final Edition-----
 * -----Final Edition-----	  
 * @author Sheng hao Dong
 */
public class computer {
	// All the variable we will need to build a computer
	
	// Indicate should the while loop be continue running or nor
	bit halted = new bit();
	
	// The PC. 
	longword PC = new longword();
	longword currentInstruction = new longword();
	// Assignment 10 
	longword SP = new longword();
	
	// An instance of the memory
	private memory aMemory = new memory();
	
	// This will hold the operation bit to let excute, store know what to do. 
	bit[] opcode = new bit[4];

	// Part 2(registers) of the assignment (from Assignment 6)
	longword[] Registers = new longword[16];
	
	// Part 4(decode) of the assignment. This is the op1 and op2.  (From Assignment 6)
	longword op1 = new longword();
	longword op2 = new longword();
	
	// Part 4(execute) of the assignment, this is the where the result from the ALU will goto. (From Assignment 6)
	longword result = new longword();

	// Assignment 9, part 2, compare operation
	bit isGreater = new bit();
	bit isEqual = new bit();

	/**
	 * A while loop that will be running until halted is not zero.
	 * 
	 * @throws Exception This happens when the user is trying to access a index of memory that is out of bound.
	 */
	public void run() throws Exception
	{
		halted.setValue(0);
		
		// This is a copy of the PC. 
		Impl_Longword forPC = new Impl_Longword();
		// I default the value of copy of PC to zero.
		forPC.set(0);
		// I then change PC so the value is not all NULL.
		PC.setArray(forPC.original.getArray());

		// To set the value of SP to 1020
		Impl_Longword forSP = new Impl_Longword();
		forSP.set(1020);
		SP.setArray(forSP.original.getArray()); // Default the SP to 1020. 
		
		while(halted.getValue() != 1)
		{
			fetch();
			decode();
			execute();
			store();
		}
	}
	
	/**
	 * This method read the PC and store the next longword of PC pointer of memory
	 * and store in currentInstruction. then increment PC by 2.
	 * 
	 * @throws Exception This happens when the user is trying to access a index of memory that is out of bound.
	 */
	public void fetch() throws Exception
	{
		// This is the copy of PC since I don't want to directly touch PC.
		Impl_Longword accessToPC = new Impl_Longword();
		accessToPC.original = PC;
		
		System.out.println("Current address in memory: " + accessToPC.getSigned());

		currentInstruction = aMemory.read(accessToPC.original);

		// Create a longword which has a value of 2
		Impl_Longword theNumberTwo = new Impl_Longword();
		theNumberTwo.set(2);
		
		// this longword will be holding for the result of PC after increment by 2
		longword result = new longword();
		
		// Use a rippleAdder to increment the PC by 2.
		result = rippleAdder.add(PC, theNumberTwo.original);
		
		// update the value of PC. 
		PC.setArray(result.getArray());
	}
	
	/**
	 * This method detech which operation code the instruction specify and based on
	 * the different operation code, this method does different action. 
	 * 
	 * Halt: Set halted to be 1 and end this function.
	 * Move: Does nothing for move. 
	 * Interrupt: Does nothing for interrupt.
	 * ALU: Store the first value and second value into op1 and op2.
	 * Jump: This will jump the current PC to the indicated value in term of byte.
	 * Compare: This will compare the value between two register and store the proper result after the comparision.
	 * Branches : These are varies branches name which will execute if the condition code is matches. Or else, it will not execute.
	 * 
	 * @return 0 indicate this method is runned successfully
	 */
	public int decode()
	{
		// Create a copy of currentInstruction so that I don't touch the Instruction.
		Impl_Longword copyOfInstruction = new Impl_Longword();
		copyOfInstruction.original= currentInstruction;

		// Create the mask for extracting the right most 4 bit. 
		Impl_Longword maskForRight4Bit = new Impl_Longword();
		
		// Set the mask to : 00000000 00000000 00000000 00001111
		maskForRight4Bit.set(15);
		
		// Right shift currentInstruction so that the part I want to extract is in the 
		// right most index. 
		// The opCode part. Getting the first 4 bit.
		Impl_Longword storeShifting = new Impl_Longword();
		storeShifting.original = copyOfInstruction.rightShift(28);

		// Setting the opCode for this instruction.
		readingOpCode(storeShifting.original);

		int decimalOpCode = bitToDecimal(opcode, 0);
		
		if(decimalOpCode == 0) // Halt operation code.
		{
			halted.setValue(1); 

			// Since halt instruction is receive, this function will end. 
			return 0;
		}
		else if(decimalOpCode == 1) // Move operation code
		{
			// decode don't do anything for move operation.
			return 0;

		}
		else if(decimalOpCode == 2) // Interrupt operation code
		{
			// As assignment 7 specify, decode don't do anything for interrupt instruction. 
			return 0;

		}
		else if(decimalOpCode == 3) // Jump operation code.
		{
			// As assignment 9 specify, decode don't do anything for jump instruction.
			return 0;
		}
		else if(decimalOpCode == 4) // Compare operation code
		{
			// As assignment 9 specify, the store() should be use for compare instruction.
			return 0;
		}
		else if(decimalOpCode == 5) // Branch operation code
		{	
			// Based on assignment 9, decode doesn't do anything for branch.
			return 0;
		}
		else if(decimalOpCode == 6) // Stack related operation code
		{
			bit[] stackCode = new bit[2];
			for(int i = 0; i < stackCode.length; i++)
			{
				bit holder = new bit();
				holder.setValue(currentInstruction.getArray()[4 + i].getValue());
				stackCode[i] = holder;
			}
			int stackOp = bitToDecimal(stackCode, 0);

			if(stackOp == 0) // Push operation code
			{
				// I think it reasonable to let store() for handling the Stack-related operation
				return 0;
			}
			else if(stackOp == 1) // Pop operation code
			{
				// I think it reasonable to let store() for handling the Stack-related operation
				return 0;
			}
			else if(stackOp == 2) // Call operation code
			{
				// I think it reasonable to let store() for handling the Stack-related operation
				return 0;
			}
			else if(stackOp == 3) // Return operation code
			{
				// I think it reasonable to let store() for handling the Stack-related operation
				return 0;
			}

			return 0;
		}
		else if(decimalOpCode > 6) // ALU operation code
		{  // Assignment 6 code, with some renovation for easy to read purpose. 

			// Shifting the instruction where R3 is at the rightest index.
			storeShifting.original = copyOfInstruction.rightShift(20);

			// Mask the last 4 bit, ex) 000000...000...0000XXXX
			longword extract4Bit = storeShifting.and(maskForRight4Bit.original);
			
			// I want to change the longword representation of register index to 
			// decimal representation. I will be using the getSigned() function.
			Impl_Longword longwordToDecimal = new Impl_Longword();
			longwordToDecimal.original.setArray(extract4Bit.getArray());
			int registerIndex = longwordToDecimal.getSigned();

			// Final steps, store the value at register's index into the correct op (op2).
			op2.setArray(Registers[registerIndex].getArray());

			// A repeat of the above step but with different parameter. The steps and reasons
			// behind each code is identical so look at the top code for reference.

			// Shifting the instruction where R2 is at the rightest index.
			storeShifting.original = copyOfInstruction.rightShift(24);
			extract4Bit = storeShifting.and(maskForRight4Bit.original);
			longwordToDecimal.original.setArray(extract4Bit.getArray());
			registerIndex = longwordToDecimal.getSigned();
			op1.setArray(Registers[registerIndex].getArray());
		}
		return 0;
	}
	
	/**
	 * This is where most action will be happening. This method will do different action based on the 
	 * different operation code.
	 * Halt: Automatic end this function.
	 * Move: Save the value that will be store into op1.
	 * Interrupt: This will print out either the registers or the memory based on type of interrupt.
	 * ALU: Call the ALU class to perform the calculation and save the result for storing.
	 * Jump: This will jump the current PC to the indicated value in term of byte.
	 * Compare: This will compare the value between two register and store the proper result after the comparision.
	 * Branches : These are varies branches name which will execute if the condition code is matches. Or else, it will not execute.
	 * 
	 * @throws Exception This exception is throw when user is trying to access a index of memory that is out of bound.
	 * @return 0 This happen when either halt or interrupt is encountered.
	 * @return 1 This happen when either move ALU is encountered and successfully performed. 
	 */
	public int execute() throws Exception 
	{
		// Check to see which operation instruction received. 
		int decimalOpCode = bitToDecimal(opcode, 0);

		// Create a copy of currentInstruction so that I don't touch the Instruction.
		Impl_Longword copyOfInstruction = new Impl_Longword();
		copyOfInstruction.original = currentInstruction; 

		// Create the mask for extracting the right most 4 bit. 
		Impl_Longword maskForRight4Bit = new Impl_Longword();

		// Set the mask to : 00000000 00000000 00000000 00001111
		maskForRight4Bit.set(15);

		if(decimalOpCode == 0) // Halt operation code.
		{
			// Since halt instruction is receive, this function will end. 
			return 0;
		}
		else if(decimalOpCode == 1) // Move operation code
		{
			// left shift currentInstruction so that the part I want to extract is in the 
			// left most index. 

			// The getting value part. Getting the value that will be move to register.
			Impl_Longword storeShifting = new Impl_Longword();
			storeShifting.original = copyOfInstruction.leftshift(8);

			// Create a bit array, size 8, that will hold the value that will be move.
			bit[] size8Binary = new bit[8];

			// This for loop will copy the left-shifted instruction into the bit array which will hold 
			// the value of the number that will be move. 
			for(int i = 0; i < 8; i++)
			{
				bit aNewBit = new bit();
				aNewBit.setValue(storeShifting.original.getArray()[i].getValue());
				size8Binary[i] = aNewBit;
			}

			// This is the decimal value of the longword value.
			int bitArrayToDecimal = bitToDecimal(size8Binary, 1);

			// Set the longword's value to represent the decimal.
			Impl_Longword longwordRep = new Impl_Longword();
			longwordRep.set(bitArrayToDecimal);

			// This is the final step, which store the value into the op1. 
			op1.setArray(longwordRep.original.getArray());
		}
		else if(decimalOpCode == 2) // Interrupt operation code
		{
			// Shifting the instruction where R4 is at the rightest index.
			Impl_Longword storeShifting = new Impl_Longword();
			storeShifting.original = copyOfInstruction.rightShift(16);

			// Mask the last 4 bit, ex) 000000...000...0000XXXX
			longword extract4Bit = storeShifting.and(maskForRight4Bit.original);

			// I want to change the longword representation of register index to 
			// decimal representation. I will be using the getSigned() function.
			Impl_Longword longwordToDecimal = new Impl_Longword();
			longwordToDecimal.original.setArray(extract4Bit.getArray());
			int interruptType = (int)longwordToDecimal.getUnsigned();

			// This is the last index of the interrupt is 0
			if(interruptType == 0)
			{
				// This longword will be use mainly for printing out the data.
				Impl_Longword aPrinter = new Impl_Longword();

				System.out.println("Printing the registers\n");

				// A loop that goes over each registers check if it null or not
				for(int i = 0; i < Registers.length; i++)
				{	
					if(Registers[i] == null)
					{
						// If the register is null, then tell user this register is null
						System.out.println("Registers[" + i + "]: null" );
					}
					else
					{
						// If the registers is not null, then call the printer to print out the register.
						System.out.println("Registers[" + i + "]: ");
						
						aPrinter.printBit(Registers[i].getArray());
					}
				}
				// skip a line.
				System.out.println();
			}
			else if(interruptType == 1) // This is if the last index of interrupt is 1
			{
				System.out.println("Printing the memory...\n");

				// For printing out the memory.
				Impl_Longword aPrinter = new Impl_Longword();

				// For accessing different address of memory.
				Impl_Longword address = new Impl_Longword();
				Impl_Longword numberFour = new Impl_Longword();

				// Initialize address.
				address.set(0);

				// Use for increment the index of memory by 4 byte.
				numberFour.set(4);

				// A loop that goes voer the first 40 byte of memory and prints them out.
				for(int i = 0; i <= 40; i = i+4)
				{
					aPrinter.printBit(aMemory.read(address.original).getArray());
					address.original = rippleAdder.add(address.original, numberFour.original);
				}
				address.set(1004);
				System.out.println("\nPrinting the stack...\n");
				System.out.print("...");

				for(int i = 0; i < 20; i = i+4)
				{
					aPrinter.printBit(aMemory.read(address.original).getArray());
					address.original = rippleAdder.add(address.original, numberFour.original);
				}
				System.out.println();
			}
		}
		else if(decimalOpCode == 3) // Jump operation code
		{
			// As assignment 9 specify, execute don't do anything for jump instruction. 
			return 0;
		} 
		else if(decimalOpCode == 4) // Compare operation code
		{
			// As assignment 9 specify, store() should be operating the compare instruction.
			return 0;
		}
		else if(decimalOpCode == 5) // Branch operation code
		{
			// Branch If Equal 			- 01   
			// Branch Not Equal 		- 00
			// Branch Greater Than 		- 10
			// Greater Than or Equal 	- 11 
			bit signs = copyOfInstruction.original.getArray()[6];
			bit checkEqual = copyOfInstruction.original.getArray()[5];
			bit checkGreater = copyOfInstruction.original.getArray()[4];

			// Set the mask to ...00 0000 0001 1111 1111
			Impl_Longword maskFor9Bit = new Impl_Longword();
			maskFor9Bit.set(-1);
			maskFor9Bit.original = maskFor9Bit.rightShift(23);

			Impl_Longword shiftForInstruction = new Impl_Longword();
			shiftForInstruction.original = copyOfInstruction.rightShift(16);

			if(checkEqual.getValue() == isEqual.getValue()) // Compare if result is equal or not equal. 
			{ 					
				// This will satisfy branch Equal and branch Not Equal

				Impl_Longword store9Bit = new Impl_Longword();
				store9Bit.original = shiftForInstruction.and(maskFor9Bit.original);

				if(signs.getValue() == 1)
				{
					Impl_Longword changeToNeg = new Impl_Longword();
					changeToNeg.original = store9Bit.not();

					Impl_Longword numberOne = new Impl_Longword();
					numberOne.set(1);

					Impl_Longword resultNum = new Impl_Longword();
					resultNum.original = rippleAdder.add(changeToNeg.original, numberOne.original);

					result.setArray(resultNum.original.getArray());
					return 0;
				}
				else if(signs.getValue() == 0)
				{
					//result = store9Bit.original;
					result.setArray(store9Bit.original.getArray());
					return 0;
				}
			}
			else if(checkGreater.getValue() == isGreater.getValue())
			{
				 // Check for if the greater than condition match. 
				
				
					// This will satisfy branch Greater than and branch Greater than or Equal

					Impl_Longword store9Bit = new Impl_Longword();				
					store9Bit.original = shiftForInstruction.and(maskFor9Bit.original);

					if(signs.getValue() == 1)
					{
						Impl_Longword changeToNeg = new Impl_Longword();
						changeToNeg.original = store9Bit.not();
				
						Impl_Longword numberOne = new Impl_Longword();			
						numberOne.set(1);
		
						Impl_Longword resultNum = new Impl_Longword();		
						resultNum.original = rippleAdder.add(changeToNeg.original, numberOne.original);
	
						result.setArray(store9Bit.original.getArray());
						return 0;	
					}
					else if(signs.getValue() == 0)
					{
						result.setArray(store9Bit.original.getArray());
						return 0;
					}
				
			}	
			else
			{
				// Lets said Rx and Ry is equal to each other. If Program runs into this part of code means, 
				// 1) The condition code for equal is "not equal" but the result from "compare" is equal. The Equals don't match.
				// 2) Since the equals don't match, it is default that this branch can not be branch into. 

				// Let's said Rx is less than Ry. This will make the compare to produce 
				// bit Equal = 0	bit greaterThan = 0
				// 1) Let's said the condition code for equal and the result from compare do match, they both are 0. 
				// 2) This will lead us into checking the greater than code, which, if run into this part of code means: 
				// 	  they don't match. So the condition code from this instruction is 10 but the bit stored in CPU is 00. 
				// 3) Since one said it greater, and other said it lesser, we don't branch in.
				Impl_Longword zero = new Impl_Longword();
				zero.set(0);
				result = zero.original;
				return 0;
			}
			return 0;
		}
		else if(decimalOpCode == 6)
		{
			bit[] stackCode = new bit[2];
			for(int i = 0; i < stackCode.length; i++)
			{
				bit holder = new bit();
				holder.setValue(currentInstruction.getArray()[4 + i].getValue());
				stackCode[i] = holder;
			}
			int stackOp = bitToDecimal(stackCode, 0);

			if(stackOp == 0) // Push operation code
			{
				// I think it reasonable to let store() for handling the Stack-related operation
				return 0;
			}
			else if(stackOp == 1) // Pop operation code
			{
				// I think it reasonable to let store() for handling the Stack-related operation
				return 0;
			}
			else if(stackOp == 2) // Call operation code
			{
				// I think it reasonable to let store() for handling the Stack-related operation
				return 0;
			}
			else if(stackOp == 3) // Return operation code
			{
				// I think it reasonable to let store() for handling the Stack-related operation
				return 0;
			}
	
			return 0;
		}
		else if(decimalOpCode > 6) // ALU operation code
		{
			// // Assignment 6 code, with some renovation for easy to read purpose. 
			
			// I create a new longword and set the longword into all zero for intiating result.
			Impl_Longword aPrint = new Impl_Longword();
			aPrint.set(0);

			result = aPrint.original;

			// Set the value of result to the result of the ALU.
			result.setArray(ALU.doOp(opcode, op1, op2).getArray());
			return 1;
		}
		return 0;
	}
	
	/**
	 * This is the function use for store the value into register. Different type of
	 * action will be perform to determine the type of data to store based on
	 * different op code. Halt: Automatic end this function. Move: Read the
	 * instruction for the index of register, and store the saved value into the
	 * register's index. Interrupt: Automatic end this function. ALU: Read the
	 * instruction for the index of register, and store the result value into the
	 * register's value. Jump: This will jump the current PC to the indicated value
	 * in term of byte. Compare: This will compare the value between two register
	 * and store the proper result after the comparision. Branches : These are
	 * varies branches name which will execute if the condition code is matches. Or
	 * else, it will not execute.
	 * 
	 * This method first read from the instruction to know which index of register
	 * to store the result. After that is known, the value of result is stored in
	 * the index of register.
	 * 
	 * @return 0 This happen when either halt or interrupt is encountered.
	 * @return 1 This happen when either move ALU is encountered and successfully
	 *         performed.
	 * @throws Exception
	 */
	public int store() throws Exception
	{
		// Create a copy of currentInstruction so that I don't touch the Instruction.
		Impl_Longword copyOfInstruction = new Impl_Longword();
		copyOfInstruction.original= currentInstruction;
		
		// Create the mask for extracting the right most 4 bit. 
		Impl_Longword maskForRight4Bit = new Impl_Longword();
		
		// Set the mask to : 00000000 00000000 00000000 00001111
		maskForRight4Bit.set(15);
		
		int decimalOpCode = bitToDecimal(opcode, 0);

		if(decimalOpCode == 0) // Halt operation code.
		{
			// Since halt instruction is receive, this function will end. 
			return 0;
		}
		else if(decimalOpCode == 1) // Move operation code
		{
			Impl_Longword storeShifting = new Impl_Longword();
			storeShifting.original = copyOfInstruction.rightShift(24);

			// Mask the last 4 bit, ex) 000000...000...0000XXXX
			longword extract4Bit = storeShifting.and(maskForRight4Bit.original);

			// I want to change the longword representation of register index to 
			// decimal representation. I will be using the getSigned() function.
			Impl_Longword longwordToDecimal = new Impl_Longword();
			longwordToDecimal.original.setArray(extract4Bit.getArray());
			int registerIndex = longwordToDecimal.getSigned();

			// I will first initiate a longword.
			Impl_Longword aNewWord = new Impl_Longword();
			aNewWord.set(0);

			// Then initiate the registers using the longword I created.
			Registers[registerIndex] = aNewWord.original;

			// This is final step, store the value into the register's index.
			Registers[registerIndex].setArray(op1.getArray());
		}
		else if(decimalOpCode == 2) // Interrupt operation code
		{
			// As assignment 7 specify, store don't do anything for interrupt instruction.
			return 0;
		}
		else if(decimalOpCode == 3) // Jump operation code
		{
			Impl_Longword masking = new Impl_Longword();
			masking.set(-1);
			longword maskingRight12Bit = masking.rightShift(20);

			Impl_Longword storeShift = new Impl_Longword();
			storeShift.original = copyOfInstruction.rightShift(16);
			
			longword extractRight12Bit = storeShift.and(maskingRight12Bit);

			PC.setArray(extractRight12Bit.getArray());
		}
		else if(decimalOpCode == 4) // Compare operation code
		{ 
			bit[] firstRegister = new bit[4];
			for(int i = 8; i < 12; i++)
			{
				firstRegister[i-8] = copyOfInstruction.original.getArray()[i];
			}
			int leftRegister = bitToDecimal(firstRegister, 0); // the a in a < b, left of the comparision

			bit[] secondRegister = new bit[4];
			for(int j = 12; j < 16; j++)
			{
				secondRegister[j-12] = copyOfInstruction.original.getArray()[j];
			}
			int rightRegister = bitToDecimal(secondRegister, 0);

			longword toBeFlip = new longword();
			toBeFlip.setArray(Registers[rightRegister].getArray());

			longword compareResult = rippleAdder.subtract(Registers[leftRegister], toBeFlip);
			int resultOfCompare = bitToDecimal(compareResult.getArray(), 1);
			if(resultOfCompare > 0)
			{ // Means a is greater than b.
				isGreater.setValue(1);
				isEqual.setValue(0);
			}
			else if(resultOfCompare < 0)
			{ // Means a is less than b
				isGreater.setValue(0);
				isEqual.setValue(0);
			}
			else
			{ // Means a is equal to b.
				isGreater.setValue(1);
				isEqual.setValue(1);
			}
		}
		else if(decimalOpCode == 5) // Branch operation code
		{
			Impl_Longword wordHolder = new Impl_Longword();

			wordHolder.original = rippleAdder.add(PC, result);

			PC.setArray(wordHolder.original.getArray());
			
			return 0;
		}
		else if(decimalOpCode == 6) // Stack-related operation code
		{
			// Trying to see which of the stack operation is.
			bit[] stackCode = new bit[2];
			for(int i = 0; i < stackCode.length; i++)
			{
				bit holder = new bit();
				holder.setValue(currentInstruction.getArray()[4 + i].getValue());
				stackCode[i] = holder;
			}
			int stackOp = bitToDecimal(stackCode, 0);

			if(stackOp == 0) // Push operation code
			{
				// Extracting the register index
				Impl_Longword storeShifting = new Impl_Longword();
				storeShifting.original = copyOfInstruction.rightShift(16);
				longword extract4Bit = storeShifting.and(maskForRight4Bit.original);

				// Try to change the register index into decimal number
				Impl_Longword longwordToDecimal = new Impl_Longword();
				longwordToDecimal.original.setArray(extract4Bit.getArray());
				int index = (int) longwordToDecimal.getUnsigned();

				// Write the register value onto stack
				longword registerValue = new longword();
				registerValue.setArray(Registers[index].getArray());
				this.aMemory.write(this.SP, registerValue); // Success!
				
				// Subtract 4 from SP. 
				Impl_Longword negativeFour = new Impl_Longword();
				negativeFour.set(-4);

				// Change the SP value
				longword result = new longword();
				result = rippleAdder.add(this.SP, negativeFour.original);
				this.SP.setArray(result.getArray());

				return 0;
			}
			else if(stackOp == 1) // Pop operation code
			{
				// Extracting the register index
				Impl_Longword storeShifting = new Impl_Longword();
				storeShifting.original = copyOfInstruction.rightShift(16);
				longword extract4Bit = storeShifting.and(maskForRight4Bit.original);

				// Try to change the register index into decimal number
				Impl_Longword longwordToDecimal = new Impl_Longword();
				longwordToDecimal.original.setArray(extract4Bit.getArray());
				int index = (int) longwordToDecimal.getUnsigned();

				// Add four to current SP
				Impl_Longword numberFour = new Impl_Longword();
				numberFour.set(4);

				// Change the SP value
				longword result = new longword();
				result = rippleAdder.add(this.SP, numberFour.original);
				this.SP.setArray(result.getArray());

				// Copy from SP
				longword valueOfSP = new longword();
				valueOfSP = aMemory.read(this.SP);

				// Pop the SP onto the register
				Registers[index] = valueOfSP;

				// Clear the memory that had been read. 
				Impl_Longword freeMemory = new Impl_Longword();
				freeMemory.set(0);
				this.aMemory.write(this.SP, freeMemory.original);

				return 0;
			}
			else if(stackOp == 2) // Call operation code
			{
				// Try to read the AA AAAA AAAA part. Which give me the address to jump to.
				Impl_Longword masking = new Impl_Longword();
				masking.set(-1);
				longword maskingRight10Bit = new longword();
				maskingRight10Bit = masking.rightShift(22);

				Impl_Longword storeShifting = new Impl_Longword();
				storeShifting.original = copyOfInstruction.rightShift(16);

				// Extracting the address
				longword extractRight10Bit = new longword();
				extractRight10Bit = storeShifting.and(maskingRight10Bit);

				//Impl_Longword numberFour = new Impl_Longword();

				// Push the current instruction address onto the stack.
				longword valueOfPC = new longword();
				valueOfPC.setArray(this.PC.getArray());
				this.aMemory.write(this.SP, valueOfPC);

				// Change PC to the new address.
				PC.setArray(extractRight10Bit.getArray());

				// Popping the instruction onto R15.
				Registers[15] = new longword();
				Registers[15].setArray(this.aMemory.read(this.SP).getArray());

				// Clear the memory that had been popped.
				Impl_Longword freeMemory = new Impl_Longword();
				freeMemory.set(0);
				this.aMemory.write(this.SP, freeMemory.original);

				// So first, we want to read to AA AAAA AAAA part. Please refer to the top for masking.
				// AAA... will be the address to jump to. But not so yet change PC to the new address.
				// Lets first "Push" the next instruction's address onto the stack. ~> I'm not sure, maybe it current ins.
				// Then, you can change PC to the new address.
				// Finally, pop the next instruction's address to R15.

				return 0;
			}
			else if(stackOp == 3) // Return operation code
			{
				// Push R15 onto the stack
				longword registerValue = new longword();
				registerValue.setArray(Registers[15].getArray());
				this.aMemory.write(this.SP, registerValue);

				// Let PC equal to the top of the stack
				this.PC.setArray(aMemory.read(this.SP).getArray());

				// Lastly, clear the stack's top instruction
				Impl_Longword freeMemory = new Impl_Longword();
				freeMemory.set(0);
				this.aMemory.write(this.SP, freeMemory.original);

				return 0;
			}

			return 0;
		}
		else if(decimalOpCode > 6) // ALU operation code
		{
			// // Assignment 6 code, with some renovation for easy to read purpose. 

			// Shifting the instruction where R4 is at the rightest index.
			Impl_Longword storeShifting = new Impl_Longword();
			storeShifting.original = copyOfInstruction.rightShift(16);

			// Mask the last 4 bit, ex) 000000...000...0000XXXX
			longword extract4Bit = storeShifting.and(maskForRight4Bit.original);

			// I want to change the longword representation of register index to 
			// decimal representation. I will be using the getSigned() function.
			Impl_Longword longwordToDecimal = new Impl_Longword();
			longwordToDecimal.original.setArray(extract4Bit.getArray());
			int registerIndex = longwordToDecimal.getSigned();

			// Create a new instance for the registers.
			Impl_Longword aNewWord = new Impl_Longword();
			aNewWord.set(0);

			// Final steps, store the value of result into the register at specified index.
			Registers[registerIndex] = aNewWord.original;
			Registers[registerIndex].setArray(result.getArray());
		}
		return 1;
	}

	/**
	 * This is the helper method I create. This method convert the bit representation into decimal.
	 * This is use for determine the register index and the value for move instruction. 
	 * 
	 * @param convertToDecimal This is the bit array you want to convert to decimal.
	 * @param signedOrUnsigned this is a switch for you want to getSigned() or getUnsigned. 0 = unsigned, 1 = signed.
	 * @return This is the decimal representation of the bit array.
	 */
	public int bitToDecimal(bit[] convertToDecimal, int signedOrUnsigned)
	{
		Impl_Longword toDecimal = new Impl_Longword();
		int result = 0;

		// Have a longword that holds the value of the bit array.
		toDecimal.original.setArray(convertToDecimal);

		// Call the getSigned() function in longword to get the decmial representation of the bit array.
		if(signedOrUnsigned == 0)
		{
			result = (int)toDecimal.getUnsigned();
		}
		else if(signedOrUnsigned == 1)
		{
			result = toDecimal.getSigned();
		}
		return result;
	}

	/**
	 * This method will copy the operation bit(first 4 bit) of instruction 
	 * and store in a variable call opCode.
	 * @param currentInstruction this is the current insturction from memory I am in right now. 
	 */
	public void readingOpCode(longword currentInstruction)
	{
		for(int i = 0; i < 4; i++)
		{
			bit aNewBit = new bit();
			aNewBit.setValue(currentInstruction.getArray()[i+28].getValue());
			opcode[i] = aNewBit;
		}
	}

	/**
	 * This method loads the memory with instructions from the string array call data. 
	 * Either two action will be perform based on if the length of the data is odd or even number. 
	 * @param data this is the string array which each index contain a 16-bit instruction to write into memory.
	 */
	public void preload(String[] data)
	{
		aMemory.initilize();

		// This is the longword that have all zero use for intialize other longword.
		Impl_Longword allZero = new Impl_Longword();
		allZero.set(0);

		// This is the bit array that I will be transferring from data into here.
		bit[] copyFromData = new bit[32];
		longword longwoRepre = new longword();
		
		// The memory index which points to fresh unwrittened memory.
		Impl_Longword memoryIndex = new Impl_Longword();
		memoryIndex.set(0);

		// this will be use for increment the memory.
		Impl_Longword numberTwo = new Impl_Longword();
		numberTwo.set(4);

		// This is the for loop that goes over the string array.
		for(int j = 0; j < data.length; j= j + 2)
		{
			// This is the for loop that goes over each char at one string index.
			for(int i = 0; i < data[j].length(); i++)
			{
				bit oneBit = new bit();
				oneBit.setValue(data[j].charAt(i)- '0');

				copyFromData[i] = oneBit;
			}

			// This will happen if the length of the string is odd.
			if(j+2 == data.length+1)
			{
				for(int l = 0; l < allZero.original.getArray().length-16; l++)
				{
					bit oneBit1 = new bit();
					oneBit1.setValue(allZero.original.getArray()[l].getValue());
					copyFromData[l+16] = oneBit1;
				}

				longwoRepre.setArray(copyFromData);
				aMemory.write(memoryIndex.original, longwoRepre);
				memoryIndex.original = rippleAdder.add(memoryIndex.original, numberTwo.original);
			}
			else // this will happen if the length of the string is even.
			{
				for(int k = 0; k < data[j+1].length(); k++)
				{
					bit oneBit1 = new bit();
					oneBit1.setValue(data[j+1].charAt(k) - '0');
					copyFromData[k+16] = oneBit1;
				}
				longwoRepre.setArray(copyFromData);
				aMemory.write(memoryIndex.original, longwoRepre);
				memoryIndex.original = rippleAdder.add(memoryIndex.original, numberTwo.original);
			}
		}
	}
}