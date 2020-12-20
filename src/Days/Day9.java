package Days;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * --- Day 9: Encoding Error ---
 */
public class Day9 {

  public static void main(String[] args) throws IOException {

    BufferedReader bufferedReader = new BufferedReader(new FileReader("src/resources/Day9.txt"));

    List<Long> entries = bufferedReader.lines().map(Long::parseLong).collect(toList());

    long notMatchingNumber = findNotMatchingNumber(entries);
    System.out.println("The first not matching number is: " + notMatchingNumber); // 552655238
    System.out.println("The sum is: " + findSumOfSmallestAndLargestNumberInTheSet(entries, notMatchingNumber));
    bufferedReader.close();
  }

  private static long findSumOfSmallestAndLargestNumberInTheSet(List<Long> entries, Long desiredSum) {

    for (int i = 0, j = 1; i < entries.size() && j < entries.size(); j++) {

      List<Long> rangeToCheck = entries.subList(i, j);
      long subtotal = rangeToCheck.stream().mapToLong(Long::longValue).sum();
      if (desiredSum.equals(subtotal)) {
        return rangeToCheck.stream().max(Long::compareTo).get() + rangeToCheck.stream().min(Long::compareTo).get();
      } else if (subtotal > desiredSum) {
        i++;
        j = i;
      }
    }
    return 0;
  }

  private static long findNotMatchingNumber(List<Long> entries) {

    for (int i = 0, j = 25; i < entries.size() & j < entries.size() + 25; i++, j++) {

      Long unmatchingElement = findTwoSumElementsCustomSum(entries.subList(i, j), entries.get(j));
      if (unmatchingElement > 0) {
        return unmatchingElement;
      }
    }
    return 0;
  }

  private static Long findTwoSumElementsCustomSum(List<Long> entries, Long desiredSum) {

    Map<Long, Long> entriesAndItsSubtractions = entries.stream().distinct()
        .collect(toMap(Function.identity(), element -> desiredSum - element));

    if (entriesAndItsSubtractions.values().stream().noneMatch(entries::contains)) {
      return desiredSum;
    }
    return -1L;
  }
}
