/**
 * This is the class that represent a computer. 
 * Develop log: 
 * (10/13/20),	Finish assignment 6, a test is not required.
 * (10/20/20),	implemented the helper method to calculate binary to decmial. 
 * (10/25/20),	working on assignment 7. 
 * (10/28/20),	finished assignment 7.
 * 11/14/2020,	Start implementing assignment 9. Try to do part 1 and get the 
 * 				foundation down for part 2. 
 * 				UPDATE: Don't think I implement part 1 correctly. Will need clarification. 
 * 11/15/2020 	Have a very big confusion on assignment 9 descrption. Can not continue until
 * 				I clear my confusion. 
 * 11/17/2020	The confusion is all crystal clear now. I will be starting to implementing part 2 of 
 * 				assignment 9. 
 * 			  
 * @author Sheng hao Dong
 */
public class computer {
	// All the variable we will need to build a computer
	
	// Indicate should the while loop be continue running or nor
	bit halted = new bit();
	
	// The PC. 
	longword PC = new longword();
	longword currentInstruction = new longword();
	
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
		



		//System.out.println(accessToPC.getSigned());





		currentInstruction = aMemory.read(accessToPC.original);

		/* TESTING ONLY */
		//accessToPC.printBit(currentInstruction.getArray());
		// END OF TESTING

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
			System.out.println("Decode: Jump");
			return 0;
		}
		else if(decimalOpCode == 4) // Compare operation code
		{
			// As assignment 9 specify, the store() should be use for compare instruction.
			System.out.println("Decode: Compare");
			return 0;
		}
		else if(decimalOpCode == 5) // Branch operation code
		{	
			// Based on assignment 9, decode doesn't do anything for branch.
			return 0;
		}
		else if(decimalOpCode > 5) // ALU operation code
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
			}
		}
		else if(decimalOpCode == 3) // Jump operation code
		{
			// As assignment 9 specify, execute don't do anything for jump instruction. 
			System.out.println("Execute: jump");
			return 0;
		} 
		else if(decimalOpCode == 4) // Compare operation code
		{
			// As assignment 9 specify, store() should be operating the compare instruction.
			System.out.println("Execute: compare");
			return 0;
		}
		else if(decimalOpCode == 5) // Branch operation code
		{
			
			// Branch If Equal 			- 01   
			// Branch Not Equal 		- 00
			// Branch Greater Than 		- 10
			// Greater Than or Equal 	- 11
			bit checkEqual = copyOfInstruction.original.getArray()[5];
			bit checkGreater = copyOfInstruction.original.getArray()[4];
			// System.out.println("Branch\nCC: " + checkGreater.getValue() + checkEqual.getValue());

			// first check for checkEqual, if it 1, and 
			// isEqual is also 1, then read the number and store in result. 
			// isEqual is 0, then store the value zero in result. And tell store to just return once detect the zero

			// If checkEqual does not match, then check for checkGreater, if match, read and store, 
			//if does not match, store zero in result. 

			 

			return 0;
		}
		else if(decimalOpCode > 5) // ALU operation code
		{
			// // Assignment 6 code, with some renovation for easy to read purpose. 
			
			// I create a new longword and set the longword into all zero for intiating result.
			Impl_Longword aPrint = new Impl_Longword();
			aPrint.set(0);

			result = aPrint.original;

			// Set the value of result to the result of the ALU.
			result.setArray(ALU.doOp(opcode, op1, op2).getArray());
		}
		return 1;
	}
	
	/**
	 * This is the function use for store the value into register. Different type of action will be 
	 * perform to determine the type of data to store based on different op code.
	 * Halt: Automatic end this function.
	 * Move: Read the instruction for the index of register, and store the saved value into the register's index.
	 * Interrupt: Automatic end this function.
	 * ALU: Read the instruction for the index of register, and store the result value into the register's value. 
	 * 
	 * This method first read from the instruction to know which index of register to store
	 * the result. After that is known, the value of result is stored in the index of register.
	 * 
	 * @return 0 This happen when either halt or interrupt is encountered.
	 * @return 1 This happen when either move ALU is encountered and successfully performed. 
	 */
	public int store()
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
			System.out.println("The result: " + resultOfCompare);
			System.out.println("isGreater: " + isGreater.getValue() + "\nisEqual: " + isEqual.getValue());
		}
		else if(decimalOpCode == 5) // Branch operation code
		{
			System.out.println("Store: Branch");
			return 0;
		}
		else if(decimalOpCode > 5) // ALU operation code
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