/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to you under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.zabetak.datagenerator;

import java.util.List;
import java.util.Random;

/**
 * Generates random integers with fixed number of digits optionally including
 * prefix/suffix if provided.
 *
 * For digit length equal to 4, prefix AB, and suffix _XX, the generated values
 * look like below.
 * <pre>
 * AB0028_XX
 * AB8033_XX
 * AB0002_XX
 * </pre>
 * If the generated integer number has fewer digits than those requested it is
 * padded with zeros.
 */
public final class FixedDigitColumnGenerator
    implements ColumnGenerator<String> {
  private static final int[] POWERS_OF_10 =
      { 1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000,
          1000000000 };

  private final Random rand = new Random(31);
  private int digitLen = 4;
  private String prefix = "";
  private String suffix = "";

  @Override
  public void setup(List<String> arguments) {
    switch (arguments.size()) {
    case 3:
      suffix = arguments.get(2);
    case 2:
      prefix = arguments.get(1);
    case 1:
      digitLen = Integer.parseInt(arguments.get(0));
      if (digitLen <= 0 || digitLen > POWERS_OF_10.length) {
        String msg = "DIGIT_LEN %s must be between 1 and %s";
        throw new IllegalArgumentException(
            String.format(msg, digitLen, POWERS_OF_10.length));
      }
    case 0:
      // Use default values
      break;
    default:
      String s = "Supported arguments DIGIT_LEN, PREFIX, SUFFIX. Found: %";
      throw new IllegalArgumentException(String.format(s, arguments));
    }
  }

  @Override
  public String generate() {
    int num = rand.nextInt(POWERS_OF_10[digitLen]);
    return String.format("%s%0" + digitLen + "d%s", prefix, num, suffix);
  }
}
