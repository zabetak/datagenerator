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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Random;

/**
 * Generates random timestamps spanning a single day.
 *
 * Each timestamp corresponds to the string representation of a
 * {@link LocalDateTime} object with seconds precision.
 */
public final class SingleDayRandomTimestampGenerator
    implements ColumnGenerator<String> {
  private static final DateTimeFormatter FORMATTER =
      DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
  private static final int DAY_SECONDS = 86400;
  private final Random rand = new Random();
  private final LocalDateTime midnightToday =
      LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);

  public SingleDayRandomTimestampGenerator() {
  }

  @Override
  public String generate() {
    int randSeconds = rand.nextInt(DAY_SECONDS);
    return midnightToday.plus(randSeconds, ChronoUnit.SECONDS)
        .format(FORMATTER);
  }
}
