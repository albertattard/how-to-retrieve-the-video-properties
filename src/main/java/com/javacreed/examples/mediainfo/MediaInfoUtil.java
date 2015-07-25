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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;

public class MediaInfoUtil {

  public static String executeMediaInfo(final String mediaPath) throws IOException, InterruptedException {
    final String exePath = MediaInfoUtil.getMediaInfoCliPath();
    final ProcessBuilder builder = new ProcessBuilder(exePath, mediaPath);
    builder.redirectErrorStream(true);
    final Process process = builder.start();

    final StringBuilder buffer = new StringBuilder();
    try (Reader reader = new InputStreamReader(process.getInputStream())) {
      for (int i; (i = reader.read()) != -1;) {
        buffer.append((char) i);
      }
    }

    final int status = process.waitFor();
    if (status == 0) {
      return buffer.toString();
    }

    throw new IOException("Unexpected exit status " + status);
  }

  public static MediaInfo getMediaInfo(final String mediaPath) throws IOException, InterruptedException {
    return MediaInfo.parse(MediaInfoUtil.executeMediaInfo(mediaPath));
  }

  private static String getMediaInfoCliPath() throws IOException {
    if (MediaInfoUtil.MEDIA_INFO_CLI_PATH == null) {
      final Properties properties = new Properties();
      properties.load(MediaInfoUtil.class.getResourceAsStream("/mediainfo.properties"));

      final String exePath = properties.getProperty("mediainfo.cli.path");
      if (exePath == null) {
        throw new IOException("The property 'mediainfo.cli.path' is not set");
      }

      final File file = new File(exePath).getAbsoluteFile();
      if (!file.isFile()) {
        throw new FileNotFoundException("The value of the property 'mediainfo.cli.path' does not point to a file");
      }

      MediaInfoUtil.MEDIA_INFO_CLI_PATH = file.getAbsolutePath();
    }

    return MediaInfoUtil.MEDIA_INFO_CLI_PATH;
  }

  private static String MEDIA_INFO_CLI_PATH;

  private MediaInfoUtil() {}

}
