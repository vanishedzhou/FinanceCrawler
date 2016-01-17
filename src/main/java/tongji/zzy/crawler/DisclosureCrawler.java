package tongji.zzy.crawler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

public class DisclosureCrawler implements PageProcessor {
	private static final Logger logger = FCLog.getLogger(DisclosureCrawler.class);
	
	//get today's date and set the date range within this day
	static Date date = new Date();
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private static String dateOfToday = sdf.format(date);

	private Site site = Site.me().setRetryTimes(5).setSleepTime(10000);
	private static String currentDisclosureCategory = "";
	private static final String TARGET_CONTENT_URL = "http://data\\.eastmoney\\.com/notice/\\d+/\\w+.html";
	private static final String LIST_URL = "http://data\\.eastmoney\\.com/Notice/Noticelist\\.aspx\\?.*page=\\d*.*";

	@Override
	public Site getSite() {
		return site;
	}

	@Override
	public void process(Page page) {
		//if this is a list url
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
		//if this is a content url
		} else if (page.getUrl().regex(TARGET_CONTENT_URL).match()) {
			System.out.println("This is the conent page...");

			String date_raw = page.getHtml().xpath("html/body//div[@class='content']//h5/text()").toString();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = null;
			try {
				date = sdf.parse(date_raw.split("：")[1]);
			} catch (ParseException e) {
				logger.error("invalid date format from web page" + e.getMessage());
			}
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
				System.out.println("companyCode and companyName are not found in the content text.");
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
			System.out.println(jsonString);
			
			//store the result to file
			String fileName = getStorableFileName(page.getUrl().toString());
			HandlerWithJson.storeJsonStreamToFile(disclosureInfo, fileName);

		}
	}

	public static void main(String[] args) {
		 Spider.create(new DisclosureCrawler())
		 .addUrl(getSpecifiedDisclosureCategoryUrl("6", "2016-01-15"))
		 .run();
		
		// run different spider according to different disclosure categories
//		for (DisclosureCategory dc : DisclosureCategory.values()) {
//			currentDisclosureCategory = dc.name();
//			String categoryCode = dc.toString();
//			String SpecifiedDisclosureCategoryUrl = getSpecifiedDisclosureCategoryUrl(categoryCode, "2016-01-15"); 
//			Spider.create(new DisclosureCrawler()).addUrl(SpecifiedDisclosureCategoryUrl).run();
//		}

	}
	
	private static  String getStorableFileName(String fileNameRelatedWithUrl) {
		return fileNameRelatedWithUrl.replaceAll("/", "~").replaceAll(":", "") + ".json";
	}

	// get different disclosure categories' starting url
	private static String getSpecifiedDisclosureCategoryUrl(String categoryCode, String date) {
		return "http://data.eastmoney.com/Notice/Noticelist.aspx?page=&market=all&date="+date+"&type=" + categoryCode;
	}

}
