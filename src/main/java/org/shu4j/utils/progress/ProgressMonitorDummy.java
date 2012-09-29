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
package org.shu4j.utils.progress;

/**
 * Dummy implementation.<br>
 * This class is usable if no special progress monitor is used, especially in tests. It is recommended to use
 * an instance of this class instead of null.
 * 
 * @author heb
 * 
 */
public final class ProgressMonitorDummy implements IProgressMonitor {
  private static ProgressMonitorDummy instance = null;

  private ProgressMonitorDummy() {
  }

  public static ProgressMonitorDummy getInstance() {
    if (null == instance) {
      instance = new ProgressMonitorDummy();
    }
    return instance;
  }

  public void begin(String title, int steps) {
  }

  public void log(String comment, LogLevel level) {
  }

  public void step(int units) {
  }

  @Override
  public IProgressMonitor getSubProgress(int stepCount) {
    return this;
  }

  @Override
  public void done() {
  }
}
