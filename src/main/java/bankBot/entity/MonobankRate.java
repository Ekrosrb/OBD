package bankBot.entity;


import com.google.gson.annotations.Expose;

public class MonobankRate {
    private int currencyCodeA;
    private int currencyCodeB;
    private int date;
    private double rateSell;
    private double rateBuy;
    private double rateCross;

    public MonobankRate(int currencyCodeA, int currencyCodeB, int date, double rateSell, double rateBuy, double rateCross) {
        this.currencyCodeA = currencyCodeA;
        this.currencyCodeB = currencyCodeB;
        this.date = date;
        this.rateSell = rateSell;
        this.rateBuy = rateBuy;
        this.rateCross = rateCross;
    }

    public int getCurrencyCodeA() {
        return currencyCodeA;
    }

    public int getCurrencyCodeB() {
        return currencyCodeB;
    }

    public int getDate() {
        return date;
    }

    public double getRateSell() {
        return rateSell;
    }

    public double getRateBuy() {
        return rateBuy;
    }

    public double getRateCross() {
        return rateCross;
    }
}

