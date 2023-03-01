package org.micro.los.common.protocol;

import java.util.ArrayList;
import java.util.List;

public class BatchProcessLog extends BaseLog {

    private static final long serialVersionUID = 1L;

    /**
     * 源表
     */
    private String sourceTable = "";
    /**
     * 目标表
     */
    private String targetTable = "";

    /**
     * 批处理总记录数
     */
    private long totalCount;
    /**
     * 批处理大小
     */
    private long batchSize;
    /**
     * 已处理记录数
     */
    private long finishedCount;
    /**
     * 批处理新增记录数
     */
    private long insertCount;
    /**
     * 批处理更新记录数
     */
    private long updateCount;
    /**
     * 批处理删除记录数
     */
    private long deleteCount;
    /**
     * 批处理错误记录数
     */
    private long errorCount;

    /**
     * 错误提示
     */
    private List<String> errorMessage;

    /**
     * 源数据
     */
    private List<Object> sourceData;
    /**
     * 目标数据
     */
    private List<Object> targetData;
    /**
     * 错误源数据
     */
    private List<Object> errorSourceData;
    /**
     * 错误目标数据
     */
    private List<Object> errorTargetData;

    /**
     * 版本号
     */
    private String version;

    public BatchProcessLog() {
        super();
        this.errorMessage = new ArrayList<>();
        this.sourceData = new ArrayList<>();
        this.targetData = new ArrayList<>();
        this.errorSourceData = new ArrayList<>();
        this.errorTargetData = new ArrayList<>();
    }

    /**
     * @param buzz        业务名称
     * @param sourceTable 源表
     * @param targetTable 目标表
     * @param batchSize   批量大小
     */
    public BatchProcessLog(String buzz, String sourceTable, String targetTable, long batchSize) {
        this();
        setBuzz(buzz);
        this.sourceTable = sourceTable;
        this.targetTable = targetTable;
        this.batchSize = batchSize;
    }

    public String getSourceTable() {
        return sourceTable;
    }

    public void setSourceTable(String sourceTable) {
        this.sourceTable = sourceTable;
    }

    public String getTargetTable() {
        return targetTable;
    }

    public void setTargetTable(String targetTable) {
        this.targetTable = targetTable;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public long getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(long batchSize) {
        this.batchSize = batchSize;
    }

    public long getFinishedCount() {
        return finishedCount;
    }

    public void setFinishedCount(long finishedCount) {
        this.finishedCount = finishedCount;
    }

    public long getInsertCount() {
        return insertCount;
    }

    public void setInsertCount(long insertCount) {
        this.insertCount = insertCount;
    }

    public long getUpdateCount() {
        return updateCount;
    }

    public void setUpdateCount(long updateCount) {
        this.updateCount = updateCount;
    }

    public long getDeleteCount() {
        return deleteCount;
    }

    public void setDeleteCount(long deleteCount) {
        this.deleteCount = deleteCount;
    }

    public long getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(long errorCount) {
        this.errorCount = errorCount;
    }

    public List<String> getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(List<String> errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<Object> getSourceData() {
        return sourceData;
    }

    public void setSourceData(List<Object> sourceData) {
        this.sourceData = sourceData;
    }

    public List<Object> getTargetData() {
        return targetData;
    }

    public void setTargetData(List<Object> targetData) {
        this.targetData = targetData;
    }

    public List<Object> getErrorSourceData() {
        return errorSourceData;
    }

    public void setErrorSourceData(List<Object> errorSourceData) {
        this.errorSourceData = errorSourceData;
    }

    public List<Object> getErrorTargetData() {
        return errorTargetData;
    }

    public void setErrorTargetData(List<Object> errorTargetData) {
        this.errorTargetData = errorTargetData;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

}
