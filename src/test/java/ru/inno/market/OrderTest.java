package ru.inno.market;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.inno.market.core.Catalog;
import ru.inno.market.model.Client;
import ru.inno.market.model.Item;
import ru.inno.market.model.Order;
import ru.inno.market.model.PromoCodes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderTest {
    private Client client;
    private Catalog catalog = new Catalog();
    private Order order;

    @Test
    @DisplayName("Добавляем товар в заказ")
    @Tag("add")
    @Tag("all")
    public void addItem() {
        client = new Client(1, "Nikita");
        order = new Order(client.getId(), client);

        Item item = catalog.getItemById(7);
        order.addItem(item);

        assertTrue(order.getItems().toString().contains("HUAWEI MateBook E"));
        assertEquals(1, catalog.getCountForItem(item));
    }

    @Test
    @DisplayName("Применяем скидку")
    @Tag("discount")
    @Tag("all")
    public void acceptDiscount() {
        client = new Client(2, "Danya");
        order = new Order(client.getId(), client);

        Item item = catalog.getItemById(7);
        order.addItem(item);
        double priceBeforeDiscount = order.getTotalPrice();
        order.applyDiscount(PromoCodes.LOVE_DAY.getDiscount());

        assertEquals((priceBeforeDiscount * (1 - PromoCodes.LOVE_DAY.getDiscount())), order.getTotalPrice());
    }
}
