package tongji.zzy;

import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.model.ConsolePageModelPipeline;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.HelpUrl;
import us.codecraft.webmagic.model.annotation.TargetUrl;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;

@TargetUrl(value = { "http://data.eastmoney.com/notice/[0-9]+/\\w+.html" })
@HelpUrl(value = {"http://data.eastmoney.com/notice/"})
public class TestPageAnnotationPattern{

	@ExtractBy(value = "html/body/div[@class='mainbox']/div/div/h4/text()")
	private String title;
	
	@ExtractBy(value = "html/body/div[@class='mainbox']/div/div/h5/text()")
	private String date;
	
//	@ExtractBy(value = "html/body/div[@class='mainbox']/div/div/pre/text()")
//	private String content;

	public static void main(String[] args) {
		OOSpider.create(Site.me().setSleepTime(1000), new ConsolePageModelPipeline(), TestPageAnnotationPattern.class)
			.addUrl("http://data.eastmoney.com/notice/")
//			.addPipeline(new JsonFilePipeline("result"))
			.thread(5).run(); }

}
