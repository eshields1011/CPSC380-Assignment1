import java.util.ArrayList;
import java.util.stream.IntStream;

public class Grid extends Thread
{
  private ArrayList<int[]> solution = new ArrayList<>();
  public ArrayList<int[]> errors = new ArrayList<>();

  public Grid(ArrayList<int[]> sudoku)
  {
    solution = sudoku; // Set our parsed sudoku grid to a local variable we can work with
  }

  public void run()
  {
    for(int i = 0; i < 9; i++)
    {
      int[] grid = new int[9]; // Array to hold the 3x3 grid values
      int index = 0;

      for (int m = 0; m < 3; m++)
      {
        for (int n = 0; n < 3; n++)
        {
          int r = ((i / 3) * 3) + m; // specifies the 3x3 grid's row indexes
          int c = ((i % 3) * 3) + n; // specifiex the 3x3 grid's column indexes
          grid[index++] = solution.get(r)[c];
        }
      }

      for (int j = 0; j < 9; j++) // Iterate through each number in the grid
      {
        int num = j + 1; // Check each number 1-9
        if (!IntStream.of(grid).anyMatch(x -> x == num)) // Number does not show up in the grid
        {
          int[] error = {i + 1, num}; // Keep track of the grid and the number missing in that grid
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
