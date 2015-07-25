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

public class Example {

  public static void main(final String[] args) throws Exception {
    final String mediaPath = "video-path.mp4";

    final MediaInfo mediaInfo = MediaInfoUtil.getMediaInfo(mediaPath);

    final String[] properties = { "Width", "Height", "Format", "Duration" };
    for (final String property : properties) {
      System.out.printf("[%s] = '%s'%n", property, mediaInfo.get("Video", property));
    }
  }
}
