package org.micro.commons.basic.beans;

import com.alibaba.fastjson.JSON;

import java.util.Date;

public class BaseForm {

    /**
     * 检索关键字
     **/
    private String keyword;
    /**
     * 检索开始时间
     **/
    private Long solrStartTimestamp;
    /**
     * 检索结束时间
     **/
    private Long solrEndTimestamp;
    /**
     * 当前页码
     **/
    private Integer pageNo = 1;
    /**
     * 分页大小
     **/
    private Integer pageSize = 20;

    public String getKeyword() {
        return keyword == null ? null : keyword.trim();
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Long getSolrStartTimestamp() {
        return solrStartTimestamp;
    }

    public void setSolrStartTimestamp(Long solrStartTimestamp) {
        this.solrStartTimestamp = solrStartTimestamp;
    }

    public Date getSolrStartTime() {
        return solrStartTimestamp == null ? null : new Date(solrStartTimestamp.longValue());
    }

    public Long getSolrEndTimestamp() {
        return solrEndTimestamp;
    }

    public void setSolrEndTimestamp(Long solrEndTimestamp) {
        this.solrEndTimestamp = solrEndTimestamp;
    }

    public Date getSolrEndTime() {
        return solrEndTimestamp == null ? null : new Date(solrEndTimestamp.longValue());
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
