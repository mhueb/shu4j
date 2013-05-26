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
 * Dummy implementation for testing purpose.<br>
 * 
 */
public final class ProgressMonitorDummy implements IProgressMonitor {
  private static ProgressMonitorDummy instance = new ProgressMonitorDummy();

  private ProgressMonitorDummy() {
  }

  public static ProgressMonitorDummy get() {
    return instance;
  }

  @Override
  public void begin(String title, int steps) {
  }

  @Override
  public void log(String comment, LogLevel level) {
  }

  @Override
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
