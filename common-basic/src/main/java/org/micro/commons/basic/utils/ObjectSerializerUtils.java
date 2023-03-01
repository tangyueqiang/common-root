package org.micro.commons.basic.utils;

import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ObjectSerializerUtils {

    private ObjectSerializerUtils() {
    }

    /**
     * 序列化对象
     *
     * @param obj 对象
     * @return byte
     * @throws IOException 异常
     */
    public static byte[] serilizer(Object obj) throws IOException {
        if (obj == null) {
            return null;
        }

        ByteArrayOutputStream output = null;
        try {
            output = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(output);
            oos.writeObject(obj);
            output.flush();
            return output.toByteArray();
        } finally {
            IOUtils.closeQuietly(output);
        }
    }

    /**
     * 反序列化
     *
     * @param data 字节数组
     * @return Object
     * @throws ClassNotFoundException 异常
     * @throws IOException            异常
     */
    public static Object deSerilizer(byte[] data) throws ClassNotFoundException, IOException {
        if (data == null || data.length == 0) {
            return null;
        }

        InputStream bin = null;
        try {
            bin = new ByteArrayInputStream(data);
            return new ObjectInputStream(bin).readObject();
        } finally {
            IOUtils.closeQuietly(bin);
        }

    }

}
