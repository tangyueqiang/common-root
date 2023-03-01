package org.micro.commons.basic.utils;

import org.apache.commons.beanutils.BeanUtilsBean;

public final class BeanUtils extends org.apache.commons.beanutils.BeanUtils {

    /**
     * 对象熟悉拷贝
     *
     * @param dest dest
     * @param orig orig
     */
    public static void copyProperties(final Object dest, final Object orig) {
        try {
            BeanUtilsBean.getInstance().copyProperties(dest, orig);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
