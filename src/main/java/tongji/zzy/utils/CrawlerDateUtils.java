package tongji.zzy.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhouzhiyong on 2016/11/29.
 */
public class CrawlerDateUtils {
    private static final DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static Date parseStringToDate(String target) {
        Date date = null;
        try {
            date = sdf.parse(target);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public static void main(String[] args) {
        Date date = parseStringToDate("2016-11-29 21:42:00");

        System.out.println(date);

        CrawlerStringUtils.main(new String[]{});
    }
}
