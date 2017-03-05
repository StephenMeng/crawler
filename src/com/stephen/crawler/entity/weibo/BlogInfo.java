package com.stephen.crawler.entity.weibo;

public class BlogInfo {
	private String author;
	private String date;
	private String time;
	private String reweibo;
	private String comments;
	private String praise;
	private String content;

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getReweibo() {
		return reweibo;
	}

	public void setReweibo(String reweibo) {
		this.reweibo = reweibo;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getPraise() {
		return praise;
	}

	public void setPraise(String praise) {
		this.praise = praise;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public BlogInfo(String author, String date, String time, String reweibo, String comments, String praise,
			String content) {
		super();
		this.author = author;
		this.date = date;
		this.time = time;
		this.reweibo = reweibo;
		this.comments = comments;
		this.praise = praise;
		this.content = content;
	}

	public BlogInfo() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "WeiboInfo [author=" + author + ", date=" + date + ", time=" + time + ", reweibo=" + reweibo
				+ ", comments=" + comments + ", praise=" + praise + ", content=" + content + "]";
	}

}
