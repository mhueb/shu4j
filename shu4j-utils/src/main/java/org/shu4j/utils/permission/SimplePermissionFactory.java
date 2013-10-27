package org.shu4j.utils.permission;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;

public class SimplePermissionFactory implements IPermissionFactory {

  private static final IPermissionFactory INSTANCE = new SimplePermissionFactory();

  public static IPermissionFactory getInstance() {
    return INSTANCE;
  }

  @Override
  public IPermissionDelegate create(Permission perm, String privateData) {
    Validate.isTrue(StringUtils.isBlank(privateData), "Unexpected data");
    return perm;
  }

}
