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

  // private static final IDateTimeFormat dateFormat = DateTimeFormat.getFormat(PredefinedFormat.DATE_MEDIUM);
  // private static final IDateTimeFormat timeFormat = DateTimeFormat.getFormat(PredefinedFormat.TIME_SHORT);

}
