package com.bambora.server.rest.vm;

import com.bambora.server.commons.Currency;

import java.math.BigDecimal;

public class DepositInfoVM {

    //@DecimalMax("10.0") @DecimalMin("0.0")
    private BigDecimal amount;

    private Currency currency;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
