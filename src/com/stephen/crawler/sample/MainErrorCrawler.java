package com.stephen.crawler.sample;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.stephen.crawler.core.Crawler;
import com.stephen.crawler.core.Crawler.CrawlerManagement;
import com.stephen.crawler.core.UrlConfig;
import com.stephen.crawler.entity.weibo.BlogInfo;
import com.stephen.crawler.entity.weibo.BloggerInfo;
import com.stephen.crawler.utils.FileUtils;
import com.stephen.crawler.utils.TransferCode;


public class MainErrorCrawler {
	private static int cal = 0;
	private static int count = 1;
	private static String ERROR_PATH;
	private static int countUrl = 0;

	public static void main(String[] args) throws NumberFormatException, IOException {
		
		CrawlerManagement cm = new WBCrawler();
		
		//输入文件的文件名
		String inputFilename="url.txt";
		//数据存储根目录
		cm.getUrlConfig().rootFile="c:/users/147/desktop/errordata/";
		
		
		Crawler crawler = new Crawler(cm);
		ERROR_PATH = cm.getUrlConfig().rootFile + "error.txt";
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(new FileInputStream(new File(UrlConfig.rootFile + inputFilename))));
		String line = null;
		List<Object> results = null;
		while ((line = reader.readLine()) != null) {
			results = new ArrayList<>();
			countUrl++;
			try {
				// 爬取微博信息
				cm.getUrlConfig().setUrl(line);
				String firstShow = crawler.getRequest(cm.getUrlConfig());
				results.addAll(matchFirst(TransferCode.decodeUnicode(firstShow), line));
				int i = count / 50;
				count = FileUtils.save2Excel(results, cm.getUrlConfig().rootFile + "result/buchong" + i + ".xls", count);
			} catch (Exception e) {
				e.printStackTrace();
				FileUtils.saveInfo(ERROR_PATH, line);
			}
		}

	}

	private static BloggerInfo getUrlsInfo(CrawlerManagement cm, Crawler crawler, String url, int totalCount) {
		String htmlContent = crawler.getRequest(cm.getUrlConfig());
		String domain = Parser.getPageDomain(htmlContent);
		String id = Parser.getPageId(htmlContent);
		String name = Parser.getPageName(htmlContent);
		System.out.println("领域信息：" + domain + "|" + id + "|" + name);
		// 初始化博主信息
		BloggerInfo bloggerInfo = new BloggerInfo(url, name, id, domain, totalCount, 0, 0);
		return bloggerInfo;
	}

	public static List<BlogInfo> matchFirst(String weiboHtml, String url) {
		List<BlogInfo> blogs = new ArrayList<>();
		String regex3 = "tbinfo([^屏蔽]*?)WB_feed_repeat";
		Pattern pattern = Pattern.compile(regex3);
		Matcher matcher = pattern.matcher(weiboHtml);
		int i = 0;
		while (matcher.find()) {
			i++;
			String div = "<div " + matcher.group() + " S_bg1\" style=\"display:none;\"></div></div>";
			blogs.add(parser(div));
		}
		cal = i;
		System.out.println("第" + countUrl + "个URL，成功采集到：" + i + "条，共" + cal + "条-->" + url);
		if (i == 0) {
			System.out.println(url);
			FileUtils.saveInfo(ERROR_PATH, url);
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
}