package tongji.zzy.FinanceCrawler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tongji.zzy.handler.HandlerWithJson;
import tongji.zzy.model.BaseInfo;
import tongji.zzy.resource.DisclosureCategory;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		String projectWorkingDir = System.getProperty("user.dir");
		
		BaseInfo baseInfo= new BaseInfo();
		baseInfo.setDate(new Date());
		baseInfo.setContent("this a the content");
		baseInfo.setTitle("this is the title");
//		String result = HandlerWithJson.convertJavaClassToJsonStream(baseInfo);
//		System.out.println(result);
		HandlerWithJson.storeJsonStreamToFile(baseInfo, "test.json");
		
//		Date date = new Date();
//		System.out.println(date.toString());
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		System.out.println(sdf.format(date));
		
		
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		Date date = null;
//		try {
//			date = sdf.parse("2016-01-16");
//		} catch (parseexception e) {
//			e.printstacktrace();
//		}
//		system.out.println(sdf.format(date));
		
//		Pattern pattern = Pattern.compile(".*代码：(\\d+).*简称：(\\S+).*");
//		Matcher match = pattern.matcher("证券代码：000973         证券简称：佛塑科技   abc");
//		System.out.println(match.find());
//		System.out.println(match.group(1));
//		System.out.println(match.group(2));
//
//		
//		Pattern pattern1 = Pattern.compile(".*代码：(\\d+).*简称：.*");
//		Matcher match1 = pattern1.matcher("证券代码：000973         证券简称：佛塑科技   ");
//		System.out.println(match1.find());
//		System.out.println(match1.group(1));
		
//		DisclosureCategory dc = DisclosureCategory.定期报告;
//		System.out.println(dc);
//		for(DisclosureCategory dcItem : DisclosureCategory.values()) {
//			System.out.println(dcItem.name());
//		}
	}
}
