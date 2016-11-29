package tongji.zzy.utils;

/**
 * Created by zhouzhiyong on 2016/11/29.
 */
public class CrawlerStringUtils {
    public static String removeHtmlElements(String target) {
        return target.replaceAll("<[^>]*>", "");
    }

    public static void main(String[] args) {
        System.out.println(removeHtmlElements("<aa>sdf<asdgfdg>ddcff"));
    }
}
