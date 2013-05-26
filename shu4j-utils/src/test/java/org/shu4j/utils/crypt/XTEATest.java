package org.shu4j.utils.crypt;

import junit.framework.Assert;

import org.junit.Test;

public class XTEATest {

  @Test
  public void test() {
    XTEA tea = new XTEA("MySpecialKey");
    byte[] encryptString = tea.encryptString("Hello");
    String decrypt = tea.decryptString(encryptString);
    Assert.assertEquals("Hello", decrypt);
  }

}
