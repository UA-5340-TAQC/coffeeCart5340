package org.coffeecart5340.ui.enumData;

public enum CoffeeType {
    ESPRESSO("Espresso", "特浓咖啡"),
    ESPRESSO_MACCHIATO("Espresso Macchiato", "浓缩玛奇朵"),
    CAPPUCCINO("Capuccino", "卡布奇诺 "),
    MOCHA("Mocha", "摩卡"),
    FLAT_WHITE("Flat White", "平白咖啡"),
    AMERICANO("Americano", "美式咖啡"),
    CAFE_LATTE("Cafe Latte", "拿铁"),
    ESPRESSO_CON_PANNA("Espresso Сon Panna", "浓缩康宝蓝"),
    CAFE_BRAVE("Cafe Brave", "半拿铁");

    private final String coffee;
    private final String chineseCoffee;

     CoffeeType(String coffee, String chineseCoffee) {
        this.coffee = coffee;
        this.chineseCoffee = chineseCoffee;
    }

    public String getCoffee() {
        return coffee;
    }

    public String getChineseCoffee() {
        return chineseCoffee;
    }

    @Override
    public String toString(){
         return coffee;
    }
}
