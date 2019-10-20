import java.util.stream.IntStream;
import java.util.ArrayList;

public class Rows extends Thread
{
  private int[][] solution = new int[9][9];
  private ArrayList<int[]> errors = new ArrayList<>();

  public Rows(int[][] sudoku)
  {
    solution = sudoku; // Set our parsed sudoku grid to a local variable we can work with
  }

  public void run()
  {
    for(int i = 0; i < 9; i++)
    {
      int[] row = solution[i]; // Grab one row at a time
      for (int j = 0; j < 9; j++) // Iterate through each row
      {
        int num = j + 1; // Check each number 1-9
        if (!IntStream.of(row).anyMatch(x -> x == num)) // Number does not show up in the row
        {
          int[] error = {i + 1, num}; // Keep track of the row and number missing
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
