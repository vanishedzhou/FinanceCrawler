package tongji.zzy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class DisclosureInfo extends BaseInfo {
	public String companyCode;
	public String companyName;
	public String disclosureCategory;


	@JsonIgnore
	public static final String index = "disclosure";
	@JsonIgnore
	public String typeDisclosureSource = "";
	@JsonIgnore
	public String documentDisclosureSection = "";

	public String getTypeDisclosureSource() {
		return typeDisclosureSource;
	}

	public void setTypeDisclosureSource(String typeDisclosureSource) {
		this.typeDisclosureSource = typeDisclosureSource;
	}

	public String getDocumentDisclosureSection() {
		return documentDisclosureSection;
	}

	public void setDocumentDisclosureSection(String documentDisclosureSection) {
		this.documentDisclosureSection = documentDisclosureSection;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companySimpleName) {
		this.companyName = companySimpleName;
	}

	public String getDisclosureCategory() {
		return disclosureCategory;
	}

	public void setDisclosureCategory(String disclosureCategory) {
		this.disclosureCategory = disclosureCategory;
	}

}
