package Days;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.stream.Collectors.toList;

/**
 * Part 1:
 * Calculate the horizontal position and depth you would have after following the planned course.
 * What do you get if you multiply your final horizontal position by your final depth?
 * <p>
 * Part 2:
 * Using this new interpretation of the commands, calculate the horizontal position and depth you would have after following the planned course.
 */
public class Day2 {

    public static void main(String[] args) throws IOException {

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("src/resources/Day2.txt"))) {


            List<Command> entries = bufferedReader.lines().map(Day2::parseCommand).collect(toList());

//            System.out.println("The value you are looking for is: " + calculatePosition(entries)); // 1660158
            System.out.println("The product you are looking for is: " + calculatePositionPartTwo(entries)); //
        }

    }

    private static long calculatePosition(List<Command> entries) {

        AtomicInteger depth = new AtomicInteger();
        AtomicInteger horizontal = new AtomicInteger();

        entries.forEach(entry -> {

            switch (entry.direction) {
                case UP -> depth.addAndGet(-entry.value);
                case DOWN -> depth.addAndGet(entry.value);
                case FORWARD -> horizontal.addAndGet(entry.value);
            }

        });
        return (long) depth.intValue() * horizontal.intValue();
    }

    private static long calculatePositionPartTwo(List<Command> commands) {

        AtomicInteger depth = new AtomicInteger();
        AtomicInteger horizontal = new AtomicInteger();
        AtomicInteger aim = new AtomicInteger();

        commands.forEach(command -> {

            switch (command.direction) {
                case UP -> aim.addAndGet(-command.value);
                case DOWN -> aim.addAndGet(command.value);
                case FORWARD -> {
                    horizontal.addAndGet(command.value);
                    depth.addAndGet(aim.get() * command.value);
                }
            }

        });
        return (long) depth.intValue() * horizontal.intValue();
    }


    private static Command parseCommand(String entry) {
        String[] s = entry.split(" ");
        return new Command(Direction.valueOf(s[0].toUpperCase(Locale.ROOT)), Integer.parseInt(s[1]));
    }

    private static class Command {
        private final Direction direction;
        private final int value;

        public Command(Direction direction, int value) {
            this.direction = direction;
            this.value = value;
        }
    }

    private enum Direction {
        FORWARD, DOWN, UP
    }

}
