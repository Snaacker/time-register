package com.snaacker.timeregister.utils;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;

public class Utilities {

  public static String encrypt(String s)
      throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
          IllegalBlockSizeException, BadPaddingException {
//    byte[] encrypted = encryptToByte(s);
//
//    StringBuilder sb = new StringBuilder();
//    for (byte b : encrypted) {
//      sb.append((char) b);
//    }

//    return sb.toString();
    MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
    messageDigest.update(s.getBytes());
    return new String(messageDigest.digest());

  }

  public static byte[] encryptToByte(String s)
      throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
          IllegalBlockSizeException, BadPaddingException {
//    String key = System.getProperty("SECRET_KEY_BASE", System.getenv("SECRET_KEY_BASE"));
//    Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
//    Cipher cipher = Cipher.getInstance("AES");
//    cipher.init(Cipher.ENCRYPT_MODE, aesKey);
//    return cipher.doFinal(s.getBytes());
    MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
    messageDigest.update(s.getBytes());
    return messageDigest.digest();

  }

}
