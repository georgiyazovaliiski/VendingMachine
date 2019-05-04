package components.entity;

import java.math.BigDecimal;

public class Coin {
    private Currency currency;
    private BigDecimal value;
    private CoinType coinType;

    public CoinType getCoinType() {
        return coinType;
    }

    public void setCoinType(CoinType coinType) {
        this.coinType = coinType;
    }

    public Coin(){
        this.currency = Currency.EURO;
        this.value = BigDecimal.valueOf(0.01);
    }

    public Coin(CoinType coinType){
        this.currency = Currency.EURO;
        this.coinType = coinType;
        estimateCoinValue(coinType);
    }

    private void estimateCoinValue(CoinType coinType) {
        switch (coinType){
            case TWO_CENT: this.value = BigDecimal.valueOf(0.02); break;
            case FIVE_CENT: this.value = BigDecimal.valueOf(0.05); break;
            case TEN_CENT: this.value = BigDecimal.valueOf(0.10); break;
            case TWENTY_CENT: this.value = BigDecimal.valueOf(0.20); break;
            case FIFTY_CENT: this.value = BigDecimal.valueOf(0.50); break;
            case ONE_EURO: this.value = BigDecimal.valueOf(1.00); break;
            default: this.value = BigDecimal.valueOf(0.01); break;
        }
        this.setCoinType(coinType);
    }

    private Currency getCurrency() {
        return currency;
    }

    public BigDecimal getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coin coin = (Coin) o;
        if (this.getCurrency() != coin.getCurrency()) return false;
        if (!this.getValue().equals(coin.getValue())) return false;
        return this.getCoinType() == coin.getCoinType();
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (this.getValue() != null ? this.getValue().hashCode() : 0);
        result = 31 * result + (this.getCoinType() != null ? this.getCoinType().hashCode() : 0);
        result = 31 * result + (this.getCurrency() != null ? this.getCurrency().hashCode() : 0);
        return result;
    }
}
