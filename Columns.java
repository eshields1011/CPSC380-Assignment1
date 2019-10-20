import java.util.stream.IntStream;
import java.util.ArrayList;

public class Columns extends Thread
{
  private int[][] solution = new int[9][9];
  public ArrayList<int[]> errors = new ArrayList<>();

  public Columns(int[][] sudoku)
  {
    solution = sudoku; // Set our parsed sudoku grid to a local variable we can work with
  }

  public void run()
  {
    for(int i = 0; i < 9; i++)
    {
      int[] column = new int[9]; // Array to hold the column values
      for (int m = 0; m < 9; m++)
      {
        column[m] = solution[m][i]; // Builds the columns properly from our ArrayList
      }
      for (int j = 0; j < 9; j++) // Iterate through each column
      {
        int num = j + 1; // Check each number 1-9
        if (!IntStream.of(column).anyMatch(x -> x == num)) // Number does not show up in the column
        {
          int[] error = {i + 1, num}; // Keep track of the column and the number missing in that column
          errors.add(error);
        }
      }
    }
  }

  public ArrayList<int[]> getErrors() // Accessor method for the ArrayList of error information
  {
    return errors;
  }
}
