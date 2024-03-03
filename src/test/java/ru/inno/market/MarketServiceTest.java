package ru.inno.market;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.inno.market.core.Catalog;
import ru.inno.market.core.MarketService;
import ru.inno.market.model.Client;
import ru.inno.market.model.Item;
import ru.inno.market.model.PromoCodes;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MarketServiceTest {
    private Catalog catalog = new Catalog();
    private Client client;
    private MarketService market = new MarketService();

    @Test
    @DisplayName("Добавление товара в заказ из маркета")
    @Tag("add")
    @Tag("all")
    public void addTwoItemsToOrder() {
        client = new Client(1, "Andrey");
        int order = market.createOrderFor(client);

        market.addItemToOrder(catalog.getItemById(1), order);
        market.addItemToOrder(catalog.getItemById(8), order);

        assertEquals(market.getOrderInfo(order).getCart().size(), 2);
    }

    @Test
    @DisplayName("Применяем скидку")
    @Tag("discount")
    @Tag("all")
    public void acceptDiscount() {
        client = new Client(2, "Dima");
        int order = market.createOrderFor(client);

        Item item = catalog.getItemById(6);
        market.addItemToOrder(item, order);
        double price = market.getOrderInfo(order).getTotalPrice();

        market.applyDiscountForOrder(order, PromoCodes.FIRST_ORDER);
        assertEquals((price * (1 - PromoCodes.FIRST_ORDER.getDiscount())), market.getOrderInfo(order).getTotalPrice());
    }

    @Test
    @DisplayName("Применяем скидку дважды")
    @Tag("discount")
    @Tag("all")
    public void doubleDiscount() {
        client = new Client(3, "Artemchik");
        int order = market.createOrderFor(client);

        Item item = catalog.getItemById(6);
        market.addItemToOrder(item, order);
        double price = market.getOrderInfo(order).getTotalPrice();

        market.applyDiscountForOrder(order, PromoCodes.FIRST_ORDER);
        assertEquals((price * (1 - PromoCodes.FIRST_ORDER.getDiscount())), market.getOrderInfo(order).getTotalPrice());
        market.applyDiscountForOrder(order, PromoCodes.VDUD); // не должна примениться
        assertEquals((price * (1 - PromoCodes.FIRST_ORDER.getDiscount())), market.getOrderInfo(order).getTotalPrice());
    }
}

