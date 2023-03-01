package org.micro.commons.basic.meta;

public class ColumnMeta extends BaseMeta {

    public static final String YES = "YES";
    public static final String NO = "NO";
    public static final String EMPTY = "";
    /**
     * name's alias
     **/
    private String alias;
    /**
     * Is Primary Key
     **/
    private boolean pk;
    /**
     * if isPk=true , sequence number within primary key( a value of 1 represents the first column of the primary key,
     * a value of 2 would represent the second column within the primary key).
     **/
    private int keySeq;
    /**
     * if isPk=true , primary key name (may be null)
     **/
    private String pkName;
    /**
     * Is Index Key
     **/
    private boolean uk;
    private String ukName;
    /**
     * SQL type from java.sql.Types
     **/
    private int dataType;
    /**
     * Data source dependent type name, for a UDT the type name is fully qualified
     **/
    private String typeName;
    /**
     * column size.
     **/
    private int columnSize;
    /**
     * the number of fractional digits.
     * Null is returned for data types where DECIMAL_DIGITS is not applicable.
     **/
    private int decimalDigits;
    /**
     * Radix (typically either 10 or 2)
     **/
    private int numPrecRadix;
    /**
     * is NULL allowed. <br/>
     * columnNoNulls - might not allow NULL values <br/>
     * columnNullable - definitely allows NULL values <br/>
     * columnNullableUnknown - nullability unknown
     **/
    private int nullable;
    /**
     * comment describing column (may be null)
     **/
    private String remarks;
    /**
     * default value for the column, which should be interpreted as a string when the value is enclosed in single quotes (may be null)
     **/
    private String columnDef;
    /**
     * for char types the maximum number of bytes in the column
     **/
    private int charOctetLength;
    /**
     * index of column in table (starting at 1)
     **/
    private int ordinalPosition;

    /***
     * ISO rules are used to determine the nullability for a column.<br/>
     * ◦ YES --- if the column can include NULLs  <br/>
     * ◦ NO --- if the column cannot include NULLs <br/>
     * ◦ empty string --- if the nullability for the column is unknown
     * */
    private String isNullable;
    /***
     * Indicates whether this column is auto incremented.<br/>
     * ◦ YES --- if the column is auto incremented <br/>
     * ◦ NO --- if the column is not auto incremented <br/>
     * ◦ empty string --- if it cannot be determined whether the column is auto incremented
     *
     */
    private String isAutoincrement;
    /***
     *  Indicates whether this is a generated column.<br/>
     * ◦ YES --- if this a generated column <br/>
     * ◦ NO --- if this not a generated column <br/>
     * ◦ empty string --- if it cannot be determined whether this is a generated column
     *
     */
    private String isGeneratedcolumn;
    /***
     * 是否为分区列
     */
    private boolean isPartitionColumn;

    public ColumnMeta() {
    }

    public ColumnMeta(String name) {
        super(name);
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public int getKeySeq() {
        return keySeq;
    }

    public void setKeySeq(int keySeq) {
        this.keySeq = keySeq;
    }

    public String getPkName() {
        return pkName;
    }

    public void setPkName(String pkName) {
        this.pkName = pkName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getColumnSize() {
        return columnSize;
    }

    public void setColumnSize(int columnSize) {
        this.columnSize = columnSize;
    }

    public int getDecimalDigits() {
        return decimalDigits;
    }

    public void setDecimalDigits(int decimalDigits) {
        this.decimalDigits = decimalDigits;
    }

    public int getNumPrecRadix() {
        return numPrecRadix;
    }

    public void setNumPrecRadix(int numPrecRadix) {
        this.numPrecRadix = numPrecRadix;
    }

    public int getNullable() {
        return nullable;
    }

    public void setNullable(int nullable) {
        this.nullable = nullable;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getColumnDef() {
        return columnDef;
    }

    public void setColumnDef(String columnDef) {
        this.columnDef = columnDef;
    }

    public int getCharOctetLength() {
        return charOctetLength;
    }

    public void setCharOctetLength(int charOctetLength) {
        this.charOctetLength = charOctetLength;
    }

    public int getOrdinalPosition() {
        return ordinalPosition;
    }

    public void setOrdinalPosition(int ordinalPosition) {
        this.ordinalPosition = ordinalPosition;
    }

    public String getIsNullable() {
        return isNullable;
    }

    public void setIsNullable(String isNullable) {
        this.isNullable = isNullable;
    }

    public String getIsAutoincrement() {
        return isAutoincrement;
    }

    public void setIsAutoincrement(String isAutoincrement) {
        this.isAutoincrement = isAutoincrement;
    }

    public String getIsGeneratedcolumn() {
        return isGeneratedcolumn;
    }

    public void setIsGeneratedcolumn(String isGeneratedcolumn) {
        this.isGeneratedcolumn = isGeneratedcolumn;
    }

    public boolean isPartitionColumn() {
        return isPartitionColumn;
    }

    public void setPartitionColumn(boolean isPartitionColumn) {
        this.isPartitionColumn = isPartitionColumn;
    }

    public boolean isPk() {
        return pk;
    }

    public void setPk(boolean pk) {
        this.pk = pk;
    }

    public boolean isUk() {
        return uk;
    }

    public void setUk(boolean uk) {
        this.uk = uk;
    }

    public String getUkName() {
        return ukName;
    }

    public void setUkName(String ukName) {
        this.ukName = ukName;
    }

}
