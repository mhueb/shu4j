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

import java.util.Collection;
import java.util.Date;
import java.util.List;

public class ValidityUtil {

  public static <T extends IValidityOwner> T lookup(Collection<T> list, Date date) {
    if (list == null || list.isEmpty() || date == null)
      return null;

    for (T i : list) {
      if (contains(i.getValidity(), date))
        return i;
    }

    return null;
  }

  private static boolean contains(IValidity v, Date date) {
    return (v.getTimeFrom().before(date) || v.getTimeFrom().equals(date)) && (v.getTimeTo().after(date));
  }

  public enum CUT {
    NONE,
    ERASE,
    CUT
  };

  public static CUT checkCut(Date validFrom, Date validTo, Date date) {
    if (date == null)
      throw new IllegalArgumentException("date must not be null");

    if (date.before(validTo) || date.equals(validTo)) {
      if (date.before(validFrom) || date.equals(validFrom))
        return CUT.ERASE;
      else
        return CUT.CUT;
    }
    else
      return CUT.NONE;
  }

  public static <T extends IValidityOwner> void calculateConsequences(List<T> recents, List<T> deletes, List<T> changes, IValidity add) {
    for (T i : recents) {
      IValidity vali = i.getValidity();
      switch (checkCut(vali.getTimeFrom(), vali.getTimeTo(), add.getTimeFrom())) {
      case CUT:
        vali.setTimeTo(add.getTimeFrom());
        break;
      case ERASE:
        deletes.add(i);
        break;
      case NONE:
        break;
      default:
        throw new IllegalStateException("Unexpected cut");
      }
    }
  }

}
