package tongji.zzy.crawler;

import org.slf4j.Logger;
import tongji.zzy.transfer.ElasticSearchStorage;
import tongji.zzy.utils.CrawlerDateUtils;
import tongji.zzy.utils.CrawlerStringUtils;
import tongji.zzy.handler.HandlerWithJson;
import tongji.zzy.model.NewsInfo;
import tongji.zzy.resource.FCLog;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.Date;
import java.util.List;

@SuppressWarnings("Duplicates")
public class NewsCrawler implements PageProcessor{
	private static final Logger logger = FCLog.getLogger(NewsCrawler.class);
	protected String listUrlPattern = "http://data\\.eastmoney\\.com/Notice/Noticelist\\.aspx\\?.*page=\\d+";
	protected String contentUrlPattern = ".*/[0-9-]+/[0-9]+\\.html";

	private Site site = Site.me().setRetryTimes(5).setSleepTime(10000);

	public static void main(String[] args) {
		Spider.create(new NewsCrawler()).addUrl("http://futures.hexun.com/")
				// .addPipeline(new ConsolePipeline())
				// .addPipeline(new FilePipeline("result"))
				.thread(1)
				.run();
	}

	@Override
	public void process(Page page) {
		//和讯期货-今日头条
		if(page.getUrl().regex(contentUrlPattern).match()) {
			System.out.println("this is a content page...");
			String titleXPath = "/html/body//div[@class='layout mg articleName']/h1/text()";
			String dateXPath = "/html/body//div[@class='layout mg articleName']//span[@class='pr20']/text()";
			String infoSourceXPath = "/html/body//div[@class='layout mg articleName']//div[@class='clearfix']/div/a/text()";
			String contentXPath = "/html/body//div[@class='layout mg clearfix']//div[@class='art_context']/div[@class='art_contextBox']";

			String title = page.getHtml().xpath(titleXPath).toString();
			String dateStr = page.getHtml().xpath(dateXPath).toString();
			Date date = CrawlerDateUtils.parseStringToDate(dateStr);
			String infoSource = page.getHtml().xpath(infoSourceXPath).toString();
			String content = page.getHtml().xpath(contentXPath).toString();
			content = CrawlerStringUtils.removeHtmlElements(content);

			NewsInfo newsInfo = new NewsInfo();
			newsInfo.setUrl(page.getUrl().toString());
			newsInfo.setTitle(title);
			newsInfo.setDate(date);
			newsInfo.setInfoSource(infoSource);
			newsInfo.setContent(content);
			//set typeNewsSource and document
			newsInfo.typeNewsSource = "hexunFutures";

			//get newsinfo entity
			String jsonString = HandlerWithJson.convertJavaClassToJsonStream(newsInfo);
			logger.info("newsinfo json string: "+jsonString);
//			System.out.println(jsonString);

			//store data to es
			ElasticSearchStorage.esStorage(newsInfo.index,newsInfo.typeNewsSource,null, jsonString);
		} else {
			System.out.println("news crawler started......");
			List<String> todayNewsLinks = page.getHtml()
					.xpath("/html/body/div[@class='contentCon wrap clear']//div[@id='to_zx'")
					.links().regex(contentUrlPattern).all();
			System.out.println(todayNewsLinks);
			page.addTargetRequests(todayNewsLinks);
		}

	}

	@Override
	public Site getSite() {
		return site;
	}
}
