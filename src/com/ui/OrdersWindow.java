package com.ui;

import com.db.DatabaseConnection;
import com.dto.Order;
import com.vaadin.event.SelectionEvent;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.ArrayList;

public class OrdersWindow extends Window {

    public int currentCar = 0;
    public OrdersGrid ordersGrid = new OrdersGrid();
    public OrderForm orderForm = new OrderForm();
    public Button addOrder = new Button("New order");
    public Button close = new Button("Close");

    public OrdersWindow() {
        super("List of orders:");
        center();
        buildLayout();
        configureComponents();
        addListeners();
    }

    private void buildLayout() {
        HorizontalLayout mainLayout = new HorizontalLayout();
        mainLayout.setSizeFull();

        VerticalLayout left = new VerticalLayout();
        VerticalLayout right = new VerticalLayout();

        HorizontalLayout actions = new HorizontalLayout(addOrder, close);
        actions.setWidth("100%");
        actions.setSpacing(true);

        left.addComponent(ordersGrid);
        left.addComponent(actions);
        right.addComponent(orderForm);

        left.setMargin(true);
        mainLayout.setMargin(true);

        mainLayout.addComponent(left);
        mainLayout.addComponent(right);

        setContent(mainLayout);
    }

    private void configureComponents() {
        addOrder.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        close.setStyleName(ValoTheme.BUTTON_DANGER);

        addOrder.setWidth("100%");
        close.setWidth("100%");

        setWidth("80%");
        setHeight("80%");

        setModal(true);
        setClosable(true);

        orderForm.setVisible(false);
    }

    public void addListeners() {
        addOrder.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                orderForm.currentOrder = 0;
                orderForm.newOrder();
                orderForm.setVisible(true);
                orderForm.deleteOrder.setVisible(false);

            }
        });

        ordersGrid.addSelectionListener(new SelectionEvent.SelectionListener() {
            @Override
            public void select(SelectionEvent selectionEvent) {
                try {
                    orderForm.edit((Order) ordersGrid.getSelectedRow());
                    orderForm.date.setEnabled(true);
                    orderForm.status.setEnabled(true);
                    orderForm.deleteOrder.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        orderForm.save.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                boolean flag = orderForm.validate();
                if (flag) {
                    Order order = new Order();
                    order.setStatus(String.valueOf(orderForm.status.getValue()));
                    order.setDescription(orderForm.description.getValue());
                    order.setCost(orderForm.cost.getValue());
                    order.setDate(orderForm.date.getValue());
                    order.setCarId(currentCar);
                    order.setOrderId(orderForm.currentOrder);

                    if (order.getOrderId() == 0) {
                        DatabaseConnection.addOrder(order);
                        Notification.show("Order added", Notification.Type.HUMANIZED_MESSAGE);
                        updateOrdersList();
                        orderForm.setVisible(false);
                    } else {
                        DatabaseConnection.updateOrder(order);
                        Notification.show("Information updated", Notification.Type.HUMANIZED_MESSAGE);
                        updateOrdersList();
                        orderForm.setVisible(false);
                    }

                } else {
                    Notification.show("Invalid input", Notification.Type.WARNING_MESSAGE);
                }
            }
        });

        orderForm.cancel.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                orderForm.setVisible(false);
            }
        });
        orderForm.deleteOrder.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                DatabaseConnection.deleteOrder(orderForm.currentOrder);
                Notification.show("Order deleted", Notification.Type.HUMANIZED_MESSAGE);
                updateOrdersList();
                orderForm.setVisible(false);
            }
        });

        close.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                close();
            }
        });
    }

    public void fillOrders(int carId) {
        currentCar = carId;
        ArrayList<Order> list = DatabaseConnection.getOrders(carId);
        ordersGrid.cleanData();
        ordersGrid.fillOrderGrid(list);
    }

    public void updateOrdersList() {
        ordersGrid.getContainerDataSource().removeAllItems();
        ArrayList<Order> list = DatabaseConnection.getOrders(currentCar);
        for (Order order : list) {
            ordersGrid.getContainerDataSource().addItem(order);
        }
    }

}
