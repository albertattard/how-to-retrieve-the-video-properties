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

public class MediaInfo {

  public static MediaInfo parse(final String data) throws IllegalArgumentException {

    final MediaInfo mediaInfo = new MediaInfo(data);

    Section section = null;

    for (final String line : data.split("(\\r\\n|\\r|\\n)")) {
      if (line.isEmpty()) {
        section = null;
        continue;
      }

      if (section == null) {
        section = mediaInfo.addSection(Section.parse(line));
        continue;
      }

      section.add(NameValue.parse(line));
    }

    return mediaInfo;
  }

  private final String rawData;
  private final Map<String, Section> sections = new LinkedHashMap<>();

  private MediaInfo(final String rawData) {
    this.rawData = Objects.requireNonNull(rawData);
  }

  private Section addSection(final Section section) throws IllegalArgumentException {
    final String name = section.getName();
    if (sections.containsKey(name)) {
      throw new IllegalArgumentException("Duplicate section name: '" + name + "'");
    }

    sections.put(name, section);
    return section;
  }

  public String get(final String sectionName, final String valueName) {
    final Section section = sections.get(sectionName);
    if (section == null) {
      return null;
    }
    return section.get(valueName);
  }

  public String getRawData() {
    return rawData;
  }

  @Override
  public String toString() {
    final StringBuilder formatted = new StringBuilder();
    if (sections.isEmpty()) {
      formatted.append("No information found!!!");
    } else {
      for (final Section section : sections.values()) {
        formatted.append(section);
      }
    }

    return formatted.toString();
  }
}