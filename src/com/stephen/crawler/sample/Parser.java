package com.stephen.crawler.sample;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {


	public static int getPageCount(String htmlContent) {
		String pageCount = "";
//		Pattern pattern = Pattern.compile("class=\"W_f12\">\\d+</strong>");
//		Matcher matcher = pattern.matcher(htmlContent);
//		while (matcher.find()) {
//			pageCount = matcher.group().replaceAll("class=\"W_f12\">|</strong>", "");
//		}
		System.out.println(htmlContent);

		for (int i = 2; i < 9; i++) {
			if (pageCount.equals("")) {
				Pattern pattern = Pattern.compile("class=\\\"W_f16\\\">[\\d+]<\\/strong>");
				Matcher matcher = pattern.matcher(htmlContent);
				while (matcher.find()) {
					System.out.println("find");
					pageCount = matcher.group().replaceAll("class=\"W_f1" + i + "\">|</strong>", "");
				}
				
			}
			if (pageCount.length()>0) {
				break;
			}
			System.out.println("class=\\\"W_f16\\\">\\d+</strong>");
			i++;
		}
		System.out.println("pageCount" + pageCount);
		return Integer.parseInt(pageCount);
	}

	public static String getPageName(String htmlContent) {
		String pageName = "";
		Pattern pattern = Pattern.compile("CONFIG\\['onick'\\]='[^']*'");
		Matcher matcher = pattern.matcher(htmlContent);
		while (matcher.find()) {
			pageName = matcher.group().replace("'", "").replace("CONFIG[onick]=", "");
		}
		return pageName;
	}

	public static String getPageDomain(String htmlContent) {
		String pageDomain = "";
		Pattern pattern = Pattern.compile("CONFIG\\['domain'\\]='\\d+'");
		Matcher matcher = pattern.matcher(htmlContent);
		while (matcher.find()) {
			pageDomain = matcher.group().replace("'", "").replace("CONFIG[domain]=", "");
		}
		return pageDomain;
	}

	public static String getPageId(String htmlContent) {
		String pageId = "";
		Pattern pattern = Pattern.compile("CONFIG\\['page_id'\\]='\\d+'");
		Matcher matcher = pattern.matcher(htmlContent);
		while (matcher.find()) {
			pageId = matcher.group().replace("'", "").replace("CONFIG[page_id]=", "");
		}
		return pageId;
	}

}
