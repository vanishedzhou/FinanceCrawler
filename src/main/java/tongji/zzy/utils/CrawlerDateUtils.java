package tongji.zzy.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhouzhiyong on 2016/11/29.
 */
public class CrawlerDateUtils {
    private static final DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final DateFormat sdfSimpleOne = new SimpleDateFormat("yyyy-MM-dd");

    public static Date parseStringToDate(String target) {
        Date date = null;
        try {
            date = sdf.parse(target);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public static Date parseStringToDateSimpleOne(String target) {
        Date date = null;
        try {
            date = sdfSimpleOne.parse(target);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public static String extractDateContent(String dateContainedStr) {
        Pattern pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
        Matcher matcher = pattern.matcher(dateContainedStr);
        if(matcher.find()) {
            return matcher.group();
        } else {
            return "";
        }
    }

    public static void main(String[] args) {
        String dateStr = "发布日期: 2017-01-12";
        System.out.println(CrawlerDateUtils.extractDateContent(dateStr));
//        Date date = parseStringToDate("2016-11-29 21:42:00");
//
//        System.out.println(date);
//
//        CrawlerStringUtils.main(new String[]{});
    }
}
