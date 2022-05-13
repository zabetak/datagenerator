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

import java.math.BigDecimal;

public final class DecimalColumnGenerator
    implements ColumnGenerator<BigDecimal> {

  private final int precision;
  private final int scale;
  private final String num;

  public DecimalColumnGenerator(int precision, int scale) {
    this.precision = precision;
    this.scale = scale;
    String n = "";
    for (int i = 0; i < precision - scale; i++) {
      n += "2";
    }
    n += ".";
    for (int i = 0; i < scale; i++) {
      n += "3";
    }
    num = n;
  }

  public BigDecimal generate() {
    return new BigDecimal(num);
  }
}
