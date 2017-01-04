package tongji.zzy.model;

import java.util.Date;

/**
 * Created by zhouzhiyong on 2017/1/4.
 */
public class FuturesData {
    /**
     * 交易参数
     */
    //合约代码
    private String code;
    // 上市日
    private Date onMarketDate;
    // 最新价
    private double currentPrice;
    // 涨跌
    private double changePercentage;
    // 持仓量
    private double holdMount;
    // 成交量
    private double exchangeMount;
    //成交金额
    private double exchangeSum;
    // 买卖价
    private double buyInAndOutPrice;
    // 昨结算
    private double lastDayPrice;
    // 开盘
    private double openedPrice;
    //最低
    private double lowestPrice;
    //最高
    private double highestPrice;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getOnMarketDate() {
        return onMarketDate;
    }

    public void setOnMarketDate(Date onMarketDate) {
        this.onMarketDate = onMarketDate;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public double getChangePercentage() {
        return changePercentage;
    }

    public void setChangePercentage(double changePercentage) {
        this.changePercentage = changePercentage;
    }

    public double getHoldMount() {
        return holdMount;
    }

    public void setHoldMount(double holdMount) {
        this.holdMount = holdMount;
    }

    public double getExchangeMount() {
        return exchangeMount;
    }

    public void setExchangeMount(double exchangeMount) {
        this.exchangeMount = exchangeMount;
    }

    public double getExchangeSum() {
        return exchangeSum;
    }

    public void setExchangeSum(double exchangeSum) {
        this.exchangeSum = exchangeSum;
    }

    public double getBuyInAndOutPrice() {
        return buyInAndOutPrice;
    }

    public void setBuyInAndOutPrice(double buyInAndOutPrice) {
        this.buyInAndOutPrice = buyInAndOutPrice;
    }

    public double getLastDayPrice() {
        return lastDayPrice;
    }

    public void setLastDayPrice(double lastDayPrice) {
        this.lastDayPrice = lastDayPrice;
    }

    public double getOpenedPrice() {
        return openedPrice;
    }

    public void setOpenedPrice(double openedPrice) {
        this.openedPrice = openedPrice;
    }

    public double getLowestPrice() {
        return lowestPrice;
    }

    public void setLowestPrice(double lowestPrice) {
        this.lowestPrice = lowestPrice;
    }

    public double getHighestPrice() {
        return highestPrice;
    }

    public void setHighestPrice(double highestPrice) {
        this.highestPrice = highestPrice;
    }
}
