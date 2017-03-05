package com.stephen.crawler.core;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.entity.StringEntity;

public class UrlConfig {
	public static final int GET = 0;
	public static final int POST = 1;

	public static final String DEFAULT_CHARSET = "UTF-8";

	public boolean isGZIP = false;
	private String url;
	private Map<String, String> headers = new HashMap<>();
	private StringEntity postStringEntity = null;
	private UrlEncodedFormEntity posturlEntity;
	private HttpHost proxy = null;
	private RequestConfig requestConfig = null;
	
	private String charset = DEFAULT_CHARSET;
	private int requestMethod = GET;
	public static String rootFile;
	public void setGZIP(boolean isGZIP) {
		this.isGZIP = isGZIP;
	}

	public void setPosturlEntity(UrlEncodedFormEntity posturlEntity) {
		this.posturlEntity = posturlEntity;
	}

	public void setRootFile(String rootFile) {
		this.rootFile = rootFile;
		File file=new File(rootFile);
		if(!file.exists()){
			file.mkdirs();
		}
	}

	public UrlEncodedFormEntity getPosturlEntity() {
		return posturlEntity;
	}

	public static String getDefaultCharset() {
		return DEFAULT_CHARSET;
	}

	public String getCharset() {
		return charset;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public StringEntity getPostStringEntity() {
		return postStringEntity;
	}

	public String getUrl() {
		return url;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public void addHeaders(String key, String value) {
		this.headers.put(key, value);
	}

	public void setPostStringEntity(StringEntity postEntity) {
		this.postStringEntity = postEntity;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getRequestMethod() {
		return requestMethod;
	}

	public void setRequestMethod(int requestMethod) {
		this.requestMethod = requestMethod;
	}

	public HttpHost getProxy() {
		return proxy;
	}

	public void setProxy(HttpHost proxy) {
		this.proxy = proxy;
	}

	public RequestConfig getRequestConfig() {
		return requestConfig;
	}

	public void setRequestConfig(RequestConfig requestConfig) {
		this.requestConfig = requestConfig;
	}

	@Override
	public String toString() {
		return "UrlConfig [isGZIP=" + isGZIP + ", url=" + url + ", headers=" + headers + ", postStringEntity="
				+ postStringEntity + ", posturlEntity=" + posturlEntity + ", proxy=" + proxy + ", requestConfig="
				+ requestConfig + ", charset=" + charset + ", requestMethod=" + requestMethod + "]";
	}
}
