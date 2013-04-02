package models;

import java.util.Map;
import java.util.TreeMap;

public class Colors {
  public static Map<String, String> map() {
    Map<String, String> all = new TreeMap<String, String>();
    all.put("#61A6C1", "Aqua");
    all.put("#000000", "Black");
    all.put("#0000FF", "Blue");
    all.put("#54B345", "Green");
    all.put("#F0BA32", "Orange");
    all.put("#E38692", "Pink");
    all.put("#C35B9D", "Violet");
    all.put("#C7243A", "Red");
    all.put("#8E8E8E", "Gray");
    all.put("#FFFFFF", "White");
    return all;
  }
}
