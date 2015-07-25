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

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class Section {
  public static Section parse(final String line) throws IllegalArgumentException {
    if (line.contains(":")) {
      throw new IllegalArgumentException("Section name should not have ':'");
    }

    return new Section(line.trim());
  }

  private final String name;

  private final Map<String, NameValue> values = new LinkedHashMap<>();

  private Section(final String name) {
    this.name = Objects.requireNonNull(name);
  }

  public void add(final NameValue nameValue) throws IllegalArgumentException {
    final String name = nameValue.getName();
    if (values.containsKey(name)) {
      throw new IllegalArgumentException("Duplicate name: '" + name + "'");
    }

    values.put(name, nameValue);
  }

  public String get(final String valueName) {
    final NameValue nameValue = values.get(valueName);
    if (nameValue == null) {
      return null;
    }

    return nameValue.getValue();
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    final StringBuilder formatted = new StringBuilder();
    formatted.append(name).append("\n");
    if (values.isEmpty()) {
      formatted.append("  No values!!!\n");
    } else {
      for (final NameValue nameValue : values.values()) {
        formatted.append("  ").append(nameValue).append("\n");
      }
    }
    return formatted.toString();
  }
}