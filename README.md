The Windows operating system (<a href="http://windows.microsoft.com/en-gb/windows/home" target="_blank">Homepage</a>) provides useful information about video files playable by Windows Media Player (<a href="http://windows.microsoft.com/en-us/windows/windows-media-player" target="_blank">Homepage</a>).  This information includes the video length, frame width and height and other similar properties as shown in the following image.


<a href="http://www.javacreed.com/wp-content/uploads/2015/02/Video-and-Audio-Properties.png" class="preload" rel="prettyphoto" title="Video and Audio Properties" ><img src="http://www.javacreed.com/wp-content/uploads/2015/02/Video-and-Audio-Properties.png" alt="Video and Audio Properties" width="377" height="515" class="size-full wp-image-5324" /></a>


Unfortunately there is no Java API which can easily retrieve such information.  Instead we need to rely on third party native libraries such as MediaInfo (<a href="https://mediaarea.net/en/MediaInfo" target="_blank">Homepage</a>) and create wrappers around it.  In this article we will see how to use the aforementioned third party native library to retrieve the video properties from a Java application.  Please note that the examples shown in this article are based on the Windows 64 bit version of Command Line Interface version 0.7.72.  Different MediaInfo versions may produce different outputs that those shown here.


All code listed below is available at: <a href="https://java-creed-examples.googlecode.com/svn/media-info/How%20to%20Retrieve%20the%20Video%20Properties/" target="_blank">https://java-creed-examples.googlecode.com/svn/media-info/How to Retrieve the Video Properties</a>.  Most of the examples will not contain the whole code and may omit fragments which are not relevant to the example being discussed. The readers can download or view all code from the above link.


This article starts from the configuration process, where it describes from where to download the required libraries and how to use them in Java code.  The second section shows how to call the native library from within Java while the third and final section describes the parsing process.


<h2>Configuration</h2>


As mentioned already, the examples shown in this article make use of a third party native library named MediaInfo, which library can be downloaded from: <a href="http://mediaarea.net/en/MediaInfo/Download" target="_blank">http://mediaarea.net/en/MediaInfo/Download</a>.   As shown in the following image, the MediaInfo comes in several flavours.  For the purpose of this article, we only need the Command Line Interface version.


<a href="http://www.javacreed.com/wp-content/uploads/2015/02/Download-the-CLI-Version.png" class="preload" rel="prettyphoto" title="Download the CLI Version" ><img src="http://www.javacreed.com/wp-content/uploads/2015/02/Download-the-CLI-Version.png" alt="Download the CLI Version" width="1365" height="721" class="size-full wp-image-5325" /></a>


<strong>Please make sure you read the terms and conditions before downloading this library</strong>.


Once downloaded, extract the ZIP file and update the <code>mediainfo.properties</code> file, located under the <code>resources</code> folder (within the article project and not in the just extracted ZIP file).  The following example shows my copy of the properties file, which points to the location where the <code>MediaInfo.exe</code> file was extracted.

<pre>
# =============================================================================
# The 'MediaInfo.exe' can be downloaded from: 
#   http://mediaarea.net/en/MediaInfo/Download/Windows .  
# The command line (CLI) version is required by this example 
# =============================================================================
mediainfo.cli.path=<span class="highlight">C:\\Users\\Albert\\Downloads\\MediaInfo_CLI_0.7.72_Windows_x64\\MediaInfo.exe</span>
# =============================================================================
</pre>


The highlighted part needs to be modified to the path where you extracted your copy of <code>MediaInfo.exe</code>.  In the event that this property is not configure properly, a <code>FileNotFoundException</code> (<a href="http://docs.oracle.com/javase/7/docs/api/java/io/FileNotFoundException.html" target="_blank">Java Doc</a>) will be thrown as shown next.


<pre>
Exception in thread "main" java.io.FileNotFoundException: The value of the property 'mediainfo.cli.path' does not point to a file
	at com.javacreed.examples.mediainfo.MediaInfoUtil.getMediaInfoCliPath(MediaInfoUtil.java:72)
	at com.javacreed.examples.mediainfo.MediaInfoUtil.executeMediaInfo(MediaInfoUtil.java:36)
	at com.javacreed.examples.mediainfo.MediaInfoUtil.getMediaInfo(MediaInfoUtil.java:57)
	at com.javacreed.examples.mediainfo.Example.main(Example.java:29)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:57)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:601)
	at com.intellij.rt.execution.application.AppMain.main(AppMain.java:134)
</pre>


In this case please make sure that the provided path is correct and that the backslashes (<code>\</code>) are escaped.


<h2>Media Info</h2>


The <code>MediaInfo.exe</code> file is an executable file that takes the path of the video as a command line argument and prints the video properties to the same command prompt.  This can be executed as shown next from the command prompt.


<pre>
MediaInfo.exe "C:\Users\Java Creed\Pixar\Boundin.mkv"
</pre>


This example refers to a short movie by Pixar (<a href="http://www.pixar.com/" target="_blank">Homepage</a>) named Boundin&#39;
 (<a href="http://en.wikipedia.org/wiki/Boundin%27" target="_blank">Wiki</a>).  The video path contains a space and thus needs to be enclosed within the double quote (<code>"</code>) so that the command line interface treats this as a single argument.  The <code>MediaInfo.exe</code> prints a formatted string, similar to the one shown below, listing all properties of the given video.


<pre>
General
Unique ID                                : 243366064654295455569390741576099105942 (0xB7169961031FDA2CBBBF81DB9D010C96)
Complete name                            : C:\Users\Java Creed\Pixar\Boundin.mkv
Format                                   : Matroska
Format version                           : Version 2
File size                                : 224 MiB
Duration                                 : 4mn 42s
Overall bit rate                         : 6 633 Kbps
Encoded date                             : UTC 2010-10-11 09:22:18
Writing application                      : mkvmerge v4.0.0 ('The Stars were mine') built on Jun  6 2010 16:18:42
Writing library                          : libebml v1.0.0 + libmatroska v1.0.0

Video
ID                                       : 1
Format                                   : AVC
Format/Info                              : Advanced Video Codec
Format profile                           : High@L4.1
Format settings, CABAC                   : Yes
Format settings, ReFrames                : 4 frames
Codec ID                                 : V_MPEG4/ISO/AVC
Duration                                 : 4mn 42s
Bit rate                                 : 6 003 Kbps
Width                                    : 1 920 pixels
Height                                   : 1 040 pixels
Display aspect ratio                     : 1.85:1
Frame rate mode                          : Constant
Frame rate                               : 23.976 fps
Color space                              : YUV
Chroma subsampling                       : 4:2:0
Bit depth                                : 8 bits
Scan type                                : Progressive
Bits/(Pixel*Frame)                       : 0.125
Stream size                              : 198 MiB (88%)
Writing library                          : x264 core 105 r1732 2b04482
Encoding settings                        : cabac=1 / ref=4 / deblock=1:0:0 / analyse=0x3:0x133 / me=umh / subme=7 / psy=1 / psy_rd=1.00:0.00 / mixed_ref=1 / me_range=16 / chroma_me=1 / trellis=1 / 8x8dct=1 / cqm=0 / deadzone=21,11 / fast_pskip=0 / chroma_qp_offset=-2 / threads=6 / sliced_threads=0 / nr=0 / decimate=1 / interlaced=0 / constrained_intra=0 / bframes=3 / b_pyramid=2 / b_adapt=1 / b_bias=0 / direct=1 / weightb=1 / open_gop=0 / weightp=2 / keyint=250 / keyint_min=23 / scenecut=40 / intra_refresh=0 / rc_lookahead=40 / rc=2pass / mbtree=1 / bitrate=6003 / ratetol=1.0 / qcomp=0.60 / qpmin=10 / qpmax=51 / qpstep=4 / cplxblur=20.0 / qblur=0.5 / ip_ratio=1.40 / aq=1:1.00
Language                                 : English
Default                                  : No
Forced                                   : No

Audio
ID                                       : 2
Format                                   : AC-3
Format/Info                              : Audio Coding 3
Mode extension                           : CM (complete main)
Format settings, Endianness              : Big
Codec ID                                 : A_AC3
Duration                                 : 4mn 42s
Bit rate mode                            : Constant
Bit rate                                 : 640 Kbps
Channel(s)                               : 6 channels
Channel positions                        : Front: L C R, Side: L R, LFE
Sampling rate                            : 48.0 KHz
Bit depth                                : 16 bits
Compression mode                         : Lossy
Stream size                              : 21.6 MiB (10%)
Language                                 : English
Default                                  : Yes
Forced                                   : No
</pre>


The output shown above comprise from three sections, the <em>General</em>, the <em>Video</em> and the <em>Audio</em> sections.  The <em>General</em> section contains general information about the video such as the path and the size, which section is always available.  The <em>Video</em> and the <em>Audio</em> sections contain visual and audio related information respectively and are only shown when such information is available.  For example, a video without sound will not have the <em>Audio</em> section.  Furthermore, a video that includes chapters will have information related to the chapters which information will be found under the <em>Chapters</em> section.  The video used in this example does not contain chapters and thus we do not have this section.


The <code>ProcessBuilder</code> (<a href="http://docs.oracle.com/javase/7/docs/api/java/lang/ProcessBuilder.html" target="_blank">Java Doc</a>) class allows us to invoke system commands similar to the one shown above and capture the created output.  The following example captures this.


<pre>
package com.javacreed.examples.mediainfo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;

public class MediaInfoUtil {

  private static String MEDIA_INFO_CLI_PATH;

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

  private MediaInfoUtil() {}

}
</pre>


The <code>MediaInfoUtil</code> class provides a wrapper to the <code>MediaInfo.exe</code> file and returns a Java object with all information produced by the same executable.  This class is quite long.  Let us break it into smaller parts and describe each part respectively.

<ol>
<li>The class has a <code>private</code> constructor.  This was made <code>private</code> intentionally as there is no need to initialise this call.  All methods are <code>static</code> and the sole field, <code>MEDIA_INFO_CLI_PATH</code> is <code>static</code> too.

<pre>
  private MediaInfoUtil() {}
</pre>

This class acts as a singleton (<a href="http://en.wikipedia.org/wiki/Singleton_pattern" target="_blank">Wiki</a>) or better as a utilities class.
</li>

<li>
The method <code>getMediaInfoCliPath()</code> returns the path to the <code>MediaInfo.exe</code> after verifying that this path points to a file.

<pre>
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
</pre>

If the static field <code>MEDIA_INFO_CLI_PATH</code> is set, then returns its value, otherwise it loads the property file <code>/mediainfo.properties</code> and verifies that this path points to a file.  If the property is missing or does not point to a file, a <code>FileNotFoundException</code> is thrown.  This will produce the error mentioned at the end of the <em>Configuration</em> section.

Please note that the absolute path to the <code>MediaInfo.exe</code> file is returned.  This is important for the <code>ProcessBuilder</code> class to work as required.
</li>

<li>
The method <code>executeMediaInfo()</code> is responsible from creating the wrapper for the <code>MediaInfo.exe</code> and capturing its output as shown next.

<pre>
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
</pre>

Given its importance, we will split this method further and describe each respective part.

<ol>
<li>This method starts by retrieving the absolute path to the <code>MediaInfo.exe</code> file.  The <code>ProcessBuilder</code> will fail if this path is not absolute.
<pre>
    final String exePath = MediaInfoUtil.getMediaInfoCliPath();
    final ProcessBuilder builder = new ProcessBuilder(exePath, mediaPath);
    builder.redirectErrorStream(true);
    final Process process = builder.start();
</pre>

The error stream, where errors are printed, is redirected to the output stream so we only have to deal with one stream.  This is quite important as otherwise we can experience something similar to a deadlock (in theory this is not a deadlock but has the same effect).  If enough errors are produced, the error buffer will get full and will stop the application from writing more errors.  The application will not be able to proceed until the error buffer is emptied, thus waits forever.  Using this approach the errors are written to the output stream and thus the error stream will never get full.  Furthermore, as we will see next we will read all output produced to the standard stream and this will never get full.
</li>

<li>
Once the process is started, we need to start reading the output produced by the process, otherwise the process may block until the produced stream is consumed.  We can do this by using the <code>process.getInputStream()</code>, which returns the standard output stream produced by the same process.

<pre>
    final StringBuilder buffer = new StringBuilder();
    try (Reader reader = new InputStreamReader(process.getInputStream())) {
      for (int i; (i = reader.read()) != -1;) {
        buffer.append((char) i);
      }
    }
</pre>

Please note that error stream is redirected to the standard stream.  Therefore the above code fragment will capture both streams.  Furthermore, the above loop will keep iterating until the process has finished.  The method <code>Reader.read()</code> (<a href="http://docs.oracle.com/javase/7/docs/api/java/io/Reader.html#read()" target="_blank">Java Doc</a>) will block until either there are more characters to be read or the stream is closed, in which case <code>-1</code> is returned.
</li>

<li>
Finally we will wait for the process to finish and retrieve the process exit code.

<pre>
    final int status = process.waitFor();
    if (status == 0) {
      return buffer.toString();
    }
</pre>

In the event the process finished gracefully (that is the exit code is 0), then return the output produced by the same process.  Otherwise throw an <code>IOException</code> to indicate that process finished abnormally.

<pre>
    throw new IOException("Unexpected exit status " + status);
</pre>
</li>
</ol>

</li>
</ol>


As we saw in this section, the <code>MediaInfoUtil</code> provides a wrapper to the <code>MediaInfo.exe</code>.  The next section describes how the output produced by the <code>MediaInfo.exe</code> is parsed into Java Objects.


<h2>Parsing</h2>


The next step once we have the output produced by the <code>MediaInfo.exe</code> is to parse it into Java Objects.  And to help us with this we will create a set of classes that can be constructed from the produced output.


We can divide the output into three parts, the whole information, the section information and the line information.  The line information, such as the one shown next is represented by the class <code>NameValue</code>.


<pre>
Format                                   : AVC
</pre>


The following example shows the <code>NameValue</code>, which is the class representing the line information.


<pre>
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

  <span class="comments">// Other methods removed for brevity</span>
</pre>


The line information comprise name and value and is delimited by a colon (<code>:</code>). The line information is formatted using a fixed width, therefore we can use this information to split the line into a name and a value.  Instead the <code>NameValue</code> class uses the string <code>" : "</code> (space colon space) as a delimiter to be more flexible as shown next.


<pre>
  public static NameValue parse(final String line) throws IllegalArgumentException {
    if (!line.contains(" : ")) {
      throw new IllegalArgumentException("The line is expected to have a ' : '");
    }

    final String[] parts = line.split(" : ", 2);
    return new NameValue(parts[0].trim(), parts[1].trim());
  }
</pre>


The section information is represented by the <code>Section</code> and is shown next.


<pre>
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

  private final Map&lt;String, NameValue&gt; values = new LinkedHashMap&lt;&gt;();

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


  <span class="comments">// Other methods removed for brevity</span>
}
</pre>


The <code>Section</code> class comprise a name and a collection of name value pairs.  Similar to the <code>NameValue</code> class, the <code>Section</code> has its own <code>parse()</code> method.  The section contains a collection of line items, which is represented by the field <code>values</code>.  The parsed <code>NameValue</code>s are added to the <code>Section</code> class through the <code>add()</code> method which verifies name uniqueness. A section cannot have two items with the same name.


All sections are grouped by the <code>MediaInfo</code> class shown next.


<pre>
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
  private final Map&lt;String, Section&gt; sections = new LinkedHashMap&lt;&gt;();

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

  <span class="comments">// Other methods removed for brevity</span>
}
</pre>


The <code>parse()</code> method of the <code>MediaInfo</code> class takes the whole output produced by <code>MediaInfo.exe</code> and creates all necessary objects.  It starts by splitting the output into lines (using the regular expression <code>data.split("(\\r\\n|\\r|\\n)")</code>) and then process each line.  It creates the sections and fill each section with the respective line information.


The following example shows how this can be used.


<pre>
package com.javacreed.examples.mediainfo;

public class Example {

  public static void main(final String[] args) throws Exception {
    final String mediaPath = "C:\\Users\\Java Creed\\Pixar\\Boundin.mkv";

    final MediaInfo mediaInfo = MediaInfoUtil.getMediaInfo(mediaPath);

    final String[] properties = { "Width", "Height", "Format", "Duration" };
    for (final String property : properties) {
      System.out.printf("[%s] = '%s'%n", property, mediaInfo.get("Video", property));
    }
  }
}
</pre>

This example refers to a short movie by Pixar named Boundin and produces the following information.

<pre>
[Width] = '1 920 pixels'
[Height] = '1 040 pixels'
[Format] = 'AVC'
[Duration] = '4mn 42s'
</pre>


<h2>Conclusion</h2>

The MediaInfo native library provides useful information about many kinds of video media file, which information includes video information, audio information, and chapter information to name some.  This library provides more information that the native Windows properties dialog which depends on the Windows Media Player for such information.  Since that the MediaInfo is a native library, we need a wrapper which invokes it and capture its output in Java.  This output is then parsed into Java objects which can be easily used as shown in the above examples.
