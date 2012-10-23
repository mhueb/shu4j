/* 
 * Copyright 2012 Matthias Huebner
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
package org.shu4j.utils.permission;

public interface IPermissionDelegate {
  public static final IPermissionDelegate ALLOWED = new IPermissionDelegate() {

    @Override
    public void removeListener(IPermissionListener listener) {
    }

    @Override
    public Permission getPermission(Object... data) {
      return Permission.ALLOWED;
    }

    @Override
    public void addListener(IPermissionListener listener) {
    }
  };

  Permission getPermission(Object... data);

  void addListener(IPermissionListener listener);

  void removeListener(IPermissionListener listener);
}
