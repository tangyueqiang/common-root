package org.micro.commons.basic.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.StringEscapeUtils;
import org.micro.commons.basic.exception.BasicException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Objects;

public class AesUtil {

    /**
     * 默认加密种子
     **/
    public static final String AES_SEED = "#!(EPAAS)#!";
    private static final Logger LOGGER = LoggerFactory.getLogger(AesUtil.class);
    private static final String AES = "AES";

    /**
     * 加密
     *
     * @param data 需要加密的内容
     * @param key  加密密码
     * @return byte
     */
    public static byte[] encrypt(byte[] data, byte[] key) {
        if (data == null) {
            throw new NullPointerException("data is null");
        }
        if (key == null) {
            throw new NullPointerException("key is null");
        }
        try {
            SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance(ConfigureEncryptAndDecrypt.AES_ALGORITHM);// 创建密码器
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);// 初始化
            return cipher.doFinal(data); // 加密
        } catch (Exception e) {
            throw new RuntimeException("encrypt fail!", e);
        }
    }

    public static String encryptBase64(String data, String key) {
        try {
            byte[] valueByte = encrypt(data.getBytes(ConfigureEncryptAndDecrypt.CHAR_ENCODING),
                    key.getBytes(ConfigureEncryptAndDecrypt.CHAR_ENCODING));
            return Base64.encodeBase64String(valueByte);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("encrypt fail!", e);
        }

    }

    /**
     * 加密
     */
    public static String encryptStr(String content, String seed) {
        byte[] bytes = encrypt(content, seed);
        return new String(Objects.requireNonNull(Base64.encodeBase64(bytes)));
    }

    public static String encryptHexStr(String content, String seed) {
        byte[] bytes = encrypt(content, seed);
        return new String(Hex.encodeHex(Objects.requireNonNull(bytes)));
    }

    public static String decryptBase64(String data, String key) {
        if (data == null) {
            throw new NullPointerException("data is null");
        }
        if (key == null) {
            throw new NullPointerException("key is null");
        }
        byte[] originalData = Base64.decodeBase64(data.getBytes());
        byte[] valueByte = decrypt(originalData, key.getBytes(StandardCharsets.UTF_8));
        return StringEscapeUtils.unescapeJava(new String(valueByte));
    }

    /**
     * 解密
     *
     * @param data 待解密内容
     * @param key  解密密钥
     * @return byte
     */
    public static byte[] decrypt(byte[] data, byte[] key) {
        if (data == null) {
            throw new NullPointerException("data is null");
        }
        if (key == null) {
            throw new NullPointerException("key is null");
        }
        try {
            SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance(ConfigureEncryptAndDecrypt.AES_ALGORITHM);// 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, secretKey);// 初始化
            return cipher.doFinal(data); // 加密
        } catch (Exception e) {
            throw new RuntimeException("decrypt fail!", e);
        }
    }


    /**
     * 解密
     *
     * @throws org.micro.commons.basic.exception.BasicException 异常
     */
    public static String decryptStr(String content, String seed) throws BasicException {
        byte[] bytes = Base64.decodeBase64(content.getBytes());
        return new String(AesUtil.decrypt(bytes, seed));
    }

    public static String decryptHexStr(String content, String seed) throws BasicException {
        try {
            byte[] bytes = Hex.decodeHex(content.toCharArray());
            return new String(AesUtil.decrypt(bytes, seed));
        } catch (Exception e) {
            if (e instanceof BasicException) {
                throw (BasicException) e;
            }
            throw new BasicException(e);
        }
    }

    /**
     * 加密
     */
    private static byte[] encrypt(String content, String seed) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance(AES);
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(seed.getBytes());
            kgen.init(128, secureRandom);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, AES);
            Cipher cipher = Cipher.getInstance(AES);
            byte[] byteContent = content.getBytes(StandardCharsets.UTF_8);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return cipher.doFinal(byteContent);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("NoSuchAlgorithmException", e);
        } catch (NoSuchPaddingException e) {
            LOGGER.error("NoSuchPaddingException", e);
        } catch (InvalidKeyException e) {
            LOGGER.error("InvalidKeyException", e);
        } catch (IllegalBlockSizeException e) {
            LOGGER.error("IllegalBlockSizeException", e);
        } catch (BadPaddingException e) {
            LOGGER.error("BadPaddingException", e);
        }
        return null;
    }

    /**
     * 解密
     *
     * @throws BasicException 异常
     */
    private static byte[] decrypt(byte[] content, String seed) throws BasicException {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance(AES);
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(seed.getBytes());
            kgen.init(128, secureRandom);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, AES);
            Cipher cipher = Cipher.getInstance(AES);
            cipher.init(Cipher.DECRYPT_MODE, key);
            return cipher.doFinal(content);
        } catch (Exception e) {
            throw new BasicException(e);
        }
    }

    /**
     * 将二进制转换成16进制
     *
     * @param buf buf
     * @return string
     */
    public static String parseByte2HexStr(byte[] buf) {
        StringBuilder sb = new StringBuilder();
        for (byte b : buf) {
            String hex = Integer.toHexString(b & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制转换为二进制
     *
     * @param hexStr hexStr
     * @return byte
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    /**
     * 加密
     */
    public static byte[] encrypt2(String content, String seed) {
        try {
            SecretKeySpec key = new SecretKeySpec(seed.getBytes(), AES);
            Cipher cipher = Cipher.getInstance(ConfigureEncryptAndDecrypt.AES_ALGORITHM);
            byte[] byteContent = content.getBytes(StandardCharsets.UTF_8);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return cipher.doFinal(byteContent);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("NoSuchAlgorithmException", e);
        } catch (NoSuchPaddingException e) {
            LOGGER.error("NoSuchPaddingException", e);
        } catch (InvalidKeyException e) {
            LOGGER.error("InvalidKeyException", e);
        } catch (IllegalBlockSizeException e) {
            LOGGER.error("IllegalBlockSizeException", e);
        } catch (BadPaddingException e) {
            LOGGER.error("BadPaddingException", e);
        }
        return null;
    }

    /**
     * 类ConfigureEncryptAndDecrypt.java的实现描述：加密，解密相关配置常量
     */
    public static class ConfigureEncryptAndDecrypt {

        public static final String CHAR_ENCODING = "UTF-8";
        public static final String CHAR_ENCODING_GBK = "GBK";
        public static final String KEY_ALGORITHM = "RSA";
        public static final String SIGNATURE_ALGORITHM = "MD5withRSA";
        public static final String SHA_RSA = "SHA1WithRSA";
        public static final String AES_ALGORITHM = "AES/ECB/PKCS5Padding";
        /**
         * 3des加解密算法工具类
         **/
        public static final String DES_ALGORITHM = "DESede";
        public static final String MD5_ALGORITHM = "MD5";
        public static final String SHA_ALGORITHM = "SHA";
        public static final String SEPERATOR = "$";
    }

}
