package org.shu4j.utils.permission;

public interface IPermissionFactory {
  IPermissionDelegate create(Permission perm, String privateData);
}
