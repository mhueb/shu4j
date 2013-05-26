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
package org.shu4j.utils.message;

import java.io.Serializable;

public final class Message implements Serializable {
  private static final long serialVersionUID = 1L;

  private String text;

  private MessageLevel level;

  private String contextId;

  public Message() {
  }

  public Message(String text, MessageLevel level) {
    this(text, level, null);
  }

  public Message(String msg, MessageLevel level, String contextId) {
    this.text = msg;
    this.level = level;
    this.contextId = contextId;
  }

  public String getText() {
    return text;
  }

  public MessageLevel getLevel() {
    return level;
  }

  public String getContextId() {
    return contextId;
  }

  @Override
  public String toString() {
    return "Message [level=" + level + ", msg=" + text + "]";
  }
}
