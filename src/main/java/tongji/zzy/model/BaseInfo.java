package tongji.zzy.model;

import java.util.Date;

public class BaseInfo {
	//信息来源地址
	protected String url;
	//时间
	protected Date date;
	//标题
	protected String title;
	//正文
	protected String content;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
