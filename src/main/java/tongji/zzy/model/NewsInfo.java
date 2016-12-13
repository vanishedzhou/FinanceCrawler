package tongji.zzy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class NewsInfo extends BaseInfo {
	//信息来源（机构、作者）
	protected String infoSource;
	//评论
	protected String comments;

	@JsonIgnore
	public static final String index = "news";
	@JsonIgnore
	public String typeNewsSource = "";
	@JsonIgnore
	public String documentNewsSection = "";

	public static void main(String[] args) {
		NewsInfo newsInfo = new NewsInfo();

	}

	public String getInfoSource() {
		return infoSource;
	}

	public void setInfoSource(String infoSource) {
		this.infoSource = infoSource;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
}
