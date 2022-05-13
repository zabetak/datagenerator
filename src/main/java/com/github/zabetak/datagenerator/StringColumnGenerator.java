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

import java.util.Random;

public class StringColumnGenerator implements ColumnGenerator<String> {
  private static final Random RAND = new Random();
  private final int len;

  public StringColumnGenerator(int len) {
    this.len = len;
  }

  public String generate() {
    StringBuilder sb = new StringBuilder();
    char c = (char) ('a' + RAND.nextInt(20));
    for (int i = 0; i < len; i++) {
      sb.append(c);
    }
    return sb.toString();
  }
}