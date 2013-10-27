/* 
 * GWTAF - GWT Application Framework
 * 
 * Copyright (C) 2008-2010 Matthias Huebner.
 * 
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 3 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 *
 * $Id: $
 */
package org.shu4j.utils.resource;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.apache.commons.lang.StringUtils;
import org.shu4j.utils.privilege.Privilege;
import org.shu4j.utils.privilege.PrivilegeGroup;
import org.shu4j.utils.privilege.info.PrivilegeGroupInfo;
import org.shu4j.utils.privilege.info.PrivilegeInfo;

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

  private void register(PrivilegeGroup data) {
    String[] ids = splitPathId(data.getId());
    PrivilegeGroupInfo parent = StringUtils.isNotBlank(ids[0]) ? root.getGroup(ids[0]) : root;
    new PrivilegeGroupInfo(ids[1], getName(data.getId()), getDescription(data.getId()),parent);
  }

  private void register(Privilege data) {
    PrivilegeInfo info = new PrivilegeInfo(data.getId(), getName(data.getId()), getDescription(data.getId()));
    String groupid = splitPathId(data.getId())[0];
    PrivilegeGroupInfo group = root.getGroup(groupid);
    group.addPrivilege(info);
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
