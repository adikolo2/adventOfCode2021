package Days;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Array;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;

/**
 * --- Day 4: Giant Squid ---
 */
public class Day4 {

    public static void main(String[] args) throws IOException {

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("src/resources/Day4.txt"))) {


            List<List<Integer>> entries = bufferedReader.lines().filter(Predicate.not(String::isBlank))
                    .<List<Integer>>mapMulti((line, consumer) -> {
                        List<Integer> list = new ArrayList<>();
                        final var s = line.split(" ");
                        for (String s1 : s) {
                            if (!s1.isBlank()) {
                                list.add(Integer.parseInt(s1));
                            }
                        }
                        if (list.size() != 5) throw new IllegalStateException("Wrong size!");
                        consumer.accept(list);
                    })
                    .collect(toList());
            List<Integer> numbers = new ArrayList<>(Arrays.asList(10, 80, 6, 69, 22, 99, 63, 92, 30, 67, 28, 93, 0, 50, 65, 87, 38, 7, 91, 60, 57, 40, 84, 51, 27, 12, 44, 88, 64, 35, 39, 74, 61, 55, 31, 48, 81, 89, 62, 37, 94, 43, 29, 14, 95, 8, 78, 49, 90, 97, 66, 70, 25, 68, 75, 45, 42, 23, 9, 96, 56, 72, 59, 32, 85, 3, 71, 79, 18, 24, 33, 19, 15, 20, 82, 26, 21, 13, 4, 98, 83, 34, 86, 5, 2, 73, 17, 54, 1, 77, 52, 58, 76, 36, 16, 46, 41, 47, 11, 53));
//            findFirstSolution(entries, numbers);
            findSecondSolution(entries, numbers);
        }

    }

    private static void findFirstSolution(List<List<Integer>> entries, List<Integer> numbers) {
        final var boards = prepareData(entries);
        final int[] winningNumber = new int[1];
        final List<List<List<Integer>>> winningBoard = new ArrayList<>();
        try {

            numbers.forEach(guess -> {

                for (Map<List<List<Integer>>, AtomicInteger> board : boards) {
                    board.keySet().stream().forEach(entry -> {

                        entry.forEach(row -> {

                            row.remove(guess);
                            if (row.size() == 0) {
                                winningNumber[0] = guess;
                                winningBoard.add(board.keySet().stream().findFirst().get());
                                throw new IllegalStateException();
                            }
                        });
                    });
                }
            });
        } catch (Exception e) {
            System.out.println("Exception driven development");
        }


        var sum = 0;
        int counter = 0;
        for (List<Integer> list : winningBoard.get(0)) {
            sum += list.stream().reduce(Integer::sum).orElse(0);
            if (++counter == 5) break;
        }

        System.out.println("The product you are looking for is: " + sum * winningNumber[0]); //39984
    }

    private static void findSecondSolution(List<List<Integer>> entries, List<Integer> numbers) {
        final var boards = prepareData(entries);

        HashMap<Integer, Integer> winningPair = new LinkedHashMap<>();
        HashMap<Set<List<List<Integer>>>, Integer> winningPair2 = new LinkedHashMap<>();
        AtomicInteger winsCounter = new AtomicInteger(0);


        for (Integer guess : numbers) {
            for (Map<List<List<Integer>>, AtomicInteger> board : boards) {
                board.keySet().forEach(entry -> {
                    if (board.values().stream().anyMatch(v -> v.intValue() > 0)) {
                        return;
                    }
                    for (List<Integer> row : entry) {
                        row.remove(guess);
                        if (row.size() == 0) {
                            board.values().forEach(v -> v.set(winsCounter.incrementAndGet()));
                            winningPair.put(winsCounter.intValue(), guess);
                            winningPair2.put(board.keySet(), guess);
                            break;
                        }
                    }
                });
            }
        }

        final var lastGuess = winningPair.get(winningPair.size());
        final var orderedBoards = winningPair2.keySet().stream().flatMap(Collection::stream).toList();
        final var lastBoard = orderedBoards.get(orderedBoards.size()-1);

        var sum = 0;
        int counter = 0;
        for (List<Integer> list : lastBoard) {
            sum += list.stream().reduce(Integer::sum).orElse(0);
            if (++counter == 5) break;
        }


        System.out.println("The product you are looking for is: " + sum *lastGuess); //8468
    }


    private static List<Map<List<List<Integer>>, AtomicInteger>> prepareData(List<List<Integer>> rows) {

        List<Map<List<List<Integer>>, AtomicInteger>> aggBoards = new ArrayList<>();
        for (int j = 0; j < rows.size(); j += 5) {


            List<List<Integer>> board = new ArrayList<>();
            List<Integer> vertical1 = new ArrayList<>();
            List<Integer> vertical2 = new ArrayList<>();
            List<Integer> vertical3 = new ArrayList<>();
            List<Integer> vertical4 = new ArrayList<>();
            List<Integer> vertical5 = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                board.add(rows.get(j + i));
                vertical1.add(rows.get(j + i).get(0));
                vertical2.add(rows.get(j + i).get(1));
                vertical3.add(rows.get(j + i).get(2));
                vertical4.add(rows.get(j + i).get(3));
                vertical5.add(rows.get(j + i).get(4));
            }
            board.add(vertical1);
            board.add(vertical2);
            board.add(vertical3);
            board.add(vertical4);
            board.add(vertical5);


            aggBoards.add(Collections.singletonMap(board, new AtomicInteger(0)));
        }

        return aggBoards;
    }

}
