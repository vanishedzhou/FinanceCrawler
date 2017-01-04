package tongji.zzy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by zhouzhiyong on 2017/1/4.
 */
public class WeiboInfo extends BaseInfo{
    /**
     * weibo news info source
     */
    private String infoSource;

    /**
     * index
     */
    @JsonIgnore
    public static final String index = "weibo";

    public String getInfoSource() {
        return infoSource;
    }

    public void setInfoSource(String infoSource) {
        this.infoSource = infoSource;
    }
}
