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

import java.util.ArrayList;
import java.util.List;

public class PrivilegeManager {
  private final List<PrivilegeGroup> privilegeGroups = new ArrayList<PrivilegeGroup>();

  public PrivilegeManager() {
  }

  public void register(PrivilegeGroup group) {
    privilegeGroups.add(group);
  }

  public void load(IPrivilegeSource source) {
    for (PrivilegeGroup group : privilegeGroups)
      load(group, source);
  }

  private void load(PrivilegeGroup group, IPrivilegeSource source) {
    for (Privilege priv : group)
      priv.setDelegate(source.getDelegate(priv.getFullId()));
  }

  public void reset() {
    for (PrivilegeGroup group : privilegeGroups)
      reset(group);
  }

  private void reset(PrivilegeGroup group) {
    for (Privilege priv : group)
      priv.setDelegate(Permission.READONLY);
  }
}
