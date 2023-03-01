package org.micro.commons.basic.beans;

public enum EncryptType {
    AES,
    AESHex,
    DES,
    Base64,
    MD5,
    JWTRS256;


    public static EncryptType valueOf(int index) {
        switch (index) {
            case 0:
                return AES;
            case 1:
                return AESHex;
            case 2:
                return DES;
            case 3:
                return Base64;
            case 4:
                return MD5;
            case 5:
                return JWTRS256;
            default:
                return AES;
        }
    }

    public int index() {
        return this.ordinal();
    }
}
