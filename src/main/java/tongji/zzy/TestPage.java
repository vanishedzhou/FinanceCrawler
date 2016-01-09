package tongji.zzy;

import tongji.zzy.handler.TestHandlerWithJson;
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
//        page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/\\w+/\\w+)").all());
//        page.putField("author", page.getUrl().regex("https://github\\.com/(\\w+).*"));
//        page.putField("name", page.getHtml().xpath("//div[@class='main-content']//h1[@class='vcard-names']/span[@class='vcard-fullname']/text()"));
        page.putField("li", page.getHtml().xpath("html/body/div[@class='middle']/div[@class='bulletin-list']/div[@class='ct-content']/div[@id='con-div-a-latest']/ul[@id='ul_a_latest']/li"));
        
        System.out.println(page.getResultItems().get("li").toString());
//        if (page.getResultItems().get("title")==null){
//            //skip this page
//            page.setSkip(true);
//        }
//        TestHandlerWithJson.convertJavaClassToJsonStream(storeDataToModeClass(page.getResultItems()));
//        TestHandlerWithJson.storeJsonStreamToFile(storeDataToModeClass(page.getResultItems()));
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
        Spider.create(new TestPage()).addUrl("http://www.cninfo.com.cn/cninfo-new/disclosure/szse")
        .thread(5)
        .run();
    }
}
