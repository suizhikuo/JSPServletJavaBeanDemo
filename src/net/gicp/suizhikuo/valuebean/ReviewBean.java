package net.gicp.suizhikuo.valuebean;

public class ReviewBean {
	private int id = -1;
	private int articleId = -1;
	private String author = "";
	private String content = "";
	private String sdTime = "";
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getArticleId() {
		return articleId;
	}
	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSdTime() {
		return sdTime;
	}
	public void setSdTime(String sdTime) {
		this.sdTime = sdTime;
	}

	
}
