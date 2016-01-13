package tongji.zzy.FinanceCrawler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tongji.zzy.resource.DisclosureCategory;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		
		Pattern pattern = Pattern.compile(".*代码：(\\d+).*简称：(\\S+).*");
		Matcher match = pattern.matcher("证券代码：000973         证券简称：佛塑科技   abc");
		System.out.println(match.find());
		System.out.println(match.group(1));
		System.out.println(match.group(2));

		
		Pattern pattern1 = Pattern.compile(".*代码：(\\d+).*简称：.*");
		Matcher match1 = pattern1.matcher("证券代码：000973         证券简称：佛塑科技   ");
		System.out.println(match1.find());
		System.out.println(match1.group(1));
		
//		DisclosureCategory dc = DisclosureCategory.定期报告;
//		System.out.println(dc);
//		for(DisclosureCategory dcItem : DisclosureCategory.values()) {
//			System.out.println(dcItem.name());
//		}
	}
}
