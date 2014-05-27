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
package org.shu4j.utils.privilege;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.shu4j.utils.util.Hasher;

public final class PrivilegeGroupInfo extends AbstractPrivilegeInfo {
  private static final long serialVersionUID = 1L;

  private Map<String, AbstractPrivilegeInfo> children = new HashMap<String, AbstractPrivilegeInfo>();

  public PrivilegeGroupInfo() {
  }

  public PrivilegeGroupInfo(String id, String name, String description, PrivilegeGroupInfo parent) {
    super(id, name, description);
    if (parent != null)
      parent.addGroup(this);
  }

  @Override
  public boolean equals(Object obj) {
    return obj instanceof PrivilegeGroupInfo && ((PrivilegeGroupInfo) obj).getId().equals(getId());
  }

  @Override
  public int hashCode() {
    return Hasher.add(Hasher.HASHSEED, getId());
  }

  @Override
  public String toString() {
    return "PrivilegeGroup " + getId();
  }

  public Collection<AbstractPrivilegeInfo> getChildren() {
    return Collections.unmodifiableCollection(children.values());
  }

  public Set<PrivilegeGroupInfo> getChildGroups() {
    Set<PrivilegeGroupInfo> groups = new HashSet<PrivilegeGroupInfo>();
    for (AbstractPrivilegeInfo node : children.values())
      if (node instanceof PrivilegeGroupInfo)
        groups.add((PrivilegeGroupInfo) node);
    return groups;
  }

  public Set<PrivilegeInfo> getPrivileges() {
    Set<PrivilegeInfo> infos = new HashSet<PrivilegeInfo>();
    for (AbstractPrivilegeInfo node : children.values())
      if (node instanceof PrivilegeInfo)
        infos.add((PrivilegeInfo) node);
    return infos;
  }

  public AbstractPrivilegeInfo findNode(String path) {
    AbstractPrivilegeInfo seek = this;
    String[] ids = path.split("\\.");
    for (String id : ids) {
      if (seek instanceof PrivilegeGroupInfo)
        seek = ((PrivilegeGroupInfo) seek).children.get(id);
      else
        return null;
    }
    return seek;
  }

  public PrivilegeGroupInfo getGroup(String path) {
    AbstractPrivilegeInfo node = findNode(path);
    if (node instanceof PrivilegeGroupInfo)
      return (PrivilegeGroupInfo) node;
    if (node != null)
      throw new IllegalArgumentException("Invalid group path " + path);
    else
      throw new IllegalArgumentException("Non existing path " + path);
  }

  private void addGroup(PrivilegeGroupInfo child) {
    if (children.containsKey(child.getId()))
      throw new IllegalArgumentException("Duplicate child group with id=" + child.getId());

    children.put(child.getId(), child);
  }

  public void addPrivilege(PrivilegeInfo info) {
    if (children.containsKey(info.getId()))
      throw new IllegalArgumentException("Duplicate privilege " + info);

    children.put(info.getId(), info);
  }

  public void joinPrivileges(AbstractPrivilegeInfo node) {
    if (node instanceof PrivilegeGroupInfo)
      join((PrivilegeGroupInfo) node);
    else if (node instanceof PrivilegeInfo)
      join((PrivilegeInfo) node);
    else
      throw new IllegalArgumentException("Unknown type of node " + node);
  }

  private void join(PrivilegeGroupInfo group) {
    children.put(group.getId(), group);
  }

  private void join(PrivilegeInfo info) {
    children.put(info.getId(), info);
  }
}
