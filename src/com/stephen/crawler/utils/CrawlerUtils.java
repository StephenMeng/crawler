package com.stephen.crawler.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;

import com.stephen.crawler.core.UrlConfig;

public class CrawlerUtils {

	public static String getResponse(UrlConfig urlConfig) {
		CloseableHttpClient client = HttpClientBuilder.create().build();
		HttpRequestBase request = null;
		// ÔOÖÃ³¬Ê±
		if (urlConfig.getProxy() != null) {
			client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, urlConfig.getProxy());
		}
		if (urlConfig.getRequestMethod() == UrlConfig.GET) {
			request = new HttpGet(urlConfig.getUrl());
		} else {
			request = new HttpPost(urlConfig.getUrl());
			if (urlConfig.getPostStringEntity() == null) {
				System.out.println("Alert:Post Entity is null!");
			} else {
				((HttpPost) request).setEntity(urlConfig.getPostStringEntity());
			}
		}
		if (urlConfig.getRequestConfig() != null) {
			request.setConfig(urlConfig.getRequestConfig());
		}
		if (urlConfig.getHeaders().size() > 0) {
			for (Map.Entry<String, String> map : urlConfig.getHeaders().entrySet()) {
				request.addHeader(new BasicHeader(map.getKey(), map.getValue()));
			}
		}
		try {
			BufferedReader bufferedReader = null;
			HttpResponse response = client.execute(request);
			HttpEntity entity = response.getEntity();
			if (urlConfig.isGZIP) {
				GZIPInputStream gzin = new GZIPInputStream(entity.getContent());
				bufferedReader = new BufferedReader(new InputStreamReader(gzin, urlConfig.getCharset()));
			} else {
				bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent(), urlConfig.getCharset()));
			}
			String len = null;
			byte[] bs = new byte[1024];
			StringBuffer html = new StringBuffer();
			while ((len = bufferedReader.readLine()) != null) {
				html.append(len);
			}
			return html.toString();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
