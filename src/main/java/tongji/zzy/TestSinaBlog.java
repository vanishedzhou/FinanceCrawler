package tongji.zzy;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

public class TestSinaBlog implements PageProcessor {
	private static final String URL_LIST = "http://blog.sina.com.cn/s/articlelist_1487828712_0_\\d+.html";
	private static final String URL_POST = "http://blog.sina.com.cn/s/blog_\\w+.html";
	private Site site = Site.me().setDomain("http://blog.sina.com").setRetryTimes(3).setSleepTime(10000);

	@Override
	public void process(Page page) {
		if(page.getUrl().regex(URL_LIST).match()) {
			page.addTargetRequests(page.getHtml().xpath("//div[@class='article_blk']").links().regex(URL_POST).all());
		} else {
			page.putField("date", page.getHtml().xpath("//div[@id='articlebody']/div[1]/span[@class='time SG_txtc']/text()"));
			page.putField("title", page.getHtml().xpath("//*[@id='articlebody']/div[1]/h2/text()"));
//			page.putField("content", page.getHtml().xpath("//*[@id='sina_keyword_ad_area2']"));
		}

	}

	@Override
	public Site getSite() {
		return site;
	}

	public static void main(String[] args) {
		Spider.create(new TestSinaBlog())
		.addUrl("http://blog.sina.com.cn/s/articlelist_1487828712_0_1.html")
		.addPipeline(new JsonFilePipeline("G:\\CrawlerResult\\"))
//		.addPipeline(new ConsolePipeline())
		.thread(3)
		.run();
	}

}
