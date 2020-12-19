package Days;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * --- Day 7: Handy Haversacks -----Part Two---
 */
public class Day7PartTwo {

  private static List<MasterBag> masterBags;

  public static void main(String[] args) throws IOException {

    BufferedReader bufferedReader = new BufferedReader(new FileReader("src/resources/Day7.txt"));

    masterBags = bufferedReader.lines().map(MasterBag::new).collect(Collectors.toList());// 594 elements
    Bag myBag = new Bag("shiny", "gold");

    System.out.println("The number of all bags that my bag must contain is: " + countAllChildrenBags(myBag)); // 12128

    bufferedReader.close();
  }

  private static long countAllChildrenBags(Bag myBag) {

    BagNode root = new BagNode();
    root.nodeOwner = findMasterBagAsParent(myBag);
    root.children = createChildrenTree(root.nodeOwner);
    root.ownerMulitplier = 1;

    return countChildrenSubtotal(root) - 1; // main bag doesn't count
  }

  private static int countChildrenSubtotal(BagNode bagNode) {

    return bagNode.ownerMulitplier + bagNode.children.stream()
        .mapToInt(childNode -> bagNode.ownerMulitplier * countChildrenSubtotal(childNode)).sum();
  }

  private static MasterBag findMasterBagAsParent(Bag myBag) {

    return masterBags.stream().filter(masterBag -> masterBag.parent.equals(myBag)).findFirst().get();
  }

  private static List<BagNode> createChildrenTree(MasterBag myBag) {

    return myBag.bagsAllowed.entrySet().stream().map(entry -> {
      MasterBag masterBagAsParent = findMasterBagAsParent(entry.getKey());
      List<BagNode> allChildrenBags = createChildrenTree(masterBagAsParent);
      return new BagNode(masterBagAsParent, entry.getValue(), allChildrenBags);
    }).collect(Collectors.toList());
  }

  private static class BagNode {
    private MasterBag nodeOwner;

    private int ownerMulitplier;

    private List<BagNode> children;

    public BagNode(MasterBag nodeOwner, int ownerMulitplier, List<BagNode> children) {

      this.nodeOwner = nodeOwner;
      this.ownerMulitplier = ownerMulitplier;
      this.children = children;
    }

    BagNode() {

    }
  }

  private static class MasterBag {
    private final Map<Bag, Integer> bagsAllowed;

    private Bag parent;

    MasterBag(String line) {

      String[] splitOnContain = line.split("contain");
      this.parent = Bag.parentBag(splitOnContain[0]);
      if (!splitOnContain[1].contains("no other bags")) {
        this.bagsAllowed = new HashMap<>();
        String[] splitOnComma = splitOnContain[1].trim().split(",");
        Arrays.stream(splitOnComma).forEach(content -> {
          String[] s = content.trim().split(" ");
          this.bagsAllowed.put(new Bag(s[1], s[2]), Integer.valueOf(s[0]));
        });
      } else {
        this.bagsAllowed = new HashMap<>();
      }

    }
  }

  private static class Bag {
    private final String pattern;

    private final String color;

    Bag(String pattern, String color) {

      this.pattern = pattern;
      this.color = color;
    }

    static Bag parentBag(String content) {

      String[] s = content.trim().split(" ");
      return new Bag(s[0], s[1]);
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
