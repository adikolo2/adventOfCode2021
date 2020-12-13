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
 * Counting customs declaration answers - Part Two
 */
public class Day6PartTwo {

  public static void main(String[] args) throws IOException {
    BufferedReader bufferedReader = new BufferedReader(new FileReader("src/resources/Day6.txt"));
    List<String> collect = bufferedReader.lines().collect(Collectors.toList());

    List<String> groupedTogether = groupSingleForm(collect);
    List<Group> entries = groupedTogether.stream().map(Group::new).collect(Collectors.toList());


    System.out.println("The sum of all positive answered questions is: "+ countNumberOfCommonQuestions(entries)); //3476

    bufferedReader.close();
  }

  private static long countNumberOfCommonQuestions(List<Group> groups) {

    return groups.stream().mapToLong(Day6PartTwo::countInSingleGroup).sum();
  }

  private static long countInSingleGroup(Group group) {

    List<String> baseList = new ArrayList<>(group.forms.get(0));
    for (List<String> form : group.forms) {
      baseList.retainAll(form);
    }
    return baseList.size();
  }

  private static List<String> groupSingleForm(List<String> collect) {
    List<String> concatenated = new ArrayList<>();

    StringBuilder sb = new StringBuilder();
    for (String s : collect) {
      if (!s.isEmpty()) {
        sb.append(s);
        sb.append(" ");
      }else{
        concatenated.add(sb.toString());
        sb = new StringBuilder();
      }

    }
    concatenated.add(sb.toString());
    return concatenated;
  }

  private static class Group {
    private final List<List<String>> forms;

    public Group(String line) {
      String[] s = line.split(" ");
      this.forms =
          Arrays.stream(s).map(singleForm-> Arrays.asList(singleForm.split(""))).collect(Collectors.toList());
    }

  }


}
