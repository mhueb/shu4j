/* 
 * Copyright 2012 SHU4J
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class MessageList implements IMessageReceiver, IMessageSource, Serializable {
  private static final long serialVersionUID = 1L;

  private List<Message> messages = new ArrayList<Message>();

  public MessageList() {
  }

  public MessageList(IMessageSource source) {
    add(source);
  }

  public List<Message> getMessages() {
    return Collections.unmodifiableList(messages);
  }

  public MessageLevel getWorstLevel() {
    MessageLevel worst = null;
    for (Message msg : messages) {
      if (worst == null || worst.getNr() < msg.getLevel().getNr())
        worst = msg.getLevel();
    }
    return worst;
  }

  public void add(Message msg) {
    messages.add(msg);
  }

  public void clear() {
    messages.clear();
  }

  public boolean hasMessages() {
    return !messages.isEmpty();
  }

  public void add(IMessageSource source) {
    if (source != null)
      messages.addAll(source.getMessages());
  }

  @Override
  public String toString() {
    return "MessageBag [messages=" + messages + "]";
  }

  @Override
  public Iterator<Message> iterator() {
    return messages.iterator();
  }
}
