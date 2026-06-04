package cafeorder.domain;

import java.util.List;

public class OrderItem {
    private Menu menu;
    private List<Option> options;
    private int quantity;

    public OrderItem(Menu menu, List<Option> options, int quantity) {
        this.menu = menu;
        this.options = options;
        this.quantity = quantity;
    }

    public int getTotalPrice() {
        int optionTotal = options.stream().mapToInt(Option::getPrice).sum();
        return (menu.getPrice() + optionTotal) * quantity;
    }

    public Menu getMenu() {
        return menu;
    }

    public List<Option> getOptions() {
        return options;
    }

    public int getQuantity() {
        return quantity;
    }
}
