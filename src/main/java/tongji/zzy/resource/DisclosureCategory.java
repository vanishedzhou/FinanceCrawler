package tongji.zzy.resource;

public enum DisclosureCategory {

	定期报告(1), 融资公告(2), 权益分配及变动(3), 风险提示(4), 基本信息变更(5), 经营事项(6), 重大事项(7), 股东大会及其他(8);

	private int categoryCode;
	
	private DisclosureCategory(int categoryCode) {
		this.categoryCode = categoryCode;
	}
	
	@Override
	public String toString() {
		return String.valueOf(this.categoryCode);
	}

}
