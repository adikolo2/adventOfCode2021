package Days;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * --- Day 10: Adapter Array ---
 */
public class Day10 {

  public static void main(String[] args) throws IOException {

    BufferedReader bufferedReader = new BufferedReader(new FileReader("src/resources/Day10.txt"));

    List<Integer> allAdapters = bufferedReader.lines().map(Integer::valueOf).collect(Collectors.toList());
    allAdapters.add(0);
    allAdapters.add(allAdapters.stream().max(Integer::compareTo).get() + 3);
    allAdapters.sort(Integer::compareTo);

    System.out.println("The number of 1 and 3 diffrences multiplied is: " + countJoltsDifferences(allAdapters)); // 1856
    System.out.println("The number of all possible arrangements is: " + calculateAllPossibleArrangements(allAdapters)); // 2314037239808

    // BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("src/resources/Day10 - sorted.txt"));
    // for (Integer s : entries) {
    // bufferedWriter.append(s.toString());
    // bufferedWriter.newLine();
    // }
    // bufferedWriter.close();

    bufferedReader.close();
  }

  private static int countJoltsDifferences(List<Integer> adapters) {

    int oneJoltDiff = 0, threeJotlsDiff = 0;

    for (int i = 0, j = 1; j < adapters.size(); i++, j++) {

      int adaptersDiff = adapters.get(j) - adapters.get(i);
      switch (adaptersDiff) {
        case 1:
          oneJoltDiff++;
          break;
        case 3:
          threeJotlsDiff++;
          break;
        default:
          break;
      }
    }
    return oneJoltDiff * threeJotlsDiff;
  }

  private static long calculateAllPossibleArrangements(List<Integer> allAdapters) {

    return findAllTheWays(prepareAdaptersWithPossibleConnections(allAdapters), allAdapters);
  }

  private static long findAllTheWays(Map<Integer, List<Integer>> preparedAdapters, List<Integer> allAdapters) {

    Map<Integer, Long> adapterAndSumCache = new LinkedHashMap<>();
    adapterAndSumCache.put(allAdapters.get(allAdapters.size() - 1), 1L);
    for (int i = allAdapters.size() - 2; i > -1; i--) {
      Integer adapter = allAdapters.get(i);
      List<Integer> possibleConns = preparedAdapters.get(adapter);
      if (possibleConns.size() <= 1) {
        adapterAndSumCache.put(adapter, adapterAndSumCache.get(allAdapters.get(i + 1)));
      } else {
        adapterAndSumCache.put(adapter, possibleConns.stream().mapToLong(adapterAndSumCache::get).sum());
      }
    }
    return adapterAndSumCache.get(0);
  }

  private static Map<Integer, List<Integer>> prepareAdaptersWithPossibleConnections(List<Integer> allAdapters) {

    Map<Integer, List<Integer>> prepared = new LinkedHashMap<>();

    allAdapters.forEach(adapter -> {
      prepared.put(adapter, findPossibleConnections(adapter, allAdapters));
    });

    return prepared;
  }

  private static List<Integer> findPossibleConnections(Integer mainAdapter, List<Integer> allAdapters) {

    return allAdapters.stream().filter(adapter -> adapter - mainAdapter > 0 && adapter - mainAdapter <= 3)
        .collect(Collectors.toList());
  }

}
