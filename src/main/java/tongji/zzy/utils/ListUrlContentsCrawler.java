package tongji.zzy.utils;

import org.slf4j.Logger;
import tongji.zzy.handler.HandlerWithJson;
import tongji.zzy.model.DisclosureInfo;
import tongji.zzy.resource.FCLog;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.sql.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhouzhiyong on 2016/11/28.
 */
@SuppressWarnings("Duplicates")
public class ListUrlContentsCrawler implements PageProcessor{
    private static final Logger logger = FCLog.getLogger(ListUrlContentsCrawler.class);

  	private Site site = Site.me().setRetryTimes(5).setSleepTime(10000);

    protected String listUrlPattern;
	protected String contentUrlPattern;
    protected String startUrl;

    //xpath blocks
    protected String listLinksBlockXpath;
    protected String contentLinksBlockXpath;

    ListUrlContentsCrawler(String contentUrlPattern, String listUrlPattern) {
        this.contentUrlPattern = contentUrlPattern;
        this.listUrlPattern = listUrlPattern;
    }

    ListUrlContentsCrawler(String contentUrlPattern, String listUrlPattern
            , String startUrl) {
        this.contentUrlPattern = contentUrlPattern;
        this.listUrlPattern = listUrlPattern;
        this.startUrl = startUrl;
    }

    ListUrlContentsCrawler(String contentUrlPattern, String listUrlPattern
            , String startUrl
            , String listLinksBlockXpath, String contentLinksBlockXpath) {
        this.contentUrlPattern = contentUrlPattern;
        this.listUrlPattern = listUrlPattern;
        this.startUrl = startUrl;
        this.contentLinksBlockXpath = contentLinksBlockXpath;
        this.listLinksBlockXpath = listLinksBlockXpath;
    }

    public void startCrawler(ListUrlContentsCrawler listUrlContentsCrawler) {
        Spider.create(listUrlContentsCrawler)
                .addUrl(listUrlContentsCrawler.startUrl)
                .thread(5)
                .run();
    }

    @Override
    public void process(Page page) {
        if (page.getUrl().regex(listUrlPattern).match()) {
            System.out.println("This is the list page...");

            // add content urls
            List<String> targetContentUrls = page.getHtml()
                    .xpath(contentLinksBlockXpath).links()
                    .regex(contentUrlPattern).all();
            page.addTargetRequests(targetContentUrls);

            // add list urls
            List<String> listUrls = page.getHtml().xpath(listLinksBlockXpath)
                    .links().regex(listUrlPattern).all();
            page.addTargetRequests(listUrls);
        } else if (page.getUrl().regex(contentUrlPattern).match()) {
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
            disclosureInfo.setDate(date);
            disclosureInfo.setTitle(title);
            disclosureInfo.setContent(String.valueOf(content.length()));
            disclosureInfo.setCompanyCode(companyCode);
            disclosureInfo.setCompanyName(companyName);

            // convert to JSon string
            String jsonString = HandlerWithJson.convertJavaClassToJsonStream(disclosureInfo);

        }

    }

    @Override
    public Site getSite() {
        return site;
    }
}
