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
package org.shu4j.utils.resource;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.apache.commons.lang.StringUtils;
import org.shu4j.utils.privilege.Privilege;
import org.shu4j.utils.privilege.PrivilegeGroup;
import org.shu4j.utils.privilege.PrivilegeGroupInfo;
import org.shu4j.utils.privilege.PrivilegeInfo;

public class PrivilegeExtractor {

  private ClassDescriptor descriptor;
  private PrivilegeGroupInfo root;

  public PrivilegeGroupInfo extract(Class<?> cls) throws IllegalArgumentException, IllegalAccessException {
    this.descriptor = new ClassDescriptor(cls);
    this.root = new PrivilegeGroupInfo();

    for (Field field : cls.getDeclaredFields()) {
      if (Modifier.isStatic(field.getModifiers())) {
        Object data = field.get(cls);
        if (data instanceof Privilege)
          register((Privilege) data);
        else if (data instanceof PrivilegeGroup)
          register((PrivilegeGroup) data);
      }
    }

    return root;
  }

  protected String getDescription(String id) {
    return this.descriptor.getDescription(id);
  }

  protected String getName(String id) {
    String name = this.descriptor.getName(id);
    if (name != null)
      return name;
    else
      return splitPathId(id)[1];
  }

  private void register(PrivilegeGroup privilegeGroup) {
    String[] ids = splitPathId(privilegeGroup.getId());
    PrivilegeGroupInfo parent = StringUtils.isNotBlank(ids[0]) ? root.getGroup(ids[0]) : root;
    new PrivilegeGroupInfo(ids[1], getName(privilegeGroup.getId()), getDescription(privilegeGroup.getId()), parent);
  }

  private void register(Privilege privilege) {
    PrivilegeInfo prinilegeInfo = new PrivilegeInfo(privilege.getId(), getName(privilege.getId()), getDescription(privilege.getId()), privilege.getDelegateType());
    String groupid = splitPathId(privilege.getId())[0];
    PrivilegeGroupInfo groupInfo = root.getGroup(groupid);
    groupInfo.addPrivilege(prinilegeInfo);
  }

  private String[] splitPathId(String id) {
    String path = "";
    int pos = id.lastIndexOf('.');
    if (pos != -1) {
      path = id.substring(0, pos - 1);
      id = id.substring(pos + 1);
    }
    return new String[] {
        path,
        id };
  }
}
