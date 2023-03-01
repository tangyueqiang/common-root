package org.micro.common.logging.autoprop;

import java.util.Map;

public class HttpRestProperties {

    /**
     * POST JSON URI
     */
    private String uri;

    /**
     * POST JSON header
     */
    private Map<String, String> header;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public void setHeader(Map<String, String> header) {
        this.header = header;
    }


}
