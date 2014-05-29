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
