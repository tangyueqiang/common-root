package org.micro.commons.basic.utils;

import com.alibaba.fastjson.JSON;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.micro.commons.basic.beans.Result.ResultCode;
import org.micro.commons.basic.utils.HttpUtil.FormField.FormFieldType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * http请求工具类
 *
 * @author py
 * 2018年7月6日
 */
public class HttpUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtil.class);

    private static final int connectionTimeout = 20000;
    private static final int socketTimeout = 20000;
    private static final CloseableHttpClient defaultHttpClient = initHttpClient(connectionTimeout / 1000, socketTimeout / 1000);

    /**
     * 初始化http client
     *
     * @param connectionTimeoutSeconds 连接超时时间
     * @param socketTimeoutSeconds     网络超时时间
     * @return CloseableHttpClient
     */
    public static CloseableHttpClient initHttpClient(int connectionTimeoutSeconds, int socketTimeoutSeconds) {
        CloseableHttpClient client = null;
        try {
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(connectionTimeoutSeconds * 1000)
                    .setSocketTimeout(socketTimeoutSeconds * 1000)
                    .build();

            client = HttpClientBuilder.create()
                    .setDefaultRequestConfig(requestConfig)
                    .setSSLSocketFactory(createSSLFactory())
                    .build();

        } catch (Exception ex) {
            LOGGER.error("HttpClient inital error", ex);
        }

        return client;
    }

    /**
     * 创建SSL连接工厂, 支持https, 忽略证书
     *
     * @return SSLConnectionSocketFactory
     * @throws KeyManagementException   KeyManagementException
     * @throws NoSuchAlgorithmException NoSuchAlgorithmException
     * @throws KeyStoreException        KeyStoreException
     */
    public static SSLConnectionSocketFactory createSSLFactory()
            throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
        // 创建SSL连接, 支持https
        SSLContext sslContext = createSSLContext();
        return new SSLConnectionSocketFactory(sslContext, (requestedHost, remoteServerSession) -> requestedHost.equalsIgnoreCase(remoteServerSession.getPeerHost()));
    }

    /**
     * 创建SSL连接连接, 支持https, 忽略证书
     *
     * @return SSLConnectionSocketFactory
     * @throws KeyManagementException   KeyManagementException
     * @throws NoSuchAlgorithmException NoSuchAlgorithmException
     * @throws KeyStoreException        KeyStoreException
     */
    public static SSLContext createSSLContext()
            throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
        // 创建SSL连接, 支持https
        return SSLContextBuilder
                .create()
                .loadTrustMaterial(null, (TrustStrategy) (chain, authType) -> true)
                .build();
    }

    public static HttpResult get(String url) {
        return send(url, "get", null, null);
    }

    public static HttpResult get(String url, String... keyVals) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < keyVals.length; i++) {
            sb.append("&").append(keyVals[i]).append("=").append(keyVals[i + 1]);
            i++;
        }
        if (!url.contains("?")) {
            url = url.concat("?" + sb.substring(1));
        } else {
            url = url.concat(sb.toString());
        }
        return send(url, "get", null, null);
    }

    public static HttpResult post(String url, Map<String, Object> params, Map<String, String> headerMap) {
        List<NameValuePair> list = new ArrayList<>();
        if (params != null) {
            for (String key : params.keySet()) {
                if (params.get(key) != null) {
                    list.add(new BasicNameValuePair(key, String.valueOf(params.get(key))));
                }
            }
        }
        return send(url, "post", list, headerMap);
    }

    public static HttpResult post(String url, Map<String, Object> params) {
        return post(url, params, null);
    }

    /**
     * POST Json数据
     *
     * @param url    url
     * @param body   body
     * @param header header
     * @return HttpResult
     */
    public static HttpResult postJson(String url, String body, Map<String, String> header) {

        HttpPost httpReq = new HttpPost(url);

        httpReq.addHeader("content-type", "application/json;charset=utf-8");
        if (header != null) {
            for (Map.Entry<String, String> entry : header.entrySet()) {
                httpReq.addHeader(entry.getKey(), entry.getValue());
            }
        }

        HttpResult httpResult = new HttpResult();
        CloseableHttpResponse httpResponse = null;
        InputStream inputStream = null;
        InputStream inputStreamEntity = null;
        try {
            // 不为空的时候设置参数
            JSON.parseObject(body);

            inputStreamEntity = IOUtils.toInputStream(body, StandardCharsets.UTF_8);
            InputStreamEntity entity = new InputStreamEntity(inputStreamEntity);
            httpReq.setEntity(entity);

            httpResponse = defaultHttpClient.execute(httpReq);
            if (httpResponse != null) {
                int statusCode = httpResponse.getStatusLine().getStatusCode();
                httpResult.setCode(statusCode);
                if (httpResponse.getEntity() != null) {
                    inputStream = httpResponse.getEntity().getContent();
                    httpResult.setBody(IOUtils.toString(inputStream, StandardCharsets.UTF_8));
                }
            }
        } catch (Exception e) {
            if (httpResult.getCode() == 0) {
                httpResult.setCode(httpResponse != null ? httpResponse.getStatusLine().getStatusCode() : ResultCode.FAIL);
            }
            httpResult.setBody(e.getMessage());
            LOGGER.error(e.getMessage());
        } finally {
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(inputStreamEntity);
            IOUtils.closeQuietly(httpResponse);
        }

        return httpResult;
    }

    /**
     * Web service soap+x 请求
     *
     * @param url    请求路径
     * @param xml    xml 内容
     * @param header 请求头
     * @return HttpResult
     */
    public static HttpResult postSoap(String url, String xml, Map<String, String> header) {
        if (header == null) {
            header = new HashMap<>();
        }

        xml = StringUtils.isBlank(xml) ? StringUtils.EMPTY : xml;

        String contentTypeKey = "content-type";
        String contentTypeVal = "text/xml;charset=utf-8";
        for (String key : header.keySet()) {
            if (key.equalsIgnoreCase(contentTypeKey)) {
                contentTypeKey = key;
                contentTypeVal = header.get(key);
                break;
            }
        }
        header.put(contentTypeKey, contentTypeVal);
        header.put("soap", "true");

        Map<String, Object> params = new HashMap<>();
        params.put("xml", xml);
        return post(url, params, header);
    }


    public static HttpResult send(CloseableHttpClient client, String url, String method,
                                  List<NameValuePair> params, Map<String, String> headerMap) {

        if (client == null) {
            client = defaultHttpClient;
        }

        HttpResult httpResult = new HttpResult();
        CloseableHttpResponse httpResponse = null;
        InputStream inputStream = null;
        try {
            if ("get".equalsIgnoreCase(method)) {

                url = concatUrl(url, params);

                HttpGet httpGet = new HttpGet(url);
                if (headerMap != null) {
                    for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                        httpGet.addHeader(entry.getKey(), entry.getValue());
                    }
                }
                httpResponse = client.execute(httpGet);
            } else if ("post".equalsIgnoreCase(method)) {
                HttpPost httpPost = new HttpPost(url);
                if (headerMap != null) {
                    for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                        httpPost.addHeader(entry.getKey(), entry.getValue());
                    }
                }
                // soap+xml
                if (headerMap != null && headerMap.containsKey("soap")) {
                    StringEntity entity = new StringEntity(params.get(0).getValue());
                    httpPost.setEntity(entity);
                }
                // 不为空的时候设置参数
                else if (params != null) {
                    StringEntity entity = new UrlEncodedFormEntity(params, StandardCharsets.UTF_8);
                    httpPost.setEntity(entity);
                }
                httpResponse = client.execute(httpPost);
            } else if ("delete".equalsIgnoreCase(method)) {

                url = concatUrl(url, params);

                HttpDelete httpDelete = new HttpDelete(url);
                httpResponse = client.execute(httpDelete);
            } else if ("options".equalsIgnoreCase(method)) {

                url = concatUrl(url, params);
                HttpOptions httpOptions = new HttpOptions(url);
                httpResponse = client.execute(httpOptions);
            }

            if (httpResponse != null) {
                int statusCode = httpResponse.getStatusLine().getStatusCode();
                httpResult.setCode(statusCode);
                HttpEntity entity = httpResponse.getEntity();
                if (entity != null) {
                    inputStream = entity.getContent();
                    httpResult.setBody(IOUtils.toString(inputStream, StandardCharsets.UTF_8));
                }
            }
        } catch (Exception e) {
            if (httpResult.getCode() == 0) {
                httpResult.setCode(httpResponse != null ? httpResponse.getStatusLine().getStatusCode() : ResultCode.FAIL);
            }
            httpResult.setBody(e.getMessage());
            LOGGER.error("", e);
        } finally {
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(httpResponse);
        }

        httpResult.setUrl(url);
        return httpResult;
    }

    /**
     * 支持get或post或delete或options请求
     *
     * @param url       路径
     * @param method    请求方法
     * @param params    参数
     * @param headerMap 请求头
     * @return HttpResult
     */
    public static HttpResult send(String url, String method, List<NameValuePair> params, Map<String, String> headerMap) {
        return send(null, url, method, params, headerMap);
    }

    /**
     * url参数拼接
     *
     * @param url    url
     * @param params 参数列表
     * @return 拼接完的url
     * @throws UnsupportedEncodingException 异常
     */
    public static String concatUrl(String url, List<NameValuePair> params) throws UnsupportedEncodingException {

        if (params != null && !params.isEmpty()) {
            StringBuilder buff = new StringBuilder(url);
            if (StringUtils.contains(url, "?")) {
                // 已有参数
                buff.append("&");
            } else {
                // 无参数
                buff.append("?");
            }

            for (NameValuePair p : params) {
                String name = p.getName();
                String value = URLEncoder.encode(p.getValue(), "utf-8");
                buff.append(name).append("=").append(value);
                buff.append("&");
            }

            url = buff.toString();
            if (url.endsWith("?") || url.endsWith("&")) {
                url = buff.substring(0, buff.length() - 1);
            }
            buff.setLength(0);
        }

        return url;
    }

    public static String concatUrl(String base, String suburl) {
        if (StringUtils.isBlank(base)) {
            return suburl;
        }
        if (StringUtils.isBlank(suburl)) {
            return base;
        }
        base = base.endsWith("/") ? base.substring(0, base.length() - 1) : base;
        suburl = suburl.startsWith("/") ? suburl : "/" + suburl;
        base = base + suburl;
        return base;
    }

    /**
     * 需要调用方关闭InputStream
     *
     * @param url 路径
     * @return InputStream
     */
    public static InputStream download(String url) {
        InputStream inputStream = null;
        HttpGet httpget = new HttpGet(url);
        try {
            HttpResponse response = defaultHttpClient.execute(httpget);
            HttpEntity entity = response.getEntity();
            inputStream = entity.getContent();
        } catch (IOException e) {
            LOGGER.error("IOException", e);
        }
        return inputStream;
    }

    /**
     * 可以指定文件偏移量的文件上传
     *
     * @param url         路径
     * @param formFields  表单字段
     * @param connTimeout 小于0取20秒
     * @param readTimeout 小于0取20秒
     * @return HttpResult
     * @throws IOException 异常
     */
    public static HttpResult send(String url, List<FormField> formFields,
                                  int connTimeout, int readTimeout) throws IOException {

        connTimeout = connTimeout < 0 ? connectionTimeout : connTimeout;
        readTimeout = readTimeout < 0 ? socketTimeout : connTimeout;

        HttpResult result = new HttpResult();
        HttpURLConnection conn = null;
        String BOUNDARY = "---------------------" + RandomStringUtils.randomAlphanumeric(11);
        List<FormField> md5s = new ArrayList<>();
        try {
            URL uurl = new URL(url);
            // 创建SSL连接, 支持https, 忽略证书
            if ("https".equalsIgnoreCase(uurl.getProtocol())) {
                HttpsURLConnection.setDefaultSSLSocketFactory(createSSLContext().getSocketFactory());
                HttpsURLConnection.setDefaultHostnameVerifier((requestedHost, remoteServerSession) -> requestedHost.equalsIgnoreCase(remoteServerSession.getPeerHost()));
            }
            conn = (HttpURLConnection) uurl.openConnection();
            conn.setConnectTimeout(connTimeout);
            conn.setReadTimeout(readTimeout);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
            OutputStream out = conn.getOutputStream();
            // POST参数
            if (formFields != null) {
                for (FormField field : formFields) {
                    StringBuilder strBuf = new StringBuilder();
                    String name = field.getName();
                    String value = field.getValue();
                    strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");

                    switch (field.getType()) {
                        case Text:
                            strBuf.append("Content-Disposition: form-data; name=\"").append(name).append("\"\r\n\r\n");
                            strBuf.append(value);
                            out.write(strBuf.toString().getBytes(StandardCharsets.UTF_8));
                            break;
                        case File:
                        case InputStream:
                            String md5 = "";
                            MessageDigest messageDigest = null;
                            try {
                                messageDigest = MessageDigest.getInstance("MD5");
                            } catch (NoSuchAlgorithmException e) {
                                LOGGER.error("NoSuchAlgorithmException", e);
                            }

                            File file = field.getFile();
                            strBuf.append("Content-Disposition: form-data; name=\"").append(name).append("\"; filename=\"").append(file == null ? field.getOrignFilename() : file.getName()).append("\"\r\n");
                            if (field.getMimeType() == null) {
                                strBuf.append("Content-Type:application/octet-stream\r\n\r\n");
                            } else {
                                strBuf.append("Content-Type:").append(field.getMimeType()).append("\r\n\r\n");
                            }
                            out.write(strBuf.toString().getBytes(StandardCharsets.UTF_8));
                            InputStream inputStream = field.getType() == FormFieldType.InputStream ? field.getInputStream() : Files.newInputStream(Objects.requireNonNull(file).toPath());
                            long skiped = inputStream.skip(field.getOffset());
                            LOGGER.debug("sikped {} bytes", skiped);
                            int bufferSize = 10240;
                            byte[] bytes = new byte[bufferSize];
                            long remainLength = field.getLimit();
                            while (remainLength > 0) {
                                int maxReadLen = (int) (remainLength < bufferSize ? remainLength : bufferSize);
                                int length = inputStream.read(bytes, 0, maxReadLen);
                                if (length == -1) {
                                    break;
                                }
                                out.write(bytes, 0, length);
                                remainLength -= length;

                                //md5
                                Objects.requireNonNull(messageDigest).update(bytes, 0, length);
                            }
                            inputStream.close();

//						md5 = new String(Hex.encodeHex(messageDigest.digest()));
                            md5s.add(new FormField(FormFieldType.Text, name + "Md5", md5));
                            break;
                        case Password:
                        default:
                            break;
                    }
                }

                // md5
                for (FormField field : md5s) {
                    StringBuilder strBuf = new StringBuilder();
                    String name = field.getName();
                    String value = field.getValue();
                    strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
                    strBuf.append("Content-Disposition: form-data; name=\"").append(name).append("\"\r\n\r\n");
                    strBuf.append(value);
                    out.write(strBuf.toString().getBytes(StandardCharsets.UTF_8));
                }
            }
            out.write(("\r\n--" + BOUNDARY + "--\r\n").getBytes(StandardCharsets.UTF_8));
            out.flush();
            out.close();
            // 读取返回数据
            result.setCode(conn.getResponseCode());
            result.setBody(IOUtils.toString(conn.getInputStream(), StandardCharsets.UTF_8));
        } catch (IOException ioe) {
            throw ioe;
        } catch (Throwable th) {
            throw new IOException(th.getMessage(), th);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return result;
    }

    public static class HttpResult {

        private int code = 500;
        private String url = "";
        private String body = "";

        public HttpResult() {
        }

        public HttpResult(int code, String url) {
            super();
            this.code = code;
            this.url = url;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        @Override
        public String toString() {
            return JSON.toJSONString(this);
        }

    }

    public static class FormField {

        private String name;
        private String value;
        private File file;
        private Long offset = 0L;
        private Long limit = Long.MAX_VALUE;
        private FormFieldType type;
        private InputStream inputStream;
        private String orignFilename;
        private String mimeType;

        public FormField() {
            this.type = FormFieldType.Text;
        }

        public FormField(String name, String value) {
            this();
            this.name = name;
            this.value = value;
        }

        public FormField(FormFieldType type, String name, Object value) {
            this.name = name;
            if (type == null) {
                type = FormFieldType.Text;
            }
            this.type = type;

            switch (type) {
                case File:
                    if (value != null) {
                        this.file = (File) value;
                        this.value = this.file.getPath();
                        this.offset = 0L;
                        this.limit = file.length();
                    }
                    break;
                case InputStream:
                    if (value != null) {
                        this.inputStream = (InputStream) value;
                    }
                    break;
                default:
                    this.value = value == null ? "" : String.valueOf(value);
                    break;
            }
        }

        public FormField(String name, File file, Long offset, Long limit) {
            this.type = FormFieldType.File;
            this.name = name;
            this.file = file;
            this.orignFilename = file.getName();
            this.offset = offset;
            this.limit = limit;
        }

        public FormFieldType getType() {
            return type;
        }

        public void setType(FormFieldType type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public File getFile() {
            return file;
        }

        public void setFile(File file) {
            this.file = file;
        }

        public Long getOffset() {
            return offset;
        }

        public void setOffset(Long offset) {
            this.offset = offset;
        }

        public Long getLimit() {
            return limit;
        }

        public void setLimit(Long limit) {
            this.limit = limit;
        }

        public InputStream getInputStream() {
            return inputStream;
        }

        public void setInputStream(InputStream inputStream) {
            this.inputStream = inputStream;
        }

        public String getOrignFilename() {
            return orignFilename;
        }

        public void setOrignFilename(String orignFilename) {
            this.orignFilename = orignFilename;
        }

        public String getMimeType() {
            return mimeType;
        }

        public void setMimeType(String mimeType) {
            this.mimeType = mimeType;
        }

        public enum FormFieldType {
            File,
            Text,
            Password,
            InputStream
        }

    }

}
