public class cpu_test1 {

    /**
     * This is the method which will test the computer class.
     */
    public void runTest() throws Exception
    {
        System.out.println("Test 1\n");

        // This is the instruction for the memory. 
        // Each beginning 4 bit tells you the operation code.
        // 0001 - move  0000 - Halt 0010.....0000 - Interrupt 0  0010....00001 - Interrupt Other format - ALU related
        String[] dataForMemory1 = { "0001000000000001", "0001000100000011", "1110000000010010",
                                    "0001001100001010", "0001010000001010", "0111001101000101",
                                    "0001011000111111", "0001011100111111", "1111011001111000",
                                    "0010000000000000", "0010000000000001", "0000000000000000"};

        // I create a new computer instance according to the assignment.
        computer newComputer = new computer();
        newComputer.preload(dataForMemory1);

        // I run the function by calling the testing function
        newComputer.run();

        System.out.println("\nTest 2\n");

        // Please refer to the top for the description of the following code.

        String[] dataForMemory2 = { "0001000001011111", "0001000100000010", "1011000000010010",
                                    "0001001100000111", "1101000000010100", "1001000001000101",
                                    "1010000000110110", "1000000001000111", "0010000000000000",
                                    "0010000000000001", "0000000000000000"};
                                    
        computer newComputer1 = new computer();
        newComputer1.preload(dataForMemory2);

        newComputer1.run();
    }
}