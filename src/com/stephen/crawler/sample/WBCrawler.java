package com.stephen.crawler.sample;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.client.config.RequestConfig;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.stephen.crawler.core.Crawler.CrawlerManagement;
import com.stephen.crawler.core.UrlConfig;
import com.stephen.crawler.entity.weibo.BlogInfo;
import com.stephen.crawler.utils.FileUtils;

public class WBCrawler implements CrawlerManagement {

	private UrlConfig urlConfig;
	private static String errorFilePath;

	public WBCrawler() {
		UrlConfig config = new UrlConfig();
		RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(10000).setConnectTimeout(10000)
				.setSocketTimeout(10000).build();

		// config.setRootFile("c:/users/stephen/desktop/");
		// config.setUrl("http://weibo.com/p/1002061739746697?profile_ftype=1&is_all=1#_0");
		config.addHeaders("Cookie", getCookie());
		config.addHeaders("Host", "weibo.com");
		config.setRequestConfig(requestConfig);
		this.urlConfig = config;
		this.errorFilePath = config.rootFile + "error.txt";
	}

	@Override
	public UrlConfig getUrlConfig() {
		return urlConfig;
	}

	@Override
	public List<Object> parseHtml(String... string) {
		return match(string[0], string[1]);
	}

	public static List<Object> match(String weiboHtml, String url) {
		List<Object> blogs = new ArrayList<>();
		String regex3 = "tbinfo([^屏蔽]*?)WB_feed_repeat";
		Pattern pattern = Pattern.compile(regex3);
		Matcher matcher = pattern.matcher(weiboHtml);
		int i = 0;
		while (matcher.find()) {
			i++;
			String div = "<div " + matcher.group() + " S_bg1\" style=\"display:none;\"></div></div>";
			blogs.add(parser(div));
		}
		System.out.println("成功采集到：" + i + "条");
		if (i == 0) {
			FileUtils.saveInfo(errorFilePath, url);
		}
		return blogs;
	}

	private static BlogInfo parser(String div) {
		Document document = Jsoup.parse(div);
		BlogInfo weiboInfo = new BlogInfo();
		try {
			weiboInfo.setAuthor(document.select("div[class=WB_info]").first().text());
		} catch (Exception e) {
		}
		try {
			weiboInfo.setDate(document.select("div[class=WB_from S_txt2]").first().text());
		} catch (Exception e) {
		}
		try {
			weiboInfo.setTime(System.currentTimeMillis() + "");
		} catch (Exception e) {
		}
		try {
			weiboInfo.setReweibo(document.select("div[class=WB_feed_handle]").first().text());
		} catch (Exception e) {
		}
		try {
			weiboInfo.setComments("");
		} catch (Exception e) {
		}
		try {
			weiboInfo.setPraise("");
		} catch (Exception e) {
		}
		try {
			weiboInfo.setContent(document.select("div[node-type=feed_list_content]").first().text());
		} catch (Exception e) {
		}
		return weiboInfo;
	}

	public String getCookie() {
		return "SUB=_2AkMve7aDf8NhqwJRmP4UyWjkbo1-yQ3EieLBAH7sJRMxHRl-yT83qkxftRBGWq6twhYTxp5akgYDuCj2VwCrnA..; SUBP=0033WrSXqPxfM72-Ws9jqgMF55529P9D9WW8aA-r0Un7pXAI8vhZS0pO; SINAGLOBAL=5532314395199.153.1479349304547; UOR=cartoon.tudou.com,widget.weibo.com,baike.baidu.com; YF-Page-G0=7b9ec0e98d1ec5668c6906382e96b5db; _s_tentry=-; Apache=5405219151167.933.1484299161327; ULV=1484299161382:8:2:1:5405219151167.933.1484299161327:1483335980961";
	}
}
