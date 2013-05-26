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
package org.shu4j.utils.image;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.imageio.stream.MemoryCacheImageOutputStream;

import org.shu4j.utils.exception.UnexpectedErrorException;

public class ImageUtil {

  public static final class ImageUtilException extends Exception {
    private static final long serialVersionUID = 1L;

    public ImageUtilException(Throwable e) {
      super(e);
    }
  }

  public static byte[] getImageData(BufferedImage image, String formatName) {
    try {
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      ImageIO.write(image, formatName, new MemoryCacheImageOutputStream(out));
      return out.toByteArray();
    }
    catch (IOException e) {
      throw new UnexpectedErrorException(e);
    }
  }

}
