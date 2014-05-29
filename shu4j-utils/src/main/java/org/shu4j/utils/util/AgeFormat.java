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

import java.util.Date;

import org.shu4j.utils.i18n.DateTimeFormat;
import org.shu4j.utils.i18n.DateTimeFormat.PredefinedFormat;
import org.shu4j.utils.rc.AgeMessages;

public final class AgeFormat {
  private static final DateTimeFormat dateFormat = DateTimeFormat.getFormat(PredefinedFormat.DATE_MEDIUM);
  private static final DateTimeFormat timeFormat = DateTimeFormat.getFormat(PredefinedFormat.TIME_SHORT);

  private final AgeMessages constants;

  public AgeFormat(AgeMessages constants) {
    this.constants = constants;
  }

  public String format(Date date) {
    if (date != null) {
      long days = DateUtil.calcDays(new Date(), date);
      String timeString = timeFormat.format(date);
      if (days == 0)
        return constants.today(timeString);
      else if (days == 1)
        return constants.yesterday(timeString);
      else if (days > 0 && days <= 5)
        return constants.daysAgo(days, timeString);
      else if (days < 0 && days >= -5)
        return constants.inDays(-days, timeString);
      else
        return dateFormat.format(date) + " " + timeString;
    }
    else
      return null;
  }
}