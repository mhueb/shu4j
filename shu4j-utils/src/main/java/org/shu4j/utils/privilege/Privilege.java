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
package org.shu4j.utils.privilege;

public final class Privilege {
  private final String id;
  private final String delegateType;

  public Privilege(PrivilegeGroup group, String id) {
    this(group, id, null);
  }

  public Privilege(PrivilegeGroup group, String id, String delegateType) {
    this(group.getId() + "." + id, delegateType);
  }

  public Privilege(String id) {
    this(id, null);
  }

  public Privilege(String id, String delegateType) {
    this.id = id;
    this.delegateType = delegateType;
  }

  public String getId() {
    return id;
  }

  public String getDelegateType() {
    return delegateType;
  }
}
