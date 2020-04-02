package bankBot.entity;

public class PrivatbankRate {
    private String ccy;
    private String base_ccy;
    private String buy;
    private String sale;

    public PrivatbankRate(String ccy, String base_ccy, String buy, String sale) {
        this.ccy = ccy;
        this.base_ccy = base_ccy;
        this.buy = buy;
        this.sale = sale;
    }

    public String getCcy() {
        return ccy;
    }

    public String getBaseCcy() {
        return base_ccy;
    }

    public String getRateBuy() {
        return buy;
    }

    public String getRateSell() {
        return sale;
    }
}
