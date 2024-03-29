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

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Generator {
  private static final char DELIMETER = ',';
  private static final Pattern TYPE_NAME_PATTERN = Pattern.compile("[A-Z]+");
  private static final Pattern TYPE_DIGIT_PATTERN = Pattern.compile("[0-9]+");
  private static final int DEFAULT_NUM_ROWS = 100;
  public static final int DEFAULT_STRING_LENGTH =
      Integer.parseInt(System.getProperty("dgen.string.length", "20"));
  public static final int GEN_CLASSNAME_POSITION = 2;

  private enum TypeName {
    TINYINT,
    SMALLINT,
    BIGINT,
    INTEGER,
    INT,
    TIMESTAMP,
    STRING,
    VARCHAR,
    CHAR,
    DECIMAL,
    DOUBLE,
    FLOAT,
    DATE,
    BOOLEAN,
    CUSTOM
  }

  private Generator() {
  }

  /**
   * @param args
   */
  public static void main(String[] args)
      throws ClassNotFoundException, InstantiationException,
      IllegalAccessException {
    final long rows = args.length > 0 && args[0] != null ? Integer.parseInt(
        args[0]) : DEFAULT_NUM_ROWS;
    List<ColumnGenerator> colGenerators = new ArrayList<>();
    try (Scanner in = new Scanner(System.in)) {
      while (in.hasNextLine()) {
        String[] line = in.nextLine().split("\\s+");
        String name = line[0];
        String fullType = line[1].toUpperCase();
        Matcher nameMatcher = TYPE_NAME_PATTERN.matcher(fullType);
        TypeName typeName = null;
        if (nameMatcher.find()) {
          typeName = TypeName.valueOf(nameMatcher.group().toUpperCase());
        }
        Matcher digitMatcher = TYPE_DIGIT_PATTERN.matcher(fullType);
        List<String> digits = new ArrayList<>(2);
        while (digitMatcher.find()) {
          digits.add(digitMatcher.group());
        }
        switch (typeName) {
        case INTEGER:
        case INT:
        case BIGINT:
        case TINYINT:
        case SMALLINT:
          if (name.toLowerCase().contains("id")) {
            colGenerators.add(new IntColumnSequenceGenerator());
          } else {
            colGenerators.add(new IntColumnGenerator());
          }
          break;
        case DOUBLE:
          colGenerators.add(new DoubleColumnGenerator());
          break;
        case FLOAT:
          colGenerators.add(new FloatColumnGenerator());
          break;
        case DECIMAL:
          colGenerators.add(
              new DecimalColumnGenerator(Integer.parseInt(digits.get(0)),
                  Integer.parseInt(digits.get(1))));
          break;
        case STRING:
        case VARCHAR:
        case CHAR:
          if (digits.isEmpty()) {
            colGenerators.add(new StringColumnGenerator(DEFAULT_STRING_LENGTH));
          } else {
            colGenerators.add(
                new StringColumnGenerator(Integer.parseInt(digits.get(0))));
          }
          break;
        case DATE:
          colGenerators.add(new DateColumnGenerator());
          break;
        case TIMESTAMP:
          colGenerators.add(new TimestampColumnGenerator());
          break;
        case BOOLEAN:
          colGenerators.add(new BooleanColumnGenerator());
          break;
        case CUSTOM:
          assert line.length > 2;
          String generatorClassName = line[GEN_CLASSNAME_POSITION];
          Class<?> genClass = Class.forName(generatorClassName);
          ColumnGenerator<?> generator =
              (ColumnGenerator<?>) genClass.newInstance();
          List<String> arguments = new ArrayList<>();
          for (int i = GEN_CLASSNAME_POSITION + 1; i < line.length; i++) {
            arguments.add(line[i]);
          }
          generator.setup(arguments);
          colGenerators.add(generator);
          break;
        default:
          throw new IllegalStateException("Unsupported type " + typeName);
        }
      }
    }
    for (long i = 0; i < rows; i++) {
      StringBuilder sb = new StringBuilder();
      for (ColumnGenerator<?> g : colGenerators) {
        sb.append(g.generate());
        sb.append(DELIMETER);
      }
      sb.deleteCharAt(sb.length() - 1);
      System.out.println(sb.toString());
    }
  }

}
