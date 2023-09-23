package com.snaacker.timeregister.utils;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Utilities {

    public static boolean isSameDay(Date date1, Date date2) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);
        return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)
                && calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH)
                && calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH);
    }

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
