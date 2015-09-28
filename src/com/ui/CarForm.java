package com.ui;

import com.dto.Car;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

public class CarForm extends FormLayout {

    public int currentCar = 0;
    public Button save = new Button("Apply");
    public Button cancel = new Button("Cancel");
    public Button viewOrders = new Button("Related orders");
    public Button deleteCar = new Button("Delete car");
    public TextField make = new TextField("Make");
    public TextField model = new TextField("Model");
    public TextField year = new TextField("Year");
    public TextField VIN = new TextField("VIN");

    Car car;
    BeanFieldGroup<Car> formFieldBindings;

    public CarForm() {
        buildLayout();
        configureLayout();
    }

    private void buildLayout() {
        HorizontalLayout actions = new HorizontalLayout(save, cancel);
        actions.setSpacing(true);
        addComponents(make, model, year, VIN, actions, viewOrders, new Label(""), deleteCar);
    }

    private void configureLayout() {
        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        cancel.setStyleName(ValoTheme.BUTTON_DANGER);
    }

    public void edit(Car car) {
        try {
            currentCar = car.getCarId();
            this.car = car;
            if(car != null) {
                // Bind the properties of the car POJO to fiels in this form
                formFieldBindings = BeanFieldGroup.bindFieldsBuffered(car, this);
                make.focus();
            }
            setVisible(car != null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clean() {
        make.setValue("");
        model.setValue("");
        year.setValue("");
        VIN.setValue("");
    }

    public boolean validate() {

        boolean validation = false;
        String make = "^[a-zA-Z]{2,15}$";
        String year = "^[0-9]{4}$";

        if (this.make.getValue().matches(make) && this.model.getValue().length() > 0 &&
            this.model.getValue().length() < 44 && this.year.getValue().matches(year) &&
            this.VIN.getValue().length() > 1 && this.VIN.getValue().length() < 44) {

                validation = true;
        }
        return validation;
    }
}
