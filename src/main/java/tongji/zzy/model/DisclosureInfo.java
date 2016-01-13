package tongji.zzy.model;

public class DisclosureInfo extends BaseInfo {
	public String companyCode;
	public String companyName;
	public String disclosureCategory;

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getCompanySimpleName() {
		return companyName;
	}

	public void setCompanySimpleName(String companySimpleName) {
		this.companyName = companySimpleName;
	}

	public String getDisclosureCategory() {
		return disclosureCategory;
	}

	public void setDisclosureCategory(String disclosureCategory) {
		this.disclosureCategory = disclosureCategory;
	}

}
