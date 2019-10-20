import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

public class Main
{
  public static void main(String[] args)
  {
    ArrayList<int[]> sudoku = new ArrayList<>();
    try
    { //  Create input stream reader
      BufferedReader csvReader = new BufferedReader(new FileReader(args[0])); // File name taken in from command line
      String row = null;
      while ((row = csvReader.readLine()) != null) // Go through the file line by line
      {
          String[] input = row.split(","); // Parse values separated by commas
          int[] nums = new int[input.length];
          for (int i = 0; i < input.length; i++) // Convert parsed strings to int array
          {
            nums[i] = Integer.parseInt(input[i]);
          }
          sudoku.add(nums); // Add int array to our array list
      }
      csvReader.close(); // close the input stream reader
    }

    catch (Exception e)
    {
      e.printStackTrace(); // standard error checking
    }
    // Create array lists of error information from helper threads so the main thread can provide the final answer
    ArrayList<int[]> rowErrors = new ArrayList<>();
    ArrayList<int[]> columnErrors = new ArrayList<>();
    ArrayList<int[]> gridErrors = new ArrayList<>();

    try
    {
      Rows rowChecker = new Rows(sudoku);
      Columns columnChecker = new Columns(sudoku); // create threads for each validation method (row, column, 3x3 grid)
      Grid gridChecker = new Grid(sudoku);

      rowChecker.start();
      columnChecker.start(); // start the threads
      gridChecker.start();

      rowChecker.join();
      columnChecker.join(); // wait for all 3 threads to finish checking the given solution
      gridChecker.join();

      rowErrors = rowChecker.getErrors();
      columnErrors = columnChecker.getErrors(); // Set the array lists in main to the error info from the threads
      gridErrors = gridChecker.getErrors();
    }
    catch (Exception e)
    {
      e.printStackTrace(); // standard error checking
    }

    System.out.println(); // Formatting for an aggregate screenshot of all 6 test files
    if (rowErrors.size() == 0) // No errors reported
    {
      System.out.println("No errors found. Closing program\n"); // Sudoku solution validated; notify user and end program
    }
    else
    {
      for (int i = 0; i < rowErrors.size(); i++) // Go through all the errors
      {
        int rowIndex = rowErrors.get(i)[0];
        int columnIndex = columnErrors.get(i)[0]; // Get the indexes of the incorrect numbers
        int gridNumber = gridErrors.get(i)[0];
        System.out.println("Incorrect entry at: Row " + rowIndex + ", Column " + columnIndex + ", Grid " + gridNumber);
        System.out.println("The correct number at that location should be: " + rowErrors.get(i)[1] + "\n");
      } // Let the user know what went wrong and how to fix it
    }
  }
}
