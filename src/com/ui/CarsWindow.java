package com.ui;

import com.db.DatabaseConnection;
import com.dto.Car;
import com.dto.Order;
import com.vaadin.event.SelectionEvent;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.ArrayList;

public class CarsWindow extends Window {

    public OrdersWindow ordersWindow = new OrdersWindow();
    public int currentClient = 0;
    public CarsGrid carsGrid = new CarsGrid();
    public CarForm carForm = new CarForm();
    public Button addCar = new Button("Add car");
    public Button close = new Button("Close");

    public CarsWindow() {
        super("List of cars:");
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

        HorizontalLayout actions = new HorizontalLayout(addCar, close);
        actions.setWidth("100%");
        actions.setSpacing(true);

        left.addComponent(carsGrid);
        left.addComponent(actions);
        right.addComponent(carForm);

        left.setMargin(true);
        mainLayout.setMargin(true);

        mainLayout.addComponent(left);
        mainLayout.addComponent(right);

        setContent(mainLayout);
    }

    private void configureComponents() {
        addCar.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        close.setStyleName(ValoTheme.BUTTON_DANGER);

        addCar.setWidth("100%");
        close.setWidth("100%");
        setModal(true);
        setWidth("80%");
        setHeight("80%");

        ordersWindow.setVisible(false);
        setClosable(true);
        carForm.setVisible(false);
    }

    private void addListeners() {
        carForm.save.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                boolean flag = carForm.validate();
                if (flag) {
                    Car car = new Car();
                    car.setMake(carForm.make.getValue());
                    car.setModel(carForm.model.getValue());
                    car.setYear(carForm.year.getValue());
                    car.setVIN(carForm.VIN.getValue());
                    car.setCarId(carForm.currentCar);
                    car.setClientId(currentClient);

                    if (car.getCarId() == 0 ) {
                        DatabaseConnection.addCar(car);
                        Notification.show("Car added", Notification.Type.HUMANIZED_MESSAGE);
                        updateCarsList();
                        carForm.setVisible(false);
                    } else {
                        DatabaseConnection.updateCar(car);
                        Notification.show("Information updated", Notification.Type.HUMANIZED_MESSAGE);
                        updateCarsList();
                        carForm.setVisible(false);
                    }
                } else {
                    Notification.show("Invalid input", Notification.Type.WARNING_MESSAGE);
                }
            }
        });

        carsGrid.addSelectionListener(new SelectionEvent.SelectionListener() {
            @Override
            public void select(SelectionEvent selectionEvent) {
                try {
                    carForm.edit((Car) carsGrid.getSelectedRow());
                    carForm.setVisible(true);
                    carForm.viewOrders.setVisible(true);
                    carForm.deleteCar.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        addCar.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                carForm.clean();
                carForm.viewOrders.setVisible(false);
                carForm.deleteCar.setVisible(false);
                Car car = new Car();
                carForm.edit(car);
            }
        });

        carForm.cancel.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                carForm.clean();
                carForm.setVisible(false);
            }
        });

        carForm.viewOrders.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {

                ordersWindow.setVisible(true);
                ordersWindow.fillOrders(carForm.currentCar);
                ordersWindow.orderForm.setVisible(false);
                getUI().addWindow(ordersWindow);
            }
        });

        carForm.deleteCar.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                ArrayList<Order> list = DatabaseConnection.getOrders(carForm.currentCar);
                if (list.size() == 0) {
                    DatabaseConnection.deleteCar(carForm.currentCar);
                    Notification.show("Car deleted", Notification.Type.HUMANIZED_MESSAGE);
                    updateCarsList();
                    carForm.setVisible(false);
                } else {
                    Notification.show("Forbidden. There are orders", Notification.Type.ERROR_MESSAGE);
                }
            }
        });

        close.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                close();
            }
        });
    }

    public void fillCars(int clientId) {
        currentClient = clientId;
        ArrayList<Car> list = DatabaseConnection.getCars(clientId);
        carsGrid.cleanData();
        carsGrid.fillCarGrid(list);
    }

    public void updateCarsList() {
        carsGrid.getContainerDataSource().removeAllItems();
        ArrayList<Car> list = DatabaseConnection.getCars(currentClient);
        for (Car car : list) {
            carsGrid.getContainerDataSource().addItem(car);
        }
    }
}
