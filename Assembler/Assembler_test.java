/**
 * Assembler_test.java 
 * 
 * This is the test class for testing the Assembler class.
 * 
 * 11/11/2020 Created this class, and finish creating two unit tests to test Assembler.java
 * @author Sheng hao Dong
 */
public class Assembler_test {

    /**
     * This is the method use to test the Assembler. 
     * It will output four tabs: 
     * Input | Output | Expect | Equality
     * Input   : This is the instruction in word form, before transform. 
     * Output  : This is the instruction in bit form, after transform.
     * Expect  : This is the expected instruction after the transform. 
     * Equality: This is the result for checking if output is equal to expected.
     *    \_~>  True : Expect and output instruction is the same.
     *     \_~> False: Expect and output instruction is different.
     * @throws Assembler.ParseError This happen when something unexpected happen when parsing. (i.e. char is not regonized, number read is out-of-bound)
     */
    public void runTest() throws Assembler.ParseError
    {
        System.out.println("\nUnit Test #1");
        unitTest1();

        System.out.println("\nUnit Test #2");
        unitTest2();
        
    } // end of runTest()

    /**
     * This method hold the first set of datas that will use for testing the assembler.
     * @throws Assembler.ParseError This happen when something unexpected happen when parsing. (i.e. char is not regonized, number read is out-of-bound)
     */
    public static void unitTest1() throws Assembler.ParseError
    {
        // The instruction to input. 
        String[] Input =    {"Move R0 -1           ", "Move R1 -50          ", "Move R2 89           ",
                            "Add R0 R2 R3         " , "Subtract R4 R1 R4    ", "Multiply R5 R0 R5    ", 
                            "And R1 R6 R6         " , "Or R4 R3 R7          ", "Xor R6 R7 R8         ", 
                            "Not R7 R0 R9         " , "Left_shift R7 R3 R10 ", "Right_shift R8 R3 R11", 
                            "Interrupt 0          " , "Interrupt 1          ", "Halt                 " };
                            
        // The expected answer from the input.
        String[] Expected = {"0001000011111111", "0001000111001110", "0001001001011001", 
                             "1110000000100011", "1111010000010100", "0111010100000101",
                             "1000000101100110", "1001010000110111", "1010011001111000",
                             "1011011100001001", "1100011100111010", "1101100000111011",
                             "0010000000000000", "0010000000000001", "0000000000000000",};

        // This is the MEAT of the test. This String[] will be the output of the transform. 
        String[] Output = Assembler.Parser(Input);
    
        // For formatting the answers for better readability.
        System.out.println("\nInput        ------------------------------      Output      ---------------------       Expect         -------------   Equality");

        // For the tab "Equality" (Refer to the documentation comment for this method).
        boolean equals = true;

        // The loop which will print out each element from array input, output, expected, and check to see if 
        // output and expected are equals. 
        for(int j = 0; j < Input.length; j++)
        {
            if(Output[j].equals(Expected[j]))
            {
                equals = true;
            }
            else
            {
                equals = false;
            }

            System.out.println(Input[j] + "         -----         " + Output[j] + "         -----         " + Expected[j] + "         -----         " + equals);
        }

        System.out.println("\n---------------------------------------------------------------------------------------------------------------------------------");

    } // end of unitTest1()

    /**
     * This method hold the second set of datas that will use for testing the assembler.
     * @throws Assembler.ParseError This happen when something unexpected happen when parsing. (i.e. char is not regonized, number read is out-of-bound. )
     */
    public static void unitTest2() throws Assembler.ParseError
    {
        // The instruction to input. 
        String[] Input =    {"Move R15 1           ", "Move R14 55          ", "Move R13 0           ",
                            "Add R15 R14 R12      " , "Subtract R14 R13 R11 " , "Multiply R14 R15 R10 ", 
                            "And R10 R15 R9       " , "Or R14 R12 R8        " , "Xor R13 R10 R7       ", 
                            "Not R7 R10 R6        " , "Left_shift R7 R13 R5 " , "Right_shift R8 R13 R4", 
                            "Interrupt 0          " , "Interrupt 1          " , "Halt                 "};

        // The expected answer from the input.
        String[] Expected = {"0001111100000001", "0001111000110111", "0001110100000000", 
                             "1110111111101100", "1111111011011011", "0111111011111010",
                             "1000101011111001", "1001111011001000", "1010110110100111",
                             "1011011110100110", "1100011111010101", "1101100011010100",
                             "0010000000000000", "0010000000000001", "0000000000000000",};

        // This is the MEAT of the test. This String[] will be the output of the transform. 
        String[] Output = Assembler.Parser(Input);
    
        // For formatting the answers for better readability.
        System.out.println("\nInput        ------------------------------      Output      ---------------------       Expect         -------------   Equality");

        // For the tab "Equality"
        boolean equals = true;

        // The loop which will print out each element from array input, output, expected, and check to see if 
        // output and expected are equals.
        for(int j = 0; j < Input.length; j++)
        {
            if(Output[j].equals(Expected[j]))
            {
                equals = true;
            }
            else
            {
                equals = false;
            }

            System.out.println(Input[j] + "         -----         " + Output[j] + "         -----         " + Expected[j] + "         -----         " + equals);
        }

        System.out.println("\n---------------------------------------------------------------------------------------------------------------------------------");

        // For testing out-of-bound input, if assembler will tell user the error. 
        String[] testError = {"add R16 R-9 R2048"};
        String[] towardError = Assembler.Parser(testError);

        // The code should not reach pass this points. Because an error would be throw while parsing 
        // because if look at String, "testError", none of the registers is valid.
        System.out.println(towardError);

    } // end of unitTest2()

} // end of Assembler_test class