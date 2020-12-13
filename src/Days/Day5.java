package Days;

import static java.util.stream.Collectors.toList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * Calculate Seat Id
 */
public class Day5 {

  public static void main(String[] args) throws IOException {
    BufferedReader bufferedReader = new BufferedReader(new FileReader("Day5.txt"));

    List<Seat> entries = bufferedReader.lines().map(Seat::new).collect(toList());

    System.out.println("The highest seat id is: "+ findHighestSeatId(entries)); //826

    List<Integer> allSeatIds = findAllSeatIdsSorted(entries);

    System.out.println("The missing seat id is: "+ findMissingSeatId(findAllSeatIdsSorted(entries))); // 678
    bufferedReader.close();
  }

  private static int findHighestSeatId(List<Seat> seats) {

    return seats.stream().mapToInt(Day5::calculateSeatId).max().orElse(-1);
  }

  private static List<Integer> findAllSeatIdsSorted(List<Seat> seats) {

    return seats.stream().map(Day5::calculateSeatId).sorted().collect(toList());
  }

  private static int findMissingSeatId(List<Integer> seatIdsSorted) {

    for (int i = 0; i < seatIdsSorted.size()-1; i++) {
      if (!Integer.valueOf(seatIdsSorted.get(i) + 1).equals(seatIdsSorted.get(i + 1))) {
        return seatIdsSorted.get(i) +1;
      }
    }
    return 0;
  }

  private static int calculateSeatId(Seat seat) {

    int rowNumber = calculateRowNumber(seat.rowCoded,0,127);
    int columnNumber = calculateRowNumber(seat.columnCoded,0,7);

    return 8*rowNumber+columnNumber;
  }

  private static int calculateRowNumber(String rowCoded, int lowerBoundary, int upperBoundary) {
    char[] rowArray = rowCoded.toCharArray();
    for (char c : rowArray) {
      if (c == 'F' || c == 'L') {
        upperBoundary = calculateLowerHalfOfTheRange(lowerBoundary, upperBoundary);
      }else{
        lowerBoundary = calculateUpperHalfOfTheRange(lowerBoundary, upperBoundary);
      }
    }
    return lowerBoundary;
  }

  private static int calculateLowerHalfOfTheRange(int lowerBoundary, int upperBoundary) {

    int remainder = upperBoundary - lowerBoundary;
    return remainder % 2 == 0 ? lowerBoundary + remainder / 2 +1 : lowerBoundary + remainder / 2;
  }
  private static int calculateUpperHalfOfTheRange(int lowerBoundary, int upperBoundary) {

    int remainder = upperBoundary - lowerBoundary;
    return remainder % 2 == 0 ? upperBoundary - remainder / 2 : lowerBoundary + remainder / 2 + 1;
  }

  private static class Seat{
    private  String rowCoded;
    private String columnCoded;

    public Seat(String codedSeat) {

      this.rowCoded = codedSeat.substring(0,7);
      this.columnCoded = codedSeat.substring(7, 10);
    }
  }

}
