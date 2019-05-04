package components.service;

import components.comparator.CoinComparator;
import components.entity.Coin;
import components.entity.CoinType;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;


@Service
public class CoinServiceImpl implements CoinService {
    private HashMap<Coin,Integer> currentCoins;

    private HashMap<Coin,Integer> stockCoins;

    public CoinServiceImpl() {
        currentCoins = new HashMap<>();
        stockCoins = new HashMap<>();
    }

    @Override
    public void insertCoins(List<Coin> coins) {
        coins.forEach(c->insertCoins(c));
    }

    @Override
    public void insertCoins(Coin coin) {
        currentCoins.put(coin,currentCoins.getOrDefault(coin,0)+1);
    }

    @Override
    public void insertStockCoins(List<Coin> coins) {
        coins.forEach(c->insertStockCoins(c));
    }

    @Override
    public void insertStockCoins(Coin coin) {
        this.stockCoins.put(coin,this.stockCoins.getOrDefault(coin,0)+1);
    }

    @Override
    public void insertStockCoins(HashMap<Coin, Integer> coins) {
        coins.forEach((k,v)->this.stockCoins.put(k,this.stockCoins.getOrDefault(k,0)+v));
    }

    @Override
    public BigDecimal getCurrentResources() {
        BigDecimal resources = new BigDecimal(0);

        for (Map.Entry<Coin, Integer> currentCoin : currentCoins.entrySet())
            resources = resources.add(currentCoin.getKey().getValue().multiply(new BigDecimal(currentCoin.getValue())));

        return resources;
    }

    @Override
    public HashMap<Coin,Integer> getCurrentCoins() {
        return this.currentCoins;
    }

    @Override
    public HashMap<Coin, Integer> getStockCoins() {
        return this.stockCoins;
    }

    @Override
    public void userInsertCoins(boolean internalInsert, String... args){
        List<Coin> coins = new ArrayList<>();
        Queue<String> unprocessed = new ArrayDeque<>();

        for (String arg : args) {
            switch (arg){
                case "one cent":
                    coins.add(new Coin(CoinType.CENT));
                    break;
                case "two cents":
                    coins.add(new Coin(CoinType.TWO_CENT));
                    break;
                case "five cents":
                    coins.add(new Coin(CoinType.FIVE_CENT));
                    break;
                case "ten cents":
                    coins.add(new Coin(CoinType.TEN_CENT));
                    break;
                case "twenty cents":
                    coins.add(new Coin(CoinType.TWENTY_CENT));
                    break;
                case "fifty cents":
                    coins.add(new Coin(CoinType.FIFTY_CENT));
                    break;
                case "one euro":
                    coins.add(new Coin(CoinType.ONE_EURO));
                    break;
                default:
                    unprocessed.add(arg); break;
            }
        }
        if(!internalInsert){
            insertCoins(coins);

            unprocessed.forEach(a-> System.out.println("Unprocessed coin: " + a));
            System.out.println("Current amount: " + getCurrentResources());
        }
        else{
            insertStockCoins(coins);
        }
    }

    @Override
    public void giveChange(BigDecimal totalBudget, BigDecimal totalBill){
        HashMap<Coin, Integer> usersCoins = this.getCurrentCoins();

        this.insertStockCoins(usersCoins);
        this.getCurrentCoins().clear();

        HashMap<Coin, Integer> allCoins = this.getStockCoins();

        List<Coin> coinKeys = new ArrayList<>(allCoins.keySet());

        Collections.sort(coinKeys, new CoinComparator());

        BigDecimal change = totalBudget.subtract(totalBill);
        System.out.println("Change: " + change);

        for (Coin coin : coinKeys) {
            BigDecimal quantityOfCoin = BigDecimal.ZERO;

            if(change.divideToIntegralValue(coin.getValue()).compareTo(BigDecimal.valueOf(1))>=0) {
                quantityOfCoin = change.divideToIntegralValue(coin.getValue());

                if(quantityOfCoin.intValueExact() > allCoins.getOrDefault(coin, 0))
                    quantityOfCoin = BigDecimal.valueOf(allCoins.getOrDefault(coin, 0));

                    change = change.subtract(quantityOfCoin.multiply(coin.getValue()));

                    this.stockCoins.put(coin, this.stockCoins.get(coin) - quantityOfCoin.intValueExact());


                if (quantityOfCoin.intValueExact() > 1)
                    System.out.println("\t -" + quantityOfCoin.intValueExact() + " coins of " + coin.getCoinType());

                else
                    System.out.println("\t -" + quantityOfCoin.intValueExact() + " coin of " + coin.getCoinType());
            }

            if(change.equals(BigDecimal.ZERO)){
                break;
            }
        }
    }
}
