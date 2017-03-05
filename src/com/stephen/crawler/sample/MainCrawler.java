package com.stephen.crawler.sample;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.stephen.crawler.core.Crawler;
import com.stephen.crawler.core.UrlConfig;
import com.stephen.crawler.core.Crawler.CrawlerManagement;
import com.stephen.crawler.entity.weibo.BloggerInfo;
import com.stephen.crawler.utils.FileUtils;
import com.stephen.crawler.utils.TransferCode;

public class MainCrawler {
	private static int cal = 0;
	private static String ERROR_PATH;

	public static void main(String[] args) throws NumberFormatException, IOException {
		CrawlerManagement cm = new WBCrawler();

		// 输入文件的文件名
		String inputFilename = "wb.txt";
		// 数据存储根目录
		cm.getUrlConfig().rootFile = "c:/users/stephen/desktop/";

		Crawler crawler = new Crawler(cm);
		ERROR_PATH = cm.getUrlConfig().rootFile + "error.txt";
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(new FileInputStream(new File(UrlConfig.rootFile + inputFilename))));
		System.out.println(UrlConfig.rootFile + inputFilename);
		String line = null;
		FileWriter fileWriter = new FileWriter(new File("c:/users/stephen/desktop/wb-result3.txt"), true);
		while ((line = reader.readLine()) != null) {
			// crawBlog(cm, crawler, line);
			try{
				System.out.println(line);
			cm.getUrlConfig().setUrl(line);
			String data = crawler.getRequest(cm.getUrlConfig());
			if (data.contains("W_icon icon_pf_male")) {
				System.out.println("male");
				fileWriter.write(line + "\t" + "male" + "\r\n");
			}
			else if (data.contains("W_icon icon_pf_female")) {
				{
					System.out.println("female");
					fileWriter.write(line + "\t" + "female" + "\r\n");
				}
			}else{
				System.out.println("female");
				fileWriter.write(line + "\t" + " " + "\r\n");
			}
			// String
			// result=document.select("div[class=PCD_header]").first().text();
			// System.out.println(data);
			// System.out.println(result);
			// break;
			}catch(Exception exception ){
				fileWriter.write(line + "\t" + " " + "\r\n");

			}

		}
		fileWriter.close();
	}

	private static void crawBlog(CrawlerManagement cm, Crawler crawler, String line) {
		System.out.println(line);
		cal = 0;
		String url = line.split("\t")[0];
		int totalCount = Integer.parseInt(line.split("\t")[1]);
		System.out.println(url + "|" + totalCount);

		cm.getUrlConfig().setUrl(url);
		BloggerInfo bloggerInfo = getUrlsInfo(cm, crawler, url, totalCount);
		for (int page = 1; page <= totalCount / 45 + 1; page++) {
			List<Object> results = new ArrayList<>();
			try {
				System.out.println(bloggerInfo.getPageName() + "	->第 " + page + "页	-->" + url);
				bloggerInfo.updateUrlInfo(page);
				// 爬取微博信息
				cm.getUrlConfig().setUrl(bloggerInfo.getUrlinfo().getPageUrl());
				String firstShow = crawler.getRequest(cm.getUrlConfig());
				results.addAll(
						cm.parseHtml(TransferCode.decodeUnicode(firstShow), bloggerInfo.getUrlinfo().getPageUrl()));
				cm.getUrlConfig().setUrl(bloggerInfo.getUrlinfo().getPageUrlBar()[0]);
				String secondShow = crawler.getRequest(cm.getUrlConfig());
				results.addAll(cm.parseHtml(TransferCode.decodeUnicode(secondShow),
						bloggerInfo.getUrlinfo().getPageUrlBar()[0]));
				cm.getUrlConfig().setUrl((bloggerInfo.getUrlinfo().getPageUrlBar()[1]));
				String thirdShow = crawler.getRequest(cm.getUrlConfig());
				results.addAll(cm.parseHtml(TransferCode.decodeUnicode(thirdShow),
						bloggerInfo.getUrlinfo().getPageUrlBar()[1]));
				FileUtils.save2Excel(results, cm.getUrlConfig().rootFile + bloggerInfo.getPageName() + ".xls", cal);
			} catch (Exception e) {
				e.printStackTrace();
				FileUtils.saveInfo(ERROR_PATH, url);
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

}