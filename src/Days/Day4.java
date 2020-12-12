package Days;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Counts valid passports
 */
public class Day4 {

  private static final List<String> requiredFields = Arrays.asList("ecl", "eyr", "hcl", "pid", "iyr",  "byr", "hgt");

  private static final Predicate<String> BYR_PATTERN = Pattern.compile("(^19[2-9][0-9]$)|(^200[0-2]$)").asMatchPredicate();
  private static final Predicate<String>IYR_PATTERN = Pattern.compile("^201[0-9]$|^2020$").asMatchPredicate();
  private static final Predicate<String>EYR_PATTERN = Pattern.compile("^202[0-9]$|^2030$").asMatchPredicate();
  private static final Predicate<String>HGT_PATTERN = Pattern.compile("^1([5-8][0-9]|9[0-3])cm$|^(59|6[0-9]|7[0-6])in$").asMatchPredicate();
  private static final Predicate<String>HCL_PATTERN = Pattern.compile("^#([0-9]|[a-f]){6}$").asMatchPredicate();
  private static final Predicate<String>ECL_PATTERN = Pattern.compile("^amb$|^blu$|^brn$|^gry$|^grn$|^hzl$|^oth$").asMatchPredicate();
  private static final Predicate<String> PID_PATTERN = Pattern.compile("^\\d{9}$").asMatchPredicate();

  private static final Map<String, Predicate<String>> requiredFieldsWithValidation = new HashMap<>() {{
    put("byr", BYR_PATTERN);
    put("iyr", IYR_PATTERN);
    put("eyr", EYR_PATTERN);
    put("hgt", HGT_PATTERN);
    put("hcl", HCL_PATTERN);
    put("ecl", ECL_PATTERN);
    put("pid", PID_PATTERN);
  }};


  public static void main(String[] args) throws IOException {
    BufferedReader bufferedReader = new BufferedReader(new FileReader("Day4.txt"));
    List<String> collect = bufferedReader.lines().collect(Collectors.toList());
    List<String> concatenated =  parsePassports(collect);

    List<Passport> entries = concatenated.stream().map(Passport::new).collect(Collectors.toList());
//    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("Day4 - concatenated.txt"));
//    for (String s : concatenated) {
//      bufferedWriter.append(s);
//      bufferedWriter.newLine();
//    }
//  bufferedWriter.close();
    bufferedReader.close();


    System.out.println("The number of valid passports is: "+ countValidPassports_StepOne(entries));
    System.out.println("The number of valid REALLY passports is: "+ countValidPassports_StepTwo(entries));
  }



  private static long countValidPassports_StepOne(List<Passport> passports){

    return passports.stream().filter(Day4::isValid_StepOne).count();
  }


  private static boolean isValid_StepOne(Passport passport) {

    return requiredFields.stream().allMatch(passport.content::containsKey);

  }
  private static long countValidPassports_StepTwo(List<Passport> passports){

    return passports.stream().filter(Day4::isValid_StepTwo).count();
  }


  private static boolean isValid_StepTwo(Passport passport) {

    Map<String, String> passContent = passport.content;
    return requiredFieldsWithValidation.entrySet().stream().allMatch(
        (fieldAndValidation) -> passContent.containsKey(fieldAndValidation.getKey()) && fieldAndValidation.getValue().test(passContent.get(fieldAndValidation.getKey())));
  }

  private static List<String> parsePassports(List<String> collect) {
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



private static class Passport{
    private final Map<String, String > content;

  public Passport(String line) {

    String[] s = line.split(" ");
    this.content =Arrays.stream(s).map(element -> element.split(":")).collect(Collectors.toMap(pair -> pair[0],
        pair -> pair[1]));
  }

}




}
