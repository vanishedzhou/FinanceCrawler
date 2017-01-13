package tongji.zzy.crawler;

import org.slf4j.Logger;
import tongji.zzy.handler.HandlerWithJson;
import tongji.zzy.model.DisclosureInfo;
import tongji.zzy.model.NewsInfo;
import tongji.zzy.resource.FCLog;
import tongji.zzy.transfer.ElasticSearchStorage;
import tongji.zzy.utils.CrawlerDateUtils;
import tongji.zzy.utils.CrawlerStringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressWarnings("Duplicates")
public class DisclosureCrawler implements PageProcessor{
	private static final Logger logger = FCLog.getLogger(DisclosureCrawler.class);
	protected String listUrlPattern = "http://data\\.eastmoney\\.com/Notice/Noticelist\\.aspx\\?.*page=\\d+";
	protected String contentUrlPattern = "http://www\\.shfe\\.com\\.cn/news/notice/\\d+\\.html";

	public static int pageCounts = 0;

	private Site site = Site.me().setRetryTimes(5).setSleepTime(10000);

	public static void main(String[] args) {
		Spider.create(new DisclosureCrawler()).addUrl("http://www.shfe.com.cn/news/notice/index.html")
				// .addPipeline(new ConsolePipeline())
				// .addPipeline(new FilePipeline("result"))
				.thread(1)
				.run();
	}

	@Override
	public void process(Page page) {

		//上期所-公告
		if(page.getUrl().regex(contentUrlPattern).match()) {
			System.out.println("this is a content page...");
			String titleXPath = "/html/body//div[@class='article-detail-text']/h1/text()";
			String dateXPath = "/html/body//div[@class='article-detail-text']/p[@class='article-date']/text()";
//			String infoSourceXPath = "/html/body//div[@class='layout mg articleName']//div[@class='clearfix']/div/a/text()";
			String contentXPath = "/html/body//div[@class='article-detail-text']/p[@style='text-align: left']/text()";

			String title = page.getHtml().xpath(titleXPath).toString();
			String dateStrRaw = page.getHtml().xpath(dateXPath).toString();
			String dateStr = CrawlerDateUtils.extractDateContent(dateStrRaw);
			Date date = CrawlerDateUtils.parseStringToDateSimpleOne(dateStr);
			String content = page.getHtml().xpath(contentXPath).toString();
			content = CrawlerStringUtils.removeHtmlElements(content);

			DisclosureInfo disclosureInfo = new DisclosureInfo();
			disclosureInfo.setUrl(page.getUrl().toString());
			disclosureInfo.setTitle(title);
			disclosureInfo.setDate(date);
			//上期所
			disclosureInfo.setTypeDisclosureSource("shfeDisclosure");
			disclosureInfo.setContent(content);


			//get newsinfo entity
			String jsonString = HandlerWithJson.convertJavaClassToJsonStream(disclosureInfo);
			logger.info("newsinfo json string: "+jsonString);
//			System.out.println(jsonString);

			//store data to es
			ElasticSearchStorage.esStorage(disclosureInfo.index,disclosureInfo.typeDisclosureSource,null, jsonString);
		} else if(page.getUrl().regex("^.*www.shfe.com.cn/news/notice/index\\w*.html$").match()){
			List<String> disclosureContentUrls =
					page.getHtml()
							.xpath("/html/body//div[@class='p4 lawbox']/ul")
							.links()
							.regex(contentUrlPattern).all();
			page.addTargetRequests(disclosureContentUrls);

			//add next page url, first 40 pages
			if(pageCounts<=40) {
				String nextPageUrl = page.getHtml()
					.xpath("/html/body//div[@class='p4 lawbox']/div[@class='page-no']/a[3]")
					.links().get();
				page.addTargetRequest(nextPageUrl);

				pageCounts++;
			}

		}

	}

	@Override
	public Site getSite() {
		return site;
	}
}
