/**
 * Assembler.java
 * 
 * This is the implementation of an assembler. 
 * 
 * 11/07/2020   Researched/brain-stormed a little about Recursive Descent Parser(RDP). Didn't found much useful info. :(
 * 
 * 11/08/2020   Continue researched about RDP, ended up found a very useful reference from a TXT-book author.
 *              http://math.hws.edu/javanotes/c9/s5.html - - -
 *              (Introduction to Programming Using Java 8th edition, CP 9, section 9.5, author:Dr. David J. Eck)
 * 
 * 11/09/2020   Starts implementing the Assembler with RDP. Wrote a BNF-grammer that describe the instructions. 
 *              <Instruction>   ~>  (<opCode> + <Details>)
 *              <Details>       ~>  <Registers> || <number> || (<Details> + <Details>)
 *              <Registers>     ~>  ('R' + <number>)
 *              <number>        ~>  0 || 1 || 2 || 3 || 4 || 5 || 6 || 7 || 8 || ... 
 *              <opCode>        ~>  "Add" || "Subtract" || "Move" "Left_Shift" || ... 
 * 
 * 11/10/2020   Continue on implementing Assembler with RDP, but end up having trouble to effectively 
 *              using recursion when parsing. After many decision, decide to use an iterative approached for parsing.
 *              End up finishing the implementing of Assembler at the end of the day. 
 *              However, only minimal testing had been implemented. 
 * 
 * 11/11/2020   Finishing the testing. Fixed some bugs on the Assembler. Added some touch-up and renovated the overall 
 *              format of output. Fixed some missing identation on the code. 
 * --------- FINAL EDITION --------- 
 * --------- FINAL EDITION --------- 
 * --------- FINAL EDITION --------- 
 * --------- FINAL EDITION --------- 
 * @author Sheng hao Dong
 */
public class Assembler {

    /**
     * the class for handle all the possible error while parsing.
     */
    public static class ParseError extends Exception
    {
        private static final long serialVersionUID = 1L;

        ParseError(String message)
        {
            super(message);
        }
    } // end of ParseError

    /**
     * This is the main method of assembler. This method will be responsible for continue parsing 
     * the input and an " "(space) is being parsing, this method will define whether the item being
     * parsed is an number, register, or keyword and call the appropriate methods to deal with the item. 
     * @param instruction This is the series of instruction that this method will Parser.
     * @return The series of the tranformed instruction that is in machine language.
     * @throws ParseError This happen when something unexpected happen when parsing. (i.e. char is not regonized, number read is out-of-bound)
     */
    public static String[] Parser(String[] instruction) throws ParseError
    {
        // The array that will hold for the result of each successful parsing. 
        String[] outputInstruction = new String[instruction.length];

        // A loop that will conduct parsing to each individual instruction.
        for(int i = 0; i < instruction.length; i++)
        {
            String copyOfInstr = instruction[i].trim();     // A copy of current individual instruction.
            StringBuilder result = new StringBuilder();     // This will hold for the machine code results from the parsing.
            StringBuilder theCode = new StringBuilder();    // This will hold each "small part of instruction"
            while(copyOfInstr.length() > 0) // while the instruction have more to parse.
            {
                char ch = peek(copyOfInstr); // See the descrption of peek(String)
                if(Character.isLetterOrDigit(ch) || ch == '-' || ch == '_') 
                {
                    // Store all the character while a ' '(space) is not encounter.
                    theCode.append(ch); 
                    copyOfInstr = push(copyOfInstr);
                }
                else if(ch == ' ')
                {
                    // A ' '(space) is encounted, give the item parsed to evaluate to decide whether the 
                    // item is keyword, register, or number. 
                    String forEval = Evaluate(theCode.toString());
                    result.append(forEval);
                    copyOfInstr = push(copyOfInstr);
                    theCode = new StringBuilder();
                }
                else 
                {
                    throw new ParseError("Unexpected char is read. Not a letter, digit, or a space(' ').");
                }
            }
            if(result.length() > 16) // If the machine code generated is greater than the max size of an instruction(16 bit size). 
            {
                throw new ParseError("Invalid instruction! Too much command in one instruction. ");
            }

            // If there is any remain command not being identify, finish them!!
            if(theCode.length() > 0)
            {
                String lastEval = Evaluate(theCode.toString());
                result.append(lastEval);
            }
            // Store the successful machine code and continue to parse next instruction :)
            outputInstruction[i] = result.toString();
        }

        return outputInstruction;
    } // end of Parser(String) 

