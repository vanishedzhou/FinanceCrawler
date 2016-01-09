package tongji.zzy.crawler;

import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

public class DisclosureCrawler implements PageProcessor {
	private Site site = Site.me().setRetryTimes(5).setSleepTime(10000);
	private static String TARGET_CONTENT_URL = "http://data.eastmoney.com/notice/[0-9]+/\\w+.html";
	private static String LIST_URL = "http://http://data.eastmoney.com/Notice/Noticelist.aspx?*page=[0-9]+";

	@Override
	public Site getSite() {
		return site;
	}

	@Override
	public void process(Page page) {
		if(page.getUrl().regex(LIST_URL).match()) {
			System.out.println("This is the list page...");

			//add content urls
			List<String> targetContentUrls = page.getHtml()
				.xpath("/html/body/div[@class='mainFrame']//table[@class='tableCont']/tbody")
				.links().regex(TARGET_CONTENT_URL).all();
			page.addTargetRequests(targetContentUrls);
			
			//add list urls
			List<String > listUrls = page.getHtml()
				.xpath("/html/body/div[@class='mainFrame']//div[@class='PageNav']")
				.links().regex(LIST_URL).all();
			page.addTargetRequests(listUrls);
		} else if (page.getUrl().regex(TARGET_CONTENT_URL).match()) {
			System.out.println("This is the conent page...");
		}
		
	}

	public static void main(String[] args) {
		Spider.create(new DisclosureCrawler())
			.addUrl("http://data.eastmoney.com/Notice/Noticelist.aspx?type=0&market=all&date=&page=1")
			.run();

	}

}
