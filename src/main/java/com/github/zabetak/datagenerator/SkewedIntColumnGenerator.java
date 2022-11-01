package com.github.zabetak.datagenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Generates a column of Integers with skewed probabilities, which
 * can be passed through arguments.
 */
public class SkewedIntColumnGenerator implements ColumnGenerator<String> {

  private static final int[] POWERS_OF_10 =
    { 1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000,
      1000000000 };

  private final Random rand = new Random(31);

  // By default, this generates 5 Ints with the following weights
  // [0.8, 0.1, 0.05, 0.03, 0.02]. Variable weights is cumulative
  private List<Double> weights = Arrays.asList(0.8, 0.9, 0.95, 0.98, 1.00);

  private List<Integer> items;

  /**
   * Generates a value for a specific column.
   */
  @Override
  public String generate() {
    int result = items.get(findIndex(rand.nextDouble()));
    return String.valueOf(result);
  }

  /**
   * Setups the generator by using the specified arguments.
   * <p>
   * The method is called before any call is made to the {@link #generate()}
   * method.
   *
   * @param arguments the list of arguments to setup the generator.
   */
  @Override
  public void setup(List<String> arguments) {
    assert arguments.size() >= 1: "Supported arguments DIGIT_LEN [p1 p2 p3 ...]";
    int digitLen = Integer.parseInt(arguments.get(0));

    if (arguments.size() > 1) {
      weights = cumulative(normalize(convertToDouble(arguments.subList(1, arguments.size()))));
    }
    items = new ArrayList<>(weights.size());

    for (int i = 0; i < weights.size(); i++) {
      items.add(i, rand.nextInt(POWERS_OF_10[digitLen]));
    }
    System.out.println("Items: " + Arrays.toString(items.toArray()));
    System.out.println("Weights: " + Arrays.toString(weights.toArray()));
  }

  /**
   * Converts a List of Strings to List of Doubles.
   * @param arguments List of weights in String
   * @return Same weights in Double
   */
  private List<Double> convertToDouble(List<String> arguments) {
    return arguments.stream()
      .map(Double::parseDouble)
      .collect(Collectors.toList());
  }

  /**
   * Normalizes a List of Doubles so that they sum up to 1.0
   * @param weights List of weights in Doubles
   * @return Normalized List of Doubles
   */
  private List<Double> normalize(List<Double> weights) {
    double sum = weights.stream().mapToDouble(d -> d).sum();
    return weights.stream().map(d -> d / sum).collect(Collectors.toList());
  }

  /**
   * Sorts a List of normalized weights, and generates a cumulative List of weights.
   * @param weights List of Normalized weights
   * @return List with cumulative values
   */
  private List<Double> cumulative(List<Double> weights) { // [0.05, 0.03, 0.02, 0.9]
    List<Double> result = new ArrayList<>(weights.size());
    weights.sort(Collections.reverseOrder());   // [0.9, 0.05, 0.03, 0.02]
    result.add(weights.get(0));

    for (int i = 1; i < weights.size(); i++) {
      result.add(i, result.get(i - 1) + weights.get(i));
    }

    return result;    // [0.9, 0.95, 0.98, 1.0]
  }

  /**
   * Probes a list of cumulative values linearly, to find the index of random value p.
   *
   * idx | value  |  range
   * =========================
   *  0  |  0.90  |  (0.00, 0.90]
   *  1  |  0.95  |  (0.90, 0.95]
   *  2  |  0.98  |  (0.95, 0.98]
   *  3  |  1.00  |  (0.98, 1.00]
   *
   * If p = 0.92, idx = 1, as p lies in (0.90, 0.95]
   *
   * @param p Random double value between 0 and 1
   * @return index of the range which contains p
   */
  private int findIndex(double p) {
    for (int i = 0; i < weights.size(); i++) {
      if (p <= weights.get(i)) {
        return i;
      }
    }
    return weights.size() - 1;
  }
}
