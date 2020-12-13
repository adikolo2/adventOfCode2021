package Days;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * --- Day 6: Custom Customs ---
 * Counting customs declaration answers - Part One
 */
public class Day6 {

  public static void main(String[] args) throws IOException {
    BufferedReader bufferedReader = new BufferedReader(new FileReader("src/resources/Day6.txt"));
    List<String> collect = bufferedReader.lines().collect(Collectors.toList());

    List<String> groupedTogether = groupSingleForm(collect);

    System.out.println("The sum of all positive answered questions is: "+ countNumberOfDistinctQuestions(groupedTogether)); //6686

    bufferedReader.close();
  }

  private static long countNumberOfDistinctQuestions(List<String> forms) {

    return forms.stream().mapToLong(Day6::countSingleForm).sum();
  }

  private static long countSingleForm(String form) {

    return Arrays.stream(form.split("")).distinct().count();
  }

  private static List<String> groupSingleForm(List<String> collect) {
    List<String> concatenated = new ArrayList<>();

    StringBuilder sb = new StringBuilder();
    for (String s : collect) {
      if (!s.isEmpty()) {
        sb.append(s);
      }else{
        concatenated.add(sb.toString());
        sb = new StringBuilder();
      }

    }
    concatenated.add(sb.toString());
    return concatenated;
  }




}
