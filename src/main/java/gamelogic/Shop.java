
package gamelogic;

public class Shop {

    public static int money = 2000;
    public static int flowers = 30;
    public static int mango = 3;

    public Shop(int money, int flowers) {
        Shop.money = money;
        Shop.flowers = flowers;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        Shop.money = money;
    }

    public int getFlowers() {
        return flowers;
    }

    public void setFlowers(int flowers) {
        Shop.flowers = flowers;
    }

}
