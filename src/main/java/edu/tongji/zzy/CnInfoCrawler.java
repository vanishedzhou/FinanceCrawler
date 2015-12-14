package edu.tongji.zzy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

public class CnInfoCrawler implements PageProcessor {
	private Site site = Site.me().setRetryTimes(5).setSleepTime(10000);

	@Override
	public Site getSite() {
		return site;
	}

	@Override
	public void process(Page page) {
		String[] itemName = {"companyCode", "infoTitle", "time"};
		Map<String, String> itemNameXPathMap = new HashMap<>();
		itemNameXPathMap.put(itemName[0], "//*[@id='ul_a_latest']/li[1]/div[@class='t1']/text()");
		itemNameXPathMap.put(itemName[1], "//*[@id='ul_a_latest']/li[1]/div[@class='t3']/dd/a/span[1]/text()");
		itemNameXPathMap.put(itemName[2], "//*[@id='ul_a_latest']/li[1]/div[@class='t3']/dd/a/span[3]/text()");
		
//		page.putField("companyCode", page.getHtml().xpath("//ul[@id='ul_a_latest']/li[1]/div[@class='t1']/text()"));
		page.putField("Name", page.getHtml().xpath("html/body/div[@class='main-content']//h1/span[1]/text()"));
		page.putField("starredNumber", page.getHtml().xpath("html/body//*[@id='js-pjax-container']/div/div/div[1]/div/a[2]/strong/text()"));
		page.putField("followingNumber", page.getHtml().xpath("html/body//*[@id='js-pjax-container']/div/div/div[1]/div/a[3]/strong/text()").toString().trim());
		System.out.println(page.getResultItems().get("followingNumber").toString());
		
//		for(int i=0; i<itemName.length; i++) {
//			page.putField(itemName[i], page.getHtml().xpath(itemNameXPathMap.get(itemName[i])));
//		}

	}

	public static void main(String[] args) {
		Spider.create(new CnInfoCrawler())
		.addUrl("https://github.com/vanishedzhou")
		.addPipeline(new ConsolePipeline())
//		.addPipeline(new FilePipeline("results"))
		.addPipeline(new JsonFilePipeline("results"))
		.thread(1) .run();

	}

}
