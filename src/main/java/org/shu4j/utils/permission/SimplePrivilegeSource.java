package org.shu4j.utils.permission;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class SimplePrivilegeSource implements IPrivilegeSource {
  private final Map<String, IPermissionDelegate> privilegeMap = new HashMap<String, IPermissionDelegate>();

  public SimplePrivilegeSource(Map<String, Permission> privileges) {
    for (Entry<String, Permission> entry : privileges.entrySet())
      privilegeMap.put(entry.getKey(), ConstantPermission.get(entry.getValue()));
  }

  @Override
  public IPermissionDelegate getDelegate(String id) {
    return privilegeMap.get(id);
  }

}
