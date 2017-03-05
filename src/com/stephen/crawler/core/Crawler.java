package com.stephen.crawler.core;

import java.util.List;

import com.stephen.crawler.utils.CrawlerUtils;

public class Crawler {
	private CrawlerManagement cm;
	private UrlConfig config;

	public interface CrawlerManagement {

		public UrlConfig getUrlConfig();

		public List<Object> parseHtml(String...string);

	}

	public Crawler(CrawlerManagement cm) {
		this.cm = cm;
		this.config = cm.getUrlConfig();
	}

	public String getRequest(UrlConfig config) {
		return CrawlerUtils.getResponse(config);
	}

	public List<Object> parseHtml(String html) {
		return cm.parseHtml(html);
	}

	public List<Object> startCrawl() {
		System.out.println(config.getUrl());
		String html = getRequest(config);
		List<Object> results = parseHtml(html);
		return results;
	}
}
