/* 
 * Copyright 2012 Matthias Huebner
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

import java.text.ParseException;

import org.apache.commons.lang.StringUtils;
import org.shu4j.utils.rc.DurationConstants;

public class DurationFormat {

  private final Integer hpd;
  private final boolean seconds;
  private final DurationConstants texts;

  public DurationFormat(DurationConstants texts, Integer hpd, boolean seconds) {
    this.texts = texts;
    this.hpd = hpd;
    this.seconds = seconds;
  }

  public DurationFormat(DurationConstants texts, boolean seconds) {
    this(texts, null, seconds);
  }

  public DurationFormat(DurationConstants texts, Integer hpd) {
    this(texts, hpd, false);
  }

  public String format(Long value) {
    if (value == null || value == 0)
      return "";

    long mins = value / 60;
    long secs = value - mins * 60;
    long hours = mins / 60;
    mins = mins - hours * 60;

    long days = 0;
    if (hpd != null && hours > hpd) {
      days = hours / hpd;
      hours = hours - days * hpd;
    }

    StringBuffer buff = new StringBuffer();
    if (days != 0) {
      buff.append(days);
      buff.append(texts.abbreviationDays());
    }

    if (hours != 0) {
      if (buff.length() > 0)
        buff.append("  ");
      buff.append(hours);
      buff.append(texts.abbreviationHours());
    }

    if (mins != 0) {
      if (buff.length() > 0)
        buff.append("  ");
      buff.append(mins);
      buff.append(texts.abbreviationMinutes());
    }

    if (seconds) {
      if (buff.length() > 0)
        buff.append("  ");
      buff.append(secs);
      buff.append(texts.abbreviationSeconds());
    }

    return buff.toString();
  }

  public Long parse(String text) throws ParseException {
    if (StringUtils.isEmpty(text))
      return null;

    try {
      double d = Double.parseDouble(text);
      return (long) (d * 3600.0);
    }
    catch (NumberFormatException e) {
    }

    long duration = 0;

    for (int pos = 0; pos < text.length();) {
      Integer val = null;
      for (; pos < text.length(); ++pos) {
        char ch = text.charAt(pos);
        if (ch == ' ')
          continue;
        if (ch >= '0' && ch <= '9') {
          if (val == null)
            val = 0;
          val = val * 10 + (ch - '0');
        }
        else
          break;
      }

      if (val == null)
        throw new ParseException("Unexpected format: " + text, pos);

      char sym = 0;
      for (; pos < text.length(); ++pos) {
        char ch = text.charAt(pos);
        if (ch == ' ')
          continue;
        sym = ch;
        break;
      }

      final String abbrev = String.valueOf(sym);
      if (abbrev.equals(texts.abbreviationDays())) {
        if (hpd == null)
          duration += val * 3600 * 24;
        else
          duration += val * 3600 * hpd;
      }
      else if (abbrev.equals(texts.abbreviationHours())) {
        duration += val * 3600;
      }
      else if (abbrev.equals(texts.abbreviationMinutes())) {
        duration += val * 60;
      }
      else if (abbrev.equals(texts.abbreviationSeconds())) {
        duration += val;
      }
      else {
        throw new ParseException("Unexpected format: " + text, pos);
      }

      while (++pos < text.length() && text.charAt(pos) == ' ')
        ;
    }

    return duration;
  }
}
