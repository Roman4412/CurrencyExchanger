package com.projects.study.entity;

import java.math.BigDecimal;

public class ExchangeRate {
    private long id;
    private Currency baseCurrency;
    private Currency targetCurrency;
    private BigDecimal rate;

    public long getId() {
        return id;
    }

    public Currency getBaseCurrency() {
        return baseCurrency;
    }

    public Currency getTargetCurrency() {
        return targetCurrency;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setBaseCurrency(Currency baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public void setTargetCurrency(Currency targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "ExchangeRate{" + "id=" + id + ", baseCurrency=" + baseCurrency + ", targetCurrency=" + targetCurrency + ", rate=" + rate + '}';
    }

}
