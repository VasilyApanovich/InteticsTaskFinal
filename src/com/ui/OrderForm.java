package com.ui;

import com.dto.Order;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.Date;

public class OrderForm extends FormLayout {

    public int currentOrder = 0;
    public Button save = new Button("Apply");
    public Button cancel = new Button("Cancel");
    public Button deleteOrder = new Button("Delete order");

    public DateField date = new DateField("Date");
    public TextField cost = new TextField("Cost");
    public TextField description = new TextField("Description");
    public ComboBox status = new ComboBox("Status");


    Order order;
    BeanFieldGroup<Order> formFieldBindings;

    public OrderForm() {
        buildLayout();
        configureLayout();
    }

    private void buildLayout() {
        HorizontalLayout actions = new HorizontalLayout(save, cancel);
        status.addItem("In progress");
        status.addItem("Completed");
        status.addItem("Cancelled");
        status.setTextInputAllowed(false);
        actions.setSpacing(true);
        addComponents(date, cost, description, status, actions, new Label(""), deleteOrder);
    }

    private void configureLayout() {
        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        cancel.setStyleName(ValoTheme.BUTTON_DANGER);
        date.setDateFormat("dd.MM.yyyy");
    }

    public void edit(Order order) {
        try {
            currentOrder = order.getOrderId();
            this.order = order;
            if(order != null) {
                // Bind the properties of the order POJO to fiels in this form
                formFieldBindings = BeanFieldGroup.bindFieldsBuffered(order, this);
                date.focus();
            }
            setVisible(order != null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void newOrder() {
        date.setValue(new Date());
        date.setEnabled(false);
        description.setValue("");
        status.setValue("In progress");
        status.setEnabled(false);
        cost.setValue("");
    }

    public boolean validate() {

        boolean validation = false;
        String cost = this.cost.getValue();
        String costRegEx = "^[-+]?(\\d{1,8}|(?=\\d\\d*\\.\\d+?$)[\\d\\.]{1,8})$";
        if (this.date.getValue() != null && this.description.getValue().length() < 99 && cost.matches(costRegEx)) {

            float costF = Float.parseFloat(cost);
            if (costF >=0 && costF < 10000) {
                   validation = true;
            }
        }
        return validation;
    }
}
