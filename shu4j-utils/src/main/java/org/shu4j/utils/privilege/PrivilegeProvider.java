package org.shu4j.utils.privilege;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.shu4j.utils.permission.IPermissionDelegate;
import org.shu4j.utils.permission.Permission;

public final class PrivilegeProvider implements IPrivilegeProvider, Serializable {
  private static final long serialVersionUID = 1L;

  private final Map<String, IPermissionDelegate> delegateMap = new HashMap<String, IPermissionDelegate>();
  
  public IPermissionDelegate get(Privilege priv) {
    IPermissionDelegate delegate = delegateMap.get(priv.getId());
    if (delegate == null)
      delegate = Permission.READONLY;
    return delegate;
  }

  public void setPrivilege(String id, IPermissionDelegate delegate) {
    Validate.notEmpty(id);
    Validate.notNull(delegate);
    delegateMap.put(id, delegate);
  }
}
