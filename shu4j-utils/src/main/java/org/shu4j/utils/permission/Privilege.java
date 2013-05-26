package org.shu4j.utils.permission;

public final class Privilege implements IPermissionDelegate {
  private final String id;
  private final String fullId;
  private IPermissionDelegate delegate;

  public Privilege(PrivilegeGroup group, String id) {
    this.id = id;
    this.fullId = group.getId() + "." + id;
    this.delegate = ConstantPermission.READONLY;
    group.add(this);
  }

  public String getId() {
    return id;
  }

  public String getFullId() {
    return fullId;
  }

  void setDelegate(IPermissionDelegate delegate) {
    this.delegate = delegate == null ? ConstantPermission.READONLY : delegate;
  }

  @Override
  public Permission getPermission(Object... data) {
    return delegate.getPermission(data);
  }
}
