package Days;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

// #### Part One movements ####
//   right 3, down 1

// #### Part Two movements ####
//    Right 1, down 1.
//    Right 3, down 1. (This is the slope you already checked.)
//    Right 5, down 1.
//    Right 7, down 1.
//    Right 1, down 2.

public class Day3 {

  private static final AtomicInteger stepCounter = new AtomicInteger(0);

  public static void main(String[] args) throws IOException {
    BufferedReader bufferedReader = new BufferedReader(new FileReader("src/resources/Day3.txt"));

    List<TreeLine> entries = bufferedReader.lines().map(TreeLine::new).collect(Collectors.toList());
    List<Movement> movements = new LinkedList<>();
    movements.add(new Movement(1, 1));
    movements.add(new Movement(3, 1));
    movements.add(new Movement(5, 1));
    movements.add(new Movement(7, 1));
    movements.add(new Movement(1, 2));

//    System.out.println("The number of hit trees is: "+ countTreesOnTheWay(entries));
    System.out.println("The number of the multiplied hit trees is: "+ countTreesOnTheWay(movements, entries));

    bufferedReader.close();
  }

  private static long countTreesOnTheWay(List<Movement> movements, List<TreeLine> treeLines) {
    long multiplication = 1;
    for (final Movement move : movements) {
      stepCounter.set(0);
      long sum = IntStream.range(0, treeLines.size())
              .filter(index -> (index % move.down) == 0)
              .filter(index2 -> hitATree(treeLines.get(index2), move.right)).count();

      if (sum != 0) {
        multiplication *= sum;
      }
    }

    return multiplication;
  }


  private static boolean hitATree(TreeLine treeLine, int step) {

    int currentPosition = stepCounter.getAndAdd(step);
    return treeLine.treePositions.contains(calculatePositionWithinRange(treeLine, currentPosition));
  }

  private static long countTreesOnTheWay( List<TreeLine> treeLines) {

    return treeLines.stream().filter(treeLine -> hitATree(treeLine, 3)).count();
  }

  private static int calculatePositionWithinRange(TreeLine treeLine, int currentPosition) {

    if (currentPosition < treeLine.length) {
      return currentPosition;
    }
      int subtracted = currentPosition - treeLine.length;
    return subtracted < treeLine.length ? subtracted : calculatePositionWithinRange(treeLine, subtracted);
  }

  private static class TreeLine {
  private final List<Integer> openSquarePositions;
  private final List<Integer> treePositions;
  private final int length;

    public TreeLine(String line) {

      this.openSquarePositions = new LinkedList<>();
      this.treePositions = new LinkedList<>();
      this.length = line.length();

      IntStream.range(0,line.length()).forEach(index->{
        if (line.charAt(index) == '.') {
          this.openSquarePositions.add(index);
        }else {
          this.treePositions.add(index);
        }
      });
    }
  }

    private static class Movement {
      final int  right;
      final int down;

      public Movement(int right, int down) {

        this.right = right;
        this.down = down;
      }
    }





}
