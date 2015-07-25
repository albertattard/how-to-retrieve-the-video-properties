/*
 * #%L
 * How to Retrieve the Video Properties
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2012 - 2015 Java Creed
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package com.javacreed.examples.mediainfo;

import java.util.Objects;

public class NameValue {

  public static NameValue parse(final String line) throws IllegalArgumentException {
    if (!line.contains(" : ")) {
      throw new IllegalArgumentException("The line is expected to have a ' : '");
    }

    final String[] parts = line.split(" : ", 2);
    return new NameValue(parts[0].trim(), parts[1].trim());
  }

  private final String name;
  private final String value;

  private NameValue(final String name, final String value) {
    this.name = Objects.requireNonNull(name);
    this.value = Objects.requireNonNull(value);
  }

  public String getName() {
    return name;
  }

  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return String.format("[%s] = '%s'", name, value);
  }

}