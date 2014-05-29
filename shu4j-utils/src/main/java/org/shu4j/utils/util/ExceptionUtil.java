/* 
 * Copyright 2012 SHU4J
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.shu4j.utils.util;

public class ExceptionUtil {

  private static final String SMALL = "<div style='font-size:10px;'>";

  public static String dumpStackTraceHTML(Throwable e) {
    return dumpStackTraceHTML(e, -1);
  }

  public static String dumpStackTraceHTML(Throwable e, int maxLines) {
    if (e == null)
      return "";

    StringBuilder buffer = new StringBuilder();
    buffer.append(SMALL);
    dumpStackTrace(buffer, e, maxLines, "<br/>");
    if (buffer.length() == SMALL.length())
      return "";
    buffer.append("</div>");
    return buffer.toString();
  }

  public static String dumpStackTrace(Throwable e) {
    return dumpStackTrace(e, -1);
  }

  public static String dumpStackTrace(Throwable e, final int maxLines) {
    if (e == null)
      return null;

    StringBuilder buffer = new StringBuilder();
    dumpStackTrace(buffer, e, maxLines, "\n");
    return buffer.toString();
  }

  private static void dumpStackTrace(StringBuilder buffer, Throwable e, final int maxLines, final String nl) {
    while (e != null) {
      StackTraceElement[] trace = e.getStackTrace();
      int max = maxLines;
      if (maxLines == -1)
        max = trace.length;
      else if (maxLines > trace.length)
        max = trace.length;

      for (int i = 0; i < max; ++i) {
        String stackline = trace[i].toString();
        if (stackline.startsWith("sun.reflect.")) {
          if (max < trace.length)
            max++;
          continue;
        }

        buffer.append(stackline);
        buffer.append(nl);
      }

      if (max < trace.length)
        buffer.append("...").append(nl);

      if (e != e.getCause()) {
        e = e.getCause();
        if (e != null)
          buffer.append(nl).append("Caused by: ").append(e.toString()).append(nl);
      }
    }
  }
}
