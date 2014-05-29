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
package org.shu4j.utils.i18n;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Delegate to make it work in GWT too
 * 
 * @author Matt
 * 
 */
public class DateTimeFormat {

  private DateFormat format;

  public enum PredefinedFormat {
    DATE_MEDIUM,
    TIME_SHORT
  }

  // TODO

  public static DateTimeFormat getFormat(PredefinedFormat format) {
    switch (format) {
    case DATE_MEDIUM:
      return new DateTimeFormat(SimpleDateFormat.getDateTimeInstance());
    case TIME_SHORT:
      return new DateTimeFormat(SimpleDateFormat.getDateTimeInstance());
    }
    throw new IllegalArgumentException("Unexpected argument " + format);
  }

  public DateTimeFormat(DateFormat format) {
    this.format = format;
  }

  public String format(Date date) {
    return format.format(date);
  }

}
