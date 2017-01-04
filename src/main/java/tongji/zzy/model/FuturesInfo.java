package tongji.zzy.model;

import java.util.Date;

/**
 * Created by zhouzhiyong on 2017/1/4.
 */
public class FuturesInfo {
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


    /**
     * 合约信息
     */
    // 到期日
    private Date offMarketDate;
    // 开始交割日
    private Date startOfferDate;
    // 最后交割日
    private Date endOfferDate;
    // 挂牌基准价
    private double basePrice;
}