    /**
     * This method will help parse(String[]) to identify whether the commands parsed is a 
     * keyword, register, number, or Invalid Command.
     * @param theWord The command from Instruction(i.e. Add, R2, -100, Subtract, Move, ...)
     * @return The machine code representation of each command.
     * @throws ParseError This happen when something unexpected happen when parsing. (i.e. char is not regonized, number read is out-of-bound)
     */
    public static String Evaluate(String theWord) throws ParseError
    {
        char FirstCh = theWord.charAt(0); 

        if( (FirstCh == 'R' || FirstCh == 'r') && Character.isDigit(theWord.charAt(1)) )
        {
            // The command is a registers. Hand the command to a dedicated method just for handling register.
            return Registers(theWord);
        }
        else if(Character.isDigit(FirstCh) || FirstCh == '-')
        {
            // The command is a number. But before call the dedicated method for handling number, 
            // we need to check if the number is in-bound. 
            try
            {
                // Check if the number is in-bound. If not, throw an error.
                int decimalF = Integer.parseInt(theWord);
                if(decimalF > 127 || decimalF < -127) 
                {
                    // Since we are using 8 bit signed binary number, we actually only have 7 bit to 
                    // represent a number. 
                    throw new ParseError("Error: The number is out-of-bound. " + decimalF);
                }
                else
                {
                    // The number passed the check, call the dedicated method just for handling number.
                    return number(decimalF, 8);
                }
            }
            catch(NumberFormatException e)
            {
                System.out.println("Error: Your string contains non-digits.");
            }
        }
        else if(Character.isLetter(FirstCh))
        {
            // The command is a opcode. Hand the command to a dedicated method just for handling register.
            return opCode(theWord);
        }

        // *** This method should not go pass beyond this point ***
        return theWord;
    } // end of Evaluate(String)

    /**
     * This method is the method that just for dealing with register.
     * @param registerIndex The index of register.
     * @return The machine code format of the index of register.
     * @throws ParseError This happen when something unexpected happen when parsing. (i.e. char is not regonized, number read is out-of-bound)
     */
    public static String Registers(String registerIndex) throws ParseError
    {
        char FirstC = peek(registerIndex);

        // This will eliminate the 'R' in front of the number, since we already know this is register, 
        // we don't need to 'R' anymore.
        if(Character.isLetter(FirstC))
        {
            String newIndex = push(registerIndex);
            return Registers(newIndex);
        }
        else
        {
            try 
            {
                // If the index of register is in-bound.
                int RIndex = Integer.parseInt(registerIndex);
                if(RIndex > 15 || RIndex < 0)
                {
                    throw new ParseError("Error: Register index is out-of-bound. " + "R" + RIndex);
                }
                // The index of register pass the check, call the method dedicated for dealing with numbers. 
                // Since it is number associate with Register, we want a 4 bit format machine code.
                return number(RIndex, 4);
            }
            catch(NumberFormatException e)
            {
                System.out.println("Error: Your string contains non-digits.");
            }
        }

        // *** This method should not go pass beyond this point ***
        return "Error!";
    } // end of Registers(String)

    /**
     * This method is dedicated for dealing with opCode. 
     * @param theOpCode The opCode command
     * @return The machine language format of the opCode.
     * @throws Assembler.ParseError This happen when something unexpected happen when parsing. (i.e. char is not regonized, number read is out-of-bound)
     */
    public static String opCode(String theOpCode) throws Assembler.ParseError
    {
        // To prevent different case but it the same word.
        String toLowerCase = theOpCode.toLowerCase();

        // Based on the opCode, return each represetation of opCode.
        switch (toLowerCase)
        {
            case "add": 
                return "1110";
            case "subtract":
                return "1111";
            case "multiply":
                return "0111";
            case "and":
                return "1000";
            case "or":
                return "1001";
            case "xor":
                return "1010";
            case "not":
                return "1011";
            case "left_shift":
                return "1100";
            case "right_shift":
                return "1101";
            case "interrupt":
                return "00100000";
            case "move":
                return "0001";
            case "halt":
                return "0000000000000000";
            default: 
                throw new ParseError("Your opCode doesn't exist, or spell wrong. " + toLowerCase);
        }
    } // end of opCode(String)

