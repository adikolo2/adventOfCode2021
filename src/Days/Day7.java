package Days;

import static java.util.stream.Collectors.toList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * --- Day 7: Handy Haversacks ---
 */
public class Day7 {
  private static List<BagAndParent> families;

  public static void main(String[] args) throws IOException {

    BufferedReader bufferedReader = new BufferedReader(new FileReader("src/resources/Day7.txt"));

    families = bufferedReader.lines().map(Day7::createFamilies).flatMap(Collection::stream)
        .collect(Collectors.toList());// 1455 elements
    Bag myBag = new Bag("shiny", "gold");

    System.out.println("The number of bag colors that can contain my bag is: " + countPossibleParentBags(myBag)); // 192

    bufferedReader.close();
  }

  private static int countPossibleParentBags(Bag myBag) {

    List<Bag> indirectParents = findAllParents(myBag);
    Set<Bag> allPossibleParents = new HashSet<>(indirectParents);
    Set<Bag> allPossibleParentsTemp = new HashSet<>(indirectParents);
    do {
      allPossibleParents.addAll(allPossibleParentsTemp);
      allPossibleParents.forEach(indirectParent -> {
        allPossibleParentsTemp.addAll(findAllParents(indirectParent));
      });
    } while (!allPossibleParents.equals(allPossibleParentsTemp));

    return allPossibleParents.size();
  }

  private static List<Bag> findAllParents(Bag myBag) {

    return families.stream().filter(bagAndParent -> bagAndParent.child.equals(myBag))
        .map(bagAndParent -> bagAndParent.parent).collect(toList());
  }

  private static List<BagAndParent> createFamilies(String line) {

    List<BagAndParent> families = new ArrayList<>();
    String[] splitOnContain = line.split("contain");
    Bag parent = new Bag(splitOnContain[0]);
    List<Bag> children;
    if (!splitOnContain[1].contains("no other bags")) {
      String[] splitOnComma = splitOnContain[1].trim().split(",");
      children = Arrays.stream(splitOnComma).map(Bag::new).collect(toList());
    } else {
      children = new ArrayList<>();
    }
    children.forEach(child -> {
      families.add(new BagAndParent(child, parent));
    });
    return families;
  }

  private static class BagAndParent {
    private Bag child;

    private Bag parent;

    BagAndParent(Bag child, Bag parent) {

      this.child = child;
      this.parent = parent;
    }
  }

  private static class Bag {
    private final String pattern;

    private final String color;

    Bag(String pattern, String color) {

      this.pattern = pattern;
      this.color = color;
    }

    Bag(String content) {

      String[] s = content.trim().split(" ");
      if (s.length == 4) {
        this.pattern = s[1];
        this.color = s[2];
      } else {
        this.pattern = s[0];
        this.color = s[1];
      }
    }

    @Override
    public boolean equals(Object o) {

      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      Bag bag = (Bag) o;
      return this.pattern.equals(bag.pattern) && this.color.equals(bag.color);
    }

    @Override
    public int hashCode() {

      return Objects.hash(this.pattern, this.color);
    }
  }

}
