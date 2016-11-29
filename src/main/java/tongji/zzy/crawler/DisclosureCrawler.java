package tongji.zzy.crawler;

import java.sql.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;

import tongji.zzy.handler.HandlerWithJson;
import tongji.zzy.model.DisclosureInfo;
import tongji.zzy.resource.DisclosureCategory;
import tongji.zzy.resource.FCLog;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

@SuppressWarnings("Duplicates")
public class DisclosureCrawler implements PageProcessor {
	private static final Logger logger = FCLog.getLogger(DisclosureCrawler.class);

	private Site site = Site.me().setRetryTimes(5).setSleepTime(10000);
	private static String currentDisclosureCategory = "";
	private static String TARGET_CONTENT_URL = "http://data\\.eastmoney\\.com/notice/\\d+/\\w+.html";
	private static String LIST_URL = "http://data\\.eastmoney\\.com/Notice/Noticelist\\.aspx\\?.*page=\\d+";

	@Override
	public Site getSite() {
		return site;
	}

	@Override
	public void process(Page page) {
		if (page.getUrl().regex(LIST_URL).match()) {
			System.out.println("This is the list page...");

			// add content urls
			List<String> targetContentUrls = page.getHtml()
					.xpath("/html/body/div[@class='mainFrame']//table[@class='tableCont']/tbody").links()
					.regex(TARGET_CONTENT_URL).all();
			page.addTargetRequests(targetContentUrls);

			// add list urls
			List<String> listUrls = page.getHtml().xpath("/html/body/div[@class='mainFrame']//div[@class='PageNav']")
					.links().regex(LIST_URL).all();
			page.addTargetRequests(listUrls);
		} else if (page.getUrl().regex(TARGET_CONTENT_URL).match()) {
			System.out.println("This is the conent page...");

			String date_raw = page.getHtml().xpath("html/body//div[@class='content']//h5/text()").toString();
			Date date = Date.valueOf(date_raw.split("：")[1]);
			String title = page.getHtml().xpath("html/body//div[@class='content']//h4/text()").toString();
			String content = page.getHtml().xpath("html/body//div[@class='content']//pre/text()").toString();

			// find companyName and companyCode from content text(first n
			// characters) using regular expression
			Pattern pattern = Pattern.compile(".*代码：(\\d+).*简称：(\\S+).*");
			Matcher matcher = pattern.matcher(content.subSequence(0, Math.min(100, content.length())));
			String companyCode = "";
			String companyName = "";
			if (matcher.find()) {
				companyCode = matcher.group(1);
				companyName = matcher.group(2);

			} else {
				System.out.println("companyCode and companyName not found in the content text.");
			}

			// create the DisclosureInfo entity
			DisclosureInfo disclosureInfo = new DisclosureInfo();
			disclosureInfo.setUrl(page.getUrl().toString());
			disclosureInfo.setDisclosureCategory(currentDisclosureCategory);
			disclosureInfo.setDate(date);
			disclosureInfo.setTitle(title);
			disclosureInfo.setContent(String.valueOf(content.length()));
			disclosureInfo.setCompanyCode(companyCode);
			disclosureInfo.setCompanyName(companyName);

			// convert to JSon string
			String jsonString = HandlerWithJson.convertJavaClassToJsonStream(disclosureInfo);

		}
	}

	public static void main(String[] args) {
		// Spider.create(new DisclosureCrawler())
		// .addUrl("http://data.eastmoney.com/Notice/Noticelist.aspx?type=1&market=all&date=&page=1")
		// .run();

		// run different spider according to different disclosure categories
		for (DisclosureCategory dc : DisclosureCategory.values()) {
			currentDisclosureCategory = dc.name();
			Spider.create(new DisclosureCrawler()).addUrl(getSpecifiedDisclosureCategoryUrl(dc.toString())).run();
		}

	}

	// get different disclosure categories' starting url
	private static String getSpecifiedDisclosureCategoryUrl(String categoryCode) {
		return "http://data.eastmoney.com/Notice/Noticelist.aspx?page=&market=all&date=&type=" + categoryCode;
	}

}
