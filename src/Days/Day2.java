package Days;

import static java.util.stream.Collectors.toList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Day2 {

  public static void main(String[] args) throws IOException {
    BufferedReader bufferedReader = new BufferedReader(new FileReader("src/resources/Day2.txt"));

    List<PolicySet> entries = bufferedReader.lines().map(PolicySet::new).collect(toList());

    System.out.println("The number of valid password is: "+ countValidPasswords(entries));

    bufferedReader.close();
  }

  private static long countValidPasswords(List<PolicySet> policySet) {

    return policySet.stream().filter(Day2::isPasswordValidNew).count();
  }

  private static boolean isPasswordValidNew(PolicySet policySet) {

    int firstOccurrence = policySet.getRange()[0];
    int secondOccurrence = policySet.getRange()[1];
    String password = policySet.getPassword();
    return password.charAt(firstOccurrence - 1) == policySet.getLetter().charAt(0) ^ password.charAt(secondOccurrence - 1) == policySet.getLetter().charAt(0);
  }
  private static boolean isPasswordValidOld(PolicySet policySet) {

    int lower = policySet.getRange()[0];
    int higher = policySet.getRange()[1];
    String password = policySet.getPassword();
    long count = password.chars().filter(c -> c == policySet.getLetter().charAt(0)).count();
    return count>=lower && count<=higher;
  }



  private static class PolicySet{
    private  int[] range = new int[2];
    private final String letter;
    private String password;

    public PolicySet(String line) {
      String[] lineSplit = line.split(" ");
      this.range[0] = Integer.parseInt(lineSplit[0].split("-")[0]);
      this.range[1] = Integer.parseInt(lineSplit[0].split("-")[1]);

      this.letter = lineSplit[1].substring(0, 1);
      this.password = lineSplit[2];
    }

    /**
     * @return range
     */
    public int[] getRange() {

      return this.range;
    }

    /**
     * @return letter
     */
    public String getLetter() {

      return this.letter;
    }

    /**
     * @return password
     */
    public String getPassword() {

      return this.password;
    }
  }
}
