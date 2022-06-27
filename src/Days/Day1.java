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
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Count the number of times a depth measurement increases from the previous measurement.
 */
public class Day1 {

    public static void main(String[] args) throws IOException {

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("src/resources/Day1.txt"))) {


            List<Integer> entries = bufferedReader.lines().map(Integer::parseInt).collect(toList());

            System.out.println("The product you are looking for is: " + countIncreasedValues(entries)); // 1195
            System.out.println("The product you are looking for is: " + countSummedUpIncreasedValues(entries)); //1235
        }

    }

    private static long countIncreasedValues(List<Integer> entries) {

        return IntStream.range(0, entries.size() - 1).filter(i -> entries.get(i + 1) > entries.get(i)).count();
    }

    private static long countSummedUpIncreasedValues(List<Integer> entries) {

        List<Integer> sums = new ArrayList<>();
        IntStream.range(0, entries.size() - 2).forEach(i -> {
            sums.add(entries.get(i) + entries.get(i + 1) + entries.get(i + 2));

        });
        return countIncreasedValues(sums);
    }


}
