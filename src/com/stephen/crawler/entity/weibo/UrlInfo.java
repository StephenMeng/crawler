package com.stephen.crawler.entity.weibo;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class UrlInfo {
	private String pageUrl;
	private String[] pageUrlBar;
	private int LENGTH = 2;

	public UrlInfo(String domain, int page, String id, String pageName) {
		setPageBar(domain, page, id, pageName);
		createPageUrl(pageName);
	}
	public String getPageUrl() {
		return pageUrl;
	}
	public String[] getPageUrlBar() {
		return pageUrlBar;
	}
	public List<String> getUrlList() {
		List<String> urls = new LinkedList();
		urls.add(pageUrl);
		for (String pagebar : pageUrlBar) {
			urls.add(pagebar);
		}
		return urls;
	}

	public void createPageUrl(String pageName) {
		this.pageUrl = "http://weibo.com/" + pageName
				+ "?is_search=0&visible=0&is_all=1&is_tag=0&profile_ftype=1&page=1#feedtop";
	}

	public void setPageUrl(int page, String pageName) {
		this.pageUrl = "http://weibo.com/" + pageName + "?is_search=0&visible=0&is_all=1&is_tag=0&profile_ftype=1&page="
				+ page + "#feedtop";
	}

	public void setPageBar(String domain, int page, String id, String pageName) {
		pageUrlBar = new String[LENGTH];
		for (int i = 0; i < LENGTH; i++) {
			pageUrlBar[i] = "http://weibo.com/p/aj/v6/mblog/mbloglist?ajwvr=6&domain=" + domain + "&is_search=0"
					+ "&visible=1&is_all=1&is_tag=0&profile_ftype=1&page=" + page + "&pagebar=" + i
					+ "&pl_name=Pl_Official_MyProfileFeed__26&" + "id=" + id + "&script_uri=/" + pageName
					+ "&feed_type=0&pre_page=" + page;
		}
	}

	@Override
	public String toString() {
		return "UrlInfo [pageUrl=" + pageUrl + ", pageUrlBar=" + Arrays.toString(pageUrlBar) + "]";
	}

}
