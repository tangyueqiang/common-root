package org.micro.commons.basic.utils;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

    /**
     * 对一个文件获取md5值
     *
     * @throws NoSuchAlgorithmException 异常
     */
    public static String getMD5(File file) throws IOException, NoSuchAlgorithmException {
        MessageDigest messageDigest;
        messageDigest = MessageDigest.getInstance("MD5");
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            byte[] buffer = new byte[8192];
            int length;
            while ((length = fileInputStream.read(buffer)) != -1) {
                messageDigest.update(buffer, 0, length);
            }
            return new String(Hex.encodeHex(messageDigest.digest()));
        }
    }


    /**
     * 求一个字符串的md5值
     */
    public static String encode(String src) {
        return DigestUtils.md5Hex(src);
    }

}
