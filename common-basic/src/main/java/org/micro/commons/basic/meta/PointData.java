package org.micro.commons.basic.meta;

/**
 * 点数据
 */
public class PointData extends BaseMeta {

    private long xvalue;

    private long yvalue;

    public PointData() {
        super();
    }

    public PointData(long xvalue, long yvalue) {
        super();
        this.xvalue = xvalue;
        this.yvalue = yvalue;
    }

    public long getXvalue() {
        return xvalue;
    }

    public void setXvalue(long xvalue) {
        this.xvalue = xvalue;
    }

    public long getYvalue() {
        return yvalue;
    }

    public void setYvalue(long yvalue) {
        this.yvalue = yvalue;
    }

    @Override
    public String toString() {
        return String.format("(%s, %s)", xvalue, yvalue);
    }

}
