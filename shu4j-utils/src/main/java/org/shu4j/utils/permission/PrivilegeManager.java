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
      priv.setDelegate(ConstantPermission.READONLY);
  }
}
