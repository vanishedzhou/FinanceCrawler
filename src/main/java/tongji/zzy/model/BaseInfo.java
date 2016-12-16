package tongji.zzy.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class BaseInfo {
	//信息来源地址
	protected String url;
	//时间
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
	protected Date date;
	//标题
	protected String title;
	//正文
	protected String content;
	//冗余字段1
	protected String ext1;
	//冗余字段2
	protected String ext2;

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

	public String getExt1() {
		return ext1;
	}

	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}

	public String getExt2() {
		return ext2;
	}

	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}
}
