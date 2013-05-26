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
package org.shu4j.utils.permission;

public enum Permission {
  ALLOWED(1),
  READONLY(2),
  HIDDEN(3);

  private int value;

  private Permission(int value) {
    this.value = value;
  }

  public boolean isAllowed() {
    return value == 1;
  }

  public boolean isVisible() {
    return value < 3;
  }

  public boolean isMoreRestrictiveThan(Permission permission) {
    return value > permission.value;
  }

  public Permission add(Permission o) {
    if (isMoreRestrictiveThan(o))
      return this;
    else
      return o;
  }

  public static Permission valueOf(Integer val) {
    if (val != null) {
      if (ALLOWED.ordinal() == val)
        return ALLOWED;
      else if (READONLY.ordinal() == val)
        return READONLY;
      else if (HIDDEN.ordinal() == val)
        return HIDDEN;
      throw new IllegalArgumentException("Value does not represent a permission.");
    }
    return null;
  }
}
