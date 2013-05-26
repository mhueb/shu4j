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
package org.shu4j.utils.resource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.shu4j.utils.stream.ReaderInputStream;


public final class ResourceUtil {
  public static String loadFile(Class<?> location, String file) throws IOException {
    return new String(loadFileBin(location, file));
  }

  public static byte[] loadFileBin(Class<?> location, String file) throws IOException {
    InputStream stream = openResourceStream(location, file);

    try {
      int length = stream.available();
      byte[] buffer = new byte[length];
      int len = stream.read(buffer);
      int offset = len;
      while (offset < length) {
        len = stream.read(buffer, offset, length - offset);
        offset += len;
      }
      return buffer;
    }
    finally {
      stream.close();
    }
  }

  public static InputStream openResourceStream(Class<?> location, String file, String charset) throws IOException {
    InputStream stream = openResourceStream(location, file);
    if (charset != null)
      return new ReaderInputStream(new InputStreamReader(stream, charset));
    return stream;
  }

  public static InputStream openResourceStream(Class<?> location, String file) throws FileNotFoundException {
    String resourceName = '/' + location.getPackage().getName().replace('.', '/') + '/' + file;
    InputStream stream = location.getResourceAsStream(resourceName);
    if (stream == null)
      throw new FileNotFoundException("Resource '" + resourceName + "' not available.");
    return stream;
  }
}
