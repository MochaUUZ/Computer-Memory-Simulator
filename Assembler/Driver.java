/**
 * Driver.java
 * 
 * This is the driver class for running the test class for Assembler.
 */
public class Driver {

    /**
     * The method that will start the java program. 
     * @param args The set of command for starting the java program.
     * @throws Assembler.ParseError This happen when something unexpected happen when parsing. (i.e. char is not regonized, number read is out-of-bound)
     */
    public static void main(String[] args) throws Assembler.ParseError
    {
        Assembler_test toDoTest = new Assembler_test();
        toDoTest.runTest();

    } // End of main(String[])

} // End of Driver class
