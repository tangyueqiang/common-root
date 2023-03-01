package org.micro.commons.basic.meta;

/**
 * 单值
 */
public class SingleData extends BaseMeta {

    private Object value;
    private long number;

    public SingleData() {

    }

    public SingleData(Object value) {
        this.value = value;

    }

    public SingleData(Object value, long number) {
        this(value);
        this.number = number;

    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }
}
