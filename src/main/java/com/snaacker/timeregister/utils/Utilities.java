package com.snaacker.timeregister.utils;

import com.snaacker.timeregister.exception.TimeRegisterException;
import com.snaacker.timeregister.model.UserRequestDto;
import com.snaacker.timeregister.persistent.User;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Arrays;
import java.util.Base64;

public class Utilities {

  public static String encrypt(String s)
      throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
          IllegalBlockSizeException, BadPaddingException {
    byte[] encrypted = encryptToByte(s);

    StringBuilder sb = new StringBuilder();
    for (byte b : encrypted) {
      sb.append((char) b);
    }

    return sb.toString();
  }

  public static byte[] encryptToByte(String s)
      throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
          IllegalBlockSizeException, BadPaddingException {
    String key = System.getProperty("SECRET_KEY_BASE", System.getenv("SECRET_KEY_BASE"));
    Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
    Cipher cipher = Cipher.getInstance("AES");
    cipher.init(Cipher.ENCRYPT_MODE, aesKey);
    return cipher.doFinal(s.getBytes());
  }

  public static String decrypt(String s)
      throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
          IllegalBlockSizeException, BadPaddingException {
    String key = System.getProperty("SECRET_KEY_BASE", System.getenv("SECRET_KEY_BASE"));
    Cipher cipher = Cipher.getInstance("AES");
    Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
    byte[] bb = new byte[s.length()];
    for (int i = 0; i < s.length(); i++) {
      bb[i] = (byte) s.charAt(i);
    }
    cipher.init(Cipher.DECRYPT_MODE, aesKey);
    return new String(cipher.doFinal(bb));
  }
}
