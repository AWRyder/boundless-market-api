package net.wyvernia.boundlessmarketapi.orders;

public enum MarketOrderType {
    BUYING,
    SELLING;

    public static MarketOrderType parse(String value){
        return MarketOrderType.valueOf(value.toUpperCase());
    }
}
