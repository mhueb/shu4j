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
package org.shu4j.utils.message;

public enum MessageLevel {
  INFO(0),
  WARN(1),
  DECIDE(2),
  ERROR(3),
  FATAL(4);

  private int level;

  private MessageLevel(int level) {
    this.level = level;
  }

  public int getNr() {
    return level;
  }

  public static boolean isError(MessageLevel lvl) {
    return lvl != null && lvl.getNr() >= ERROR.getNr();
  }

  public static MessageLevel map(Integer value) {
    if (value != null) {
      if (value == INFO.level)
        return INFO;
      else if (value == WARN.level)
        return WARN;
      else if (value == DECIDE.level)
        return DECIDE;
      else if (value == ERROR.level)
        return ERROR;
      else if (value == FATAL.level)
        return FATAL;
    }
    return null;
  }
}
