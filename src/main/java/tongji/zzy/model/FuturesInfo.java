package tongji.zzy.model;

import java.util.Date;

/**
 * Created by zhouzhiyong on 2017/1/4.
 */
public class FuturesInfo {
    /**
     * 合约信息
     */
    //合约代码
    private String code;
    // 到期日
    private Date offMarketDate;
    // 开始交割日
    private Date startOfferDate;
    // 最后交割日
    private Date endOfferDate;
    // 挂牌基准价
    private double basePrice;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getOffMarketDate() {
        return offMarketDate;
    }

    public void setOffMarketDate(Date offMarketDate) {
        this.offMarketDate = offMarketDate;
    }

    public Date getStartOfferDate() {
        return startOfferDate;
    }

    public void setStartOfferDate(Date startOfferDate) {
        this.startOfferDate = startOfferDate;
    }

    public Date getEndOfferDate() {
        return endOfferDate;
    }

    public void setEndOfferDate(Date endOfferDate) {
        this.endOfferDate = endOfferDate;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }
}
