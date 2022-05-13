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
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Random;

/**
 * Generates random timestamps spanning a single day.
 *
 * Each timestamp is of type long and corresponds to milliseconds since epoch.
 */
public final class SingleDayRandomTimestampGenerator
    implements ColumnGenerator<Long> {

  private static final int DAY_MILLISECONDS = 86400000;
  private final Random rand = new Random();
  private final ZonedDateTime midnightToday =
      ZonedDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT, ZoneId.of("UTC"));

  public SingleDayRandomTimestampGenerator() {
  }

  @Override
  public Long generate() {
    int randMilli = rand.nextInt(DAY_MILLISECONDS);
    return midnightToday.plus(randMilli, ChronoUnit.MILLIS).toInstant()
        .toEpochMilli();
  }
}
