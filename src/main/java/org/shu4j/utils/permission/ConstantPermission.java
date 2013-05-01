package org.shu4j.utils.permission;

public class ConstantPermission implements IPermissionDelegate {
  public static final IPermissionDelegate ALLOWED = new ConstantPermission(Permission.ALLOWED);
  public static final IPermissionDelegate HIDDEN = new ConstantPermission(Permission.HIDDEN);
  public static final IPermissionDelegate READONLY = new ConstantPermission(Permission.READONLY);

  private Permission perm;

  private ConstantPermission(Permission perm) {
    this.perm = perm;
  }

  @Override
  public Permission getPermission(Object... data) {
    return perm;
  }

  public static IPermissionDelegate get(Permission perm) {
    if (perm == null)
      return null;
    switch (perm) {
    case ALLOWED:
      return ALLOWED;
    case HIDDEN:
      return HIDDEN;
    case READONLY:
      return READONLY;
    }
    throw new IllegalArgumentException("Unexpected permission " + perm);
  }

}
