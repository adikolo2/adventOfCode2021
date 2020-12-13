package Days;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class Day1 {

  public static void main(String[] args) throws IOException {
    BufferedReader bufferedReader = new BufferedReader(new FileReader("src/resources/Day1.txt"));

    List<Integer> entries = bufferedReader.lines().map(Integer::parseInt).collect(toList());

//    System.out.println("The product you are looking for is: "+ findTwoSumElements(entries));
    System.out.println("The product you are looking for is: "+ findThreeSumElements(entries));

    bufferedReader.close();
  }


  public static long findTwoSumElements(List<Integer> entries) {
    int desiredSum = 2020;
    long desiredProduct = 0;

    Map<Integer, Integer> entriesAndItsSubtractions = entries.stream().collect(
        toMap(Function.identity(), element -> desiredSum - element));

    Optional<Integer> firstOpt = entriesAndItsSubtractions.values().stream().filter(entries::contains).findFirst();
    if (firstOpt.isPresent()) {
      Integer first = firstOpt.get();
      Integer second = entriesAndItsSubtractions.get(first);

      desiredProduct = first * second;
    }

    return desiredProduct;
  }

  public static List<Integer> findTwoSumElementsCustomSum(List<Integer> entries, int desiredSum) {
    List<Integer> results = new ArrayList<>();

    Map<Integer, Integer> entriesAndItsSubtractions = entries.stream().distinct().collect(
        toMap(Function.identity(), element -> desiredSum - element));

    Optional<Integer> firstOpt = entriesAndItsSubtractions.values().stream().filter(entries::contains).findFirst();
    if (firstOpt.isPresent()) {
       results.add(firstOpt.get());
      results.add(entriesAndItsSubtractions.get(results.get(0)));

    }

    return results;
  }


  public static long findThreeSumElements(final List<Integer> originalEntries) {
    final Integer desiredSum = 2020;
    long desiredProduct = 0;

    List<Integer> sumsAfterFirstSubtraction = originalEntries.stream().map(element -> desiredSum - element).collect(toList());

    List<List<Integer>> subSumsAndItsElementsStepOne = new ArrayList<>();

    sumsAfterFirstSubtraction.forEach(sum -> {

      List<Integer> twoSumElementsCustomSum = findTwoSumElementsCustomSum(originalEntries, sum);
      if (!twoSumElementsCustomSum.isEmpty()) {
      subSumsAndItsElementsStepOne.add(twoSumElementsCustomSum);
      }
    });

    Map<Integer, List<Integer>> subSumsAndItsElementsStepTwo = subSumsAndItsElementsStepOne.stream().collect(toMap(
        element -> element.stream().reduce(Integer::sum).get(), Function.identity()));

    List<Integer> firstTwoElements = new ArrayList<>();

    Optional<Integer> thirdElementOpt = originalEntries.stream().filter(
        element -> subSumsAndItsElementsStepTwo.keySet().stream().anyMatch(
              subSum -> {
                boolean equals = Integer.valueOf(subSum + element).equals(desiredSum);
                if (equals) {
                  firstTwoElements.addAll(subSumsAndItsElementsStepTwo.get(subSum));
                }
                return equals;
              })).findFirst();
    firstTwoElements.forEach(subSumsAndItsElementsStepTwo::remove);
    if (thirdElementOpt.isPresent()) {
      Integer third = thirdElementOpt.get();
      desiredProduct = firstTwoElements.get(0) * firstTwoElements.get(1) * third;

    }


    return desiredProduct;
  }
}
