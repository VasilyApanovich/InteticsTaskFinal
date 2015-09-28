package com.ui;

import com.dto.Order;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Grid;

import java.util.ArrayList;

public class OrdersGrid extends Grid {

    public OrdersGrid() {
        setWidth("100%");
        setEditorEnabled(false);
        setSelectionMode(SelectionMode.SINGLE);

        setContainerDataSource(new BeanItemContainer<>(Order.class));
        setColumnOrder("date", "cost", "description", "status");
        removeColumn("carId");
        removeColumn("orderId");
    }

    public void fillOrderGrid(ArrayList<Order> list) {
        for (Order order : list) {
            getContainerDataSource().addItem(order);
        }
    }

    public void cleanData() {
        getContainerDataSource().removeAllItems();
    }
}