    /**
     * This method is dedicated for dealing with number.
     * @param decimalNumber The number
     * @param bitSize The bitsize you want the machine code representation to be.
     * @return The machine code representation of the number.
     * @throws Assembler.ParseError This happen when something unexpected happen when parsing. (i.e. char is not regonized, number read is out-of-bound)
     */
    public static String number(int decimalNumber, int bitSize) throws Assembler.ParseError
    {
        int [] binary;

        // Decide which bit size to use for storing.
        if(bitSize > 0 && bitSize <= 4)
        {
            binary = new int[4];
        }
        else if(bitSize > 4 && bitSize <= 8)
        {
            binary = new int[8];
        }
        else
        {
            throw new ParseError("Error: Can not have bitSize greater than 8 or lower than 0");
        }

        // Decide whether the number is positive or negative.
        boolean positive = true;
        if(decimalNumber >= 0)
        {
            positive = true; // This is a positive number.
        }
        else if(decimalNumber < 0)
        {
            positive = false; // This is a negative number.
            decimalNumber = Math.abs(decimalNumber) - 1; // Get ready for conversion.
        }

        // Initialize the int array. 
        binary = fillBit(binary);
        // We will start fill in the binary format according to the algorithm.
        int charLength = binary.length - 1;

        // Start fill the machine code format.
        while(decimalNumber > 0)
        {
            int result = decimalNumber % 2;
            decimalNumber = decimalNumber / 2;
            binary[charLength] = result;
            charLength--;
        }

        // Copy the machine code from int array to String [] so the return type match. 
        char[] binaryRepresentation = new char[binary.length];
        for(int i = 0; i < binaryRepresentation.length; i++)
        {
            int intRepre = binary[i];
            if(positive == false)
            {
                intRepre = Math.abs(intRepre - 1);
            }

            char cRepre = (char)(intRepre + '0');
            binaryRepresentation[i] = cRepre;
        }
        
        String finalResult = new String(binaryRepresentation);
        return finalResult;

    } // end of number(int, int)

    /**
     * This is the method that will be responsible for initialize the int arry use for number(int, int)
     * @param emptyChar The int array that should be empty. 
     * @return The initialized complete int array.
     */
    public static int[] fillBit(int[] emptyChar)
    {
        int[] filledB = new int[emptyChar.length];
        for(int i = 0; i < emptyChar.length; i++)
        {
            filledB[i] = 0;
        }
        return filledB;
    } // end of fillBit(char[])

    /**
     * This method will return the first char of the string.
     * @param toPeek The String you want to peek at.
     * @return The first char the String toPeek
     * @throws ParseError This happen when something unexpected happen when parsing. (i.e. char is not regonized, number read is out-of-bound)
     */
    public static char peek(String toPeek) throws ParseError
    {
        char ch = toPeek.charAt(0);
        if(ch == '\n' || toPeek == null)
        {
            throw new ParseError("The string is empty or null!. ");
        }
        else
        {
            return ch;
        }
    } // end of peek(String)

    /**
     * This method will push the first char of a String. 
     * Similar to a stack datatype, after push is call on a String, the second char at that String become the 1st char.
     * @param toPush The String to push.
     * @return The first char of a String.
     * @throws ParseError This happen when something unexpected happen when parsing. (i.e. char is not regonized, number read is out-of-bound)
     */
    public static String push(String toPush) throws ParseError
    {

        if(toPush.length() == 0 || toPush == null)
        {
            throw new ParseError("There is nothing in this string to push.");
        }
        else
        {
            String newToPush = toPush;
            newToPush = newToPush.substring(1);
            return newToPush;
        }
    } // end of push(String)

} // end of class Assembler class
