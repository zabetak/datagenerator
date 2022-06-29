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
 * Generates data for columns of type timestamp.
 */
public final class TimestampColumnGenerator implements ColumnGenerator<String> {

  private static final DateTimeFormatter FORMATTER =
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.nnnnnnnnn");

  private final Random rand = new Random();

  @Override
  public String generate() {
    int randYear = rand.nextInt(9999) + 1;
    int randMonth = rand.nextInt(12) + 1;
    LocalDate yearMonth = LocalDate.of(randYear, randMonth, 1);
    int randdays = rand.nextInt(yearMonth.lengthOfMonth()) + 1;
    LocalDate fullDate = yearMonth.plus(randdays, ChronoUnit.DAYS);
    int randHour = rand.nextInt(24);
    int randMinutes = rand.nextInt(60);
    int randSec = rand.nextInt(60);
    int randNano = rand.nextInt(1_000_000_000);
    LocalTime time = LocalTime.of(randHour, randMinutes, randSec, randNano);
    return LocalDateTime.of(fullDate, time).format(FORMATTER);
  }

}
