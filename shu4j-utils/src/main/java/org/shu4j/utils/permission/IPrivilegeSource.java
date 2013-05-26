package org.shu4j.utils.permission;

public interface IPrivilegeSource {
  IPermissionDelegate getDelegate(String id);
}
