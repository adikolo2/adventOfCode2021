package Days;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

/**
 * --- Day 3: Binary Diagnostic ---
 */
public class Day3 {

    public static void main(String[] args) throws IOException {

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("src/resources/Day3.txt"))) {


            List<String> entries = bufferedReader.lines().collect(toList());

//            System.out.println("The value you are looking for is: " + calculatePowerConsumption(entries)); //
            System.out.println("The product you are looking for is: " + calculateLifeSupport(entries)); // 4432698
        }

    }


    private static long calculatePowerConsumption(List<String> binaries) {

        List<AtomicInteger> gammaRate = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            gammaRate.add(new AtomicInteger(0));
        }
        IntStream.range(0, 12).forEach(i ->
                binaries.forEach(binary ->
                        gammaRate.get(i).addAndGet((Integer.parseInt(binary.substring(i, i + 1))))));

        var gamma = new StringBuilder();
        var epsilon = new StringBuilder();
        gammaRate.forEach(sum -> {
            gamma.append(sum.intValue() >= 500 ? 1 : 0);
            epsilon.append(sum.intValue() >= 500 ? 0 : 1);
        });

        return (long) Integer.parseInt(gamma.toString(), 2) * Integer.parseInt(epsilon.toString(), 2);
    }

    private static long calculateLifeSupport(List<String> binaries) {

        List<AtomicInteger> gammaRate = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            gammaRate.add(new AtomicInteger(0));
        }
        IntStream.range(0, 12).forEach(i ->
                binaries.forEach(binary ->
                        gammaRate.get(i).addAndGet((Integer.parseInt(binary.substring(i, i + 1))))));

        final var bitCriteria = gammaRate.stream()
                .map(atomicInteger -> atomicInteger.intValue() >= 500 ? "1" : "0")
                .collect(toList());

        final var oxygen = findOxygenGeneratorRating(binaries, bitCriteria.get(0), 0);
        final var co2Scrubber = findC02ScrubberRating(binaries, bitCriteria.get(0).equals("1") ? "0" : "1", 0);
        return (long) Integer.parseInt(oxygen.get(0).toString(), 2) * Integer.parseInt(co2Scrubber.get(0).toString(), 2);
    }

    private static String calculateBitCriteriaOxygen(List<String> binaries, int position) {
        if (binaries.size() < 2) {
            return "";
        }
        final AtomicInteger frequencyOfTheBit = new AtomicInteger(0);
        binaries.forEach(binary -> frequencyOfTheBit.addAndGet(Integer.parseInt(binary.substring(position, position + 1))));
        return frequencyOfTheBit.intValue() * 2 >= binaries.size() ? "1" : "0";
    }

    private static String calculateBitCriteriaCo2Scrubber(List<String> binaries, int position) {
        if (binaries.size() < 2) {
            return "";
        }
        final AtomicInteger frequencyOfTheBit = new AtomicInteger(0);
        binaries.forEach(binary -> frequencyOfTheBit.addAndGet(Integer.parseInt(binary.substring(position, position + 1))));
        return frequencyOfTheBit.intValue() * 2 >= binaries.size() ? "0" : "1";
    }

    private static List<String> findOxygenGeneratorRating(List<String> binaries, String bitCriteria, int positionCounter) {
        if (binaries.size() == 1) {
            return binaries;
        }
        final var positionIncremented = positionCounter + 1;
        final var filteredBinaries = binaries.stream()
                .filter(binary -> bitCriteria.equals(binary.substring(positionCounter, positionIncremented)))
                .collect(toList());
        return findOxygenGeneratorRating(filteredBinaries, calculateBitCriteriaOxygen(filteredBinaries, positionIncremented), positionIncremented);
    }

    private static List<String> findC02ScrubberRating(List<String> binaries, String bitCriteria, int positionCounter) {
        if (binaries.size() == 1) {
            return binaries;
        }
        final var positionIncremented = positionCounter + 1;
        final var filteredBinaries = binaries.stream()
                .filter(binary -> bitCriteria.equals(binary.substring(positionCounter, positionIncremented)))
                .collect(toList());
        return findC02ScrubberRating(filteredBinaries, calculateBitCriteriaCo2Scrubber(filteredBinaries, positionIncremented), positionIncremented);
    }

}
