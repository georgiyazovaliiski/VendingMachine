package components.comparator;

import components.entity.Coin;

import java.util.Comparator;

public class CoinComparator implements Comparator<Coin> {
    @Override
    public int compare(Coin c1, Coin c2) {
        return c2.getValue().compareTo(c1.getValue());
    }
}
