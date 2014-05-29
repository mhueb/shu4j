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
package org.shu4j.utils.resource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;
import org.shu4j.utils.util.LoggerUtil;

public class ClassDescriptor {
  private final Logger log = LoggerUtil.getLogger(ClassDescriptor.class);

  private final Properties props;
  private String name;

  public ClassDescriptor(Class<?> cls) {
    props = loadProperties(cls);
    name = cls.getSimpleName();
    name = getName(null);
    if (name == null) {
      name = cls.getSimpleName();
    }
  }

  private Properties loadProperties(Class<?> cls) {
    try {
      Properties props = loadProperties(cls, cls.getSimpleName() + "_" + Locale.getDefault().getLanguage());
      if (props == null)
        props = loadProperties(cls, cls.getSimpleName());
      return props;
    }
    catch (IOException e) {
      log.log(Level.SEVERE, "Failed to load properties of class bundle " + cls.getName(), e);
    }
    return null;
  }

  private Properties loadProperties(Class<?> cls, String name) throws IOException {
    String file = name + ".properties";

    try {
      InputStream input = ResourceUtil.openResourceStream(cls, file, "utf-8");
      if (log.isLoggable(Level.FINER))
        log.finer("Loading properties of class bundle " + cls.getName() + " from " + file);

      Properties props = new Properties();
      props.load(input);
      return props;
    }
    catch (FileNotFoundException e) {
      return null;
    }
  }

  public String getDescription(String member) {
    if (props != null) {
      String descr;
      if (member != null)
        descr = props.getProperty(member + ".description");
      else
        descr = props.getProperty("description");
      if (StringUtils.isEmpty(descr) && log.isLoggable(Level.FINER))
        log.finer(this.name + " description not set for member " + member);
      return descr;
    }
    else
      return null;
  }

  public String getName(String member) {
    if (props != null) {
      String name;
      if (member != null)
        name = props.getProperty(member + ".name");
      else
        name = props.getProperty("name");
      if (StringUtils.isEmpty(name)) {
        if (log.isLoggable(Level.WARNING) && member != null)
          log.warning(this.name + ": " + member + ".name not set!");
      }
      else
        return name;
    }
    return null;
  }

}
