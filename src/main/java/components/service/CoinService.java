package components.service;


import components.entity.Coin;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

@Service
public interface CoinService {
    void insertCoins(List<Coin> coins);
    void insertCoins(Coin coin);
    void insertStockCoins(List<Coin> coins);
    void insertStockCoins(HashMap<Coin,Integer> coins);
    void insertStockCoins(Coin coin);

    BigDecimal getCurrentResources();
    HashMap<Coin,Integer> getCurrentCoins();
    HashMap<Coin,Integer> getStockCoins();

    void userInsertCoins(boolean internalInsert, String... args);
    void giveChange(BigDecimal totalBudget, BigDecimal totalBill);
}
