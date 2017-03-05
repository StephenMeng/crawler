package com.stephen.crawler.entity.weibo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BloggerInfo {
	private String rootUrl;
	private String userName;
	private String userId;
	private String userDomain;
	private String pageName;
	private long blogAmount;
	private long fans;
	private long followr;
	private UrlInfo urlinfo;

	public void updateUrlInfo(int page) {
		urlinfo.setPageUrl(page, pageName);
		urlinfo.setPageBar(userDomain, page, userId, pageName);
	}

	private void createPageName(String rootUrl) {
		Pattern pattern = Pattern.compile("com[^?]*/?");

		Matcher matcher = pattern.matcher(rootUrl);
		int i = 0;
		while (matcher.find()) {
			pageName = matcher.group().replaceAll("com/", "");
		}
	}

	public BloggerInfo(String rootUrl, String userName, String userId, String userDomain, long blogAmount, long fans,
			long followr) {
		super();
		this.rootUrl = rootUrl;
		this.userName = userName;
		this.userId = userId;
		this.userDomain = userDomain;
		this.blogAmount = blogAmount;
		this.fans = fans;
		this.followr = followr;
		createPageName(rootUrl);
		this.urlinfo = new UrlInfo(userDomain, 1, userId, pageName);
	}

	public String getRootUrl() {
		return rootUrl;
	}

	public void setRootUrl(String rootUrl) {
		this.rootUrl = rootUrl;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserDomain() {
		return userDomain;
	}

	public void setUserDomain(String userDomain) {
		this.userDomain = userDomain;
	}

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	public long getBlogAmount() {
		return blogAmount;
	}

	public void setBlogAmount(long blogAmount) {
		this.blogAmount = blogAmount;
	}

	public long getFans() {
		return fans;
	}

	public void setFans(long fans) {
		this.fans = fans;
	}

	public long getFollowr() {
		return followr;
	}

	public void setFollowr(long followr) {
		this.followr = followr;
	}

	public UrlInfo getUrlinfo() {
		return urlinfo;
	}

	public void setUrlinfo(UrlInfo urlinfo) {
		this.urlinfo = urlinfo;
	}

	@Override
	public String toString() {
		return "BloggerInfo [rootUrl=" + rootUrl + ", userName=" + userName + ", userId=" + userId + ", userDomain="
				+ userDomain + ", pageName=" + pageName + ", blogAmount=" + blogAmount + "]"+"\n"+urlinfo.toString();
	}

}
