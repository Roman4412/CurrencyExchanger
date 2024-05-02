package com.projects.study.dto;

import com.projects.study.entity.ExchangeRate;

import java.math.BigDecimal;

public class ExchangeResponse {
    ExchangeRate exchangeRate;
    BigDecimal amount;
    BigDecimal convertedAmount;

    public void setExchangeRate(ExchangeRate exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setConvertedAmount(BigDecimal convertedAmount) {
        this.convertedAmount = convertedAmount;
    }

    public ExchangeRate getExchangeRate() {
        return exchangeRate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getConvertedAmount() {
        return convertedAmount;
    }

}
