package org.shu4j.utils.permission;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public abstract class PrivilegeGroup implements Iterable<Privilege> {
  private final List<Privilege> privileges = new ArrayList<Privilege>();
  private String id;

  public PrivilegeGroup(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  void add(Privilege privilege) {
    this.privileges.add(privilege);
  }

  @Override
  public Iterator<Privilege> iterator() {
    return Collections.unmodifiableList(privileges).iterator();
  }

}
