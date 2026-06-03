package org.coffeecart5340.ui.enumData;

public enum CoffeeType {
    ESPRESSO("Espresso", "特浓咖啡", 10.00),
    ESPRESSO_MACCHIATO("Espresso Macchiato", "浓缩玛奇朵", 12.00),
    CAPPUCCINO("Capuccino", "卡布奇诺", 19.00),
    MOCHA("Mocha", "摩卡", 8.00),
    FLAT_WHITE("Flat White", "平白咖啡", 18.00),
    AMERICANO("Americano", "美式咖啡", 7.00),
    CAFE_LATTE("Cafe Latte", "拿铁", 16.00),
    ESPRESSO_CON_PANNA("Espresso Сon Panna", "浓缩康宝蓝", 14.00),
    CAFE_BRAVE("Cafe Brave", "半拿铁", 15.00);

    private final String coffee;
    private final String chineseCoffee;
    private final double price;

    CoffeeType(String coffee, String chineseCoffee, double price) {
        this.coffee = coffee;
        this.chineseCoffee = chineseCoffee;
        this.price = price;
    }

    public String getCoffee() {
        return coffee;
    }

    public String getChineseCoffee() {
        return chineseCoffee;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString(){
        return coffee;
    }
}
