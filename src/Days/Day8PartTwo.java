package Days;

import static java.util.stream.Collectors.toList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * --- Day 8: Handheld Halting ------ Part Two ---
 */
public class Day8PartTwo {

  public static void main(String[] args) throws IOException {

    BufferedReader bufferedReader = new BufferedReader(new FileReader("src/resources/Day8.txt"));

    List<Instruction> entries = bufferedReader.lines().map(Instruction::new).collect(toList());

    System.out.println("The value of accumulator before the loop: " + bruteForceTheGame(entries)); // 1553
    bufferedReader.close();
  }

  private static int bruteForceTheGame(List<Instruction> entries) {

    bruteForceLoop: for (int i = 0; i < entries.size(); i++) {
      Operation opCache = null;
      switch (entries.get(i).operation) {
        case NOP:
          opCache = Operation.NOP;
          entries.get(i).operation = Operation.JMP;
          break;
        case JMP:
          opCache = Operation.JMP;
          entries.get(i).operation = Operation.NOP;
          break;
        case ACC:
          continue bruteForceLoop;
        default:
          break;
      }
      int accumulator = execute(entries);
      if (accumulator > 0) {
        return accumulator;
      } else {
        entries.get(i).operation = opCache;
      }
    }
    return 0;
  }

  private static int execute(List<Instruction> instructions) {

    int accumulator = 0;
    int index = 0;
    Set<Integer> indexCache = new HashSet<>();
    boolean notALoop = true;
    while (notALoop = indexCache.add(index)) {
      if (index >= instructions.size()) {
        break;
      }
      Instruction instruction = instructions.get(index);
      switch (instruction.operation) {
        case ACC:
          accumulator += instruction.value;
          index += 1;
          break;
        case JMP:
          index += instruction.value;
          break;
        case NOP:
          index += 1;
          break;
        default:
          break;
      }
    }
    if (notALoop) {
      return accumulator;
    }
    return -1;
  }

  private static class Instruction {

    private Operation operation;

    private Sign sign;

    private int value;

    Instruction(String line) {

      String[] s = line.trim().split(" ");
      this.operation = Operation.getEnum(s[0]);
      this.sign = Sign.getEnum(s[1].substring(0, 1));
      this.value = Integer.parseInt(s[1]);
    }

  }

  private enum Operation {
    NOP("nop"), ACC("acc"), JMP("jmp");

    private String operation;

    Operation(String operation) {

      this.operation = operation;
    }

    @Override
    public String toString() {

      return this.operation;
    }

    static Operation getEnum(String enumValue) {

      return Arrays.stream(Operation.values()).filter(op -> op.toString().equals(enumValue)).findFirst().get();
    }

  }

  private enum Sign {
    UP("+"), DOWN("-");

    private String sign;

    Sign(String sign) {

      this.sign = sign;
    }

    @Override
    public String toString() {

      return this.sign;
    }

    static Sign getEnum(String enumValue) {

      return Arrays.stream(Sign.values()).filter(op -> op.toString().equals(enumValue)).findFirst().get();
    }
  }
}
