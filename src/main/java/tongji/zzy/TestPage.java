package tongji.zzy;

import tongji.zzy.handler.HandlerWithJson;
import tongji.zzy.model.TestModel;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

public class TestPage implements PageProcessor {

	private Site site = Site.me().setRetryTimes(3).setSleepTime(100);


	@Override
	public void process(Page page) {
		// page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/\\w+/\\w+)").all());
		page.putField("author", page.getUrl().regex("https://github\\.com/(\\w+).*"));
		page.putField("name", page.getHtml()
				.xpath("//div[@class='main-content']//h1[@class='vcard-names']/span[@class='vcard-fullname']/text()"));
				// page.putField("name",
				// page.getHtml().xpath("//h1/span[@itemprop='name']/text()").toString());
				// page.putField("title",
				// page.getHtml().xpath("html/body/div[@class='mainbox']/div/div/h4/text()").toString());
				// page.putField("date",
				// page.getHtml().xpath("html/body/div[@class='mainbox']/div/div/h5/text()").toString());
				// page.putField("content",
				// page.getHtml().xpath("html/body/div[@class='mainbox']/div/div/pre/text()").toString());


		// if (page.getResultItems().get("title")==null){
		// //skip this page
		// page.setSkip(true);
		// }
		// page.putField("readme",
		// page.getHtml().xpath("//div[@id='readme']/tidyText()"));
		// TestHandlerWithJson.convertJavaClassToJsonStream(storeDataToModeClass(page.getResultItems()));
//		HandlerWithJson.storeJsonStreamToFile(storeDataToModeClass(page.getResultItems()));
	}


	public TestModel storeDataToModeClass(ResultItems result) {
		TestModel data = new TestModel();
		data.setAuthor(result.get("author").toString());
		data.setName(result.get("name").toString());

		System.out.println(data.getAuthor());
		System.out.println(data.getName());

		return data;

	}

	@Override
	public Site getSite() {
		return site;
	}

	public static void main(String[] args) {
		Spider.create(new TestPage()).addUrl("https://github.com/code4craft")
				// Spider.create(new
				// GithubRepoPageProcessor()).addUrl("http://data.eastmoney.com/notice/20151111/2Wvl2Y5kGQ9T3l.html").thread(5).run();
				// Spider.create(new TestPage())
				// .addUrl("http://pdf.dfcfw.com/pdf/H2_AN201511130011403438_1.pdf")
				// .addPipeline(new ConsolePipeline())
				// .addPipeline(new FilePipeline("result"))
				.thread(5).run();
	}

}
