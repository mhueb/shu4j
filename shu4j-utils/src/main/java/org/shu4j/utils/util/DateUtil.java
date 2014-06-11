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

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {
  private DateUtil() {
  }

  public static Date clearTime(Date date) {
    if (date == null)
      return null;

    Calendar cal = new GregorianCalendar();
    cal.setTime(date);
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MILLISECOND, 0);
    return cal.getTime();
  }

  public static Date merge(Date date, Date time) {
    if (date == null)
      return time;

    Calendar cal = new GregorianCalendar();
    cal.setTime(date);
    if (time == null) {
      cal.set(Calendar.HOUR_OF_DAY, 0);
      cal.set(Calendar.MINUTE, 0);
      cal.set(Calendar.SECOND, 0);
      cal.set(Calendar.MILLISECOND, 0);
    }
    else {
      Calendar tim = new GregorianCalendar();
      tim.setTime(time);

      cal.set(Calendar.HOUR_OF_DAY, tim.get(Calendar.HOUR_OF_DAY));
      cal.set(Calendar.MINUTE, tim.get(Calendar.MINUTE));
      cal.set(Calendar.SECOND, tim.get(Calendar.SECOND));
      cal.set(Calendar.MILLISECOND, tim.get(Calendar.MILLISECOND));
    }
    return cal.getTime();
  }

  public static boolean hasTime(Date time) {
    if (time != null) {
      Calendar cal = new GregorianCalendar();
      cal.setTime(time);
      return cal.get(Calendar.HOUR_OF_DAY) != 0 || cal.get(Calendar.MINUTE) != 0 || cal.get(Calendar.SECOND) != 0 || cal.get(Calendar.MILLISECOND) != 0;
    }
    return false;
  }

  /**
   * @param year Year
   * @param month 0..11
   * @param day 1..31
   * @return
   */
  public static Date makeDate(int year, int month, int day) {
    Calendar cal = new GregorianCalendar(year, month, day);
    return cal.getTime();
  }

  /**
   * @param year Year
   * @param month 0..11
   * @param day 1..31
   * @param hour 0..23
   * @param min 0..59
   * @param sec 0..59
   * @return
   */
  public static Date makeDateTime(int year, int month, int day, int hour, int min, int sec) {
    Calendar cal = new GregorianCalendar(year, month, day, hour, min, sec);
    return cal.getTime();
  }

  public static Date makeExpireDate(int seconds) {
    Calendar instance = new GregorianCalendar();
    instance.add(Calendar.SECOND, seconds);
    return instance.getTime();
  }

  public static long calcDays(Date now, Date date) {
    return (DateUtil.clearTime(now).getTime() - DateUtil.clearTime(date).getTime()) / 1000 / 60 / 60 / 24;
  }

  /**
   * Calculate Date of easter sunday.<br>
   * Formular by Carl Friedrich Gauß to calculate easter sunday between 1583 and 8202.
   * 
   * @param year 1583..8202
   * @return Date of easter sunday
   */
  public static Date calcEasterSunday(int year) {
    if (year < 1583 || year > 8202)
      throw new IllegalArgumentException("Invalid year=" + year);

    int k = year / 100;

    int m = 15;
    m += (3 * k + 3) / 4;
    m -= (8 * k + 13) / 25;

    int s = 2;
    s -= (3 * k + 3) / 4;

    int a = year % 19;
    int d = (19 * a + m) % 30;

    int r = d / 28;
    r -= d / 29;
    r *= a / 11;
    r += d / 29;

    int og = 21 + d - r;

    int sz = s + year + year / 4;
    sz %= 7;
    sz *= -1;
    sz += 7;

    int oe = og - sz;
    oe %= 7;
    oe *= -1;
    oe += 7;

    int os = og + oe;

    Calendar cal = new GregorianCalendar();
    cal.set(Calendar.YEAR, year);
    cal.set(Calendar.MONTH, os > 31 ? 4 : 3);
    cal.set(Calendar.DAY_OF_MONTH, os > 31 ? os - 31 : os);
    return cal.getTime();
  }

  @SuppressWarnings("deprecation")
  public static int getHours(Date date) {
    return date == null ? 0 : date.getHours();
  }

  @SuppressWarnings("deprecation")
  public static int getMinutes(Date date) {
    return date == null ? 0 : date.getMinutes();
  }

}
