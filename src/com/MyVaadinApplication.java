package com;

import com.db.DatabaseConnection;
import com.dto.Client;
import com.ui.*;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.event.SelectionEvent;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.ArrayList;

@SuppressWarnings("serial")
@Theme("valo")
public class MyVaadinApplication extends UI {

    final Grid clientsGrid = new ClientsGrid();
    final Button refresh = new Button("Refresh");
    final ClientForm clientForm = new ClientForm();
    final HorizontalLayout mainLayout = new HorizontalLayout();
    final VerticalLayout left = new VerticalLayout();
    final VerticalLayout right = new VerticalLayout();
    final SearchForm searchForm = new SearchForm();
    final CarsWindow carsWindow = new CarsWindow();
    final OrdersWindow ordersWindow = new OrdersWindow();

    @VaadinServletConfiguration(productionMode = false, ui = MyVaadinApplication.class)
    public static class Servlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {
        buildLayout();
        configureComponents();
    }
    public void buildLayout() {
        mainLayout.setSizeFull();
        setContent(mainLayout);

        left.setWidth("100%");
        right.setWidth("100%");
        refresh.setWidth("100%");

        right.addComponent(clientForm);
        left.addComponent(searchForm);
        left.addComponent(new Label(""));
        left.addComponent(clientsGrid);
        left.addComponent(new Label(""));
        left.addComponent(refresh);
        left.setMargin(true);

        mainLayout.addComponent(left);
        mainLayout.addComponent(right);

        carsWindow.setVisible(false);
        clientForm.setVisible(false);
    }

    public void configureComponents() {

        refresh.setStyleName(ValoTheme.BUTTON_DANGER);
        clientForm.setStyleName(ValoTheme.BUTTON_HUGE);

        searchForm.search.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                Client client;
                String firstName = searchForm.firstNameField.getValue();
                String lastName = searchForm.lastNameField.getValue();
                client = DatabaseConnection.findClient(firstName, lastName);
                clientForm.setVisible(false);
                if (client.getClientId() != 0) {
                    clientsGrid.getContainerDataSource().removeAllItems();
                    clientsGrid.getContainerDataSource().addItem(client);
                } else {
                    Notification.show("Client not found", Notification.Type.WARNING_MESSAGE);
                    updateClientsList();
                }

            }
        });

        searchForm.add.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                searchForm.clean();
                Client client = new Client();
                clientForm.edit(client);
                clientForm.viewCars.setVisible(false);
            }
        });

        clientForm.save.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                Client client = new Client();
                client.setFirstName(clientForm.firstName.getValue());
                client.setLastName(clientForm.lastName.getValue());
                client.setEmail(clientForm.email.getValue());
                client.setPhone(clientForm.phone.getValue());
                client.setAddress(clientForm.address.getValue());
                client.setDateOfBirth(clientForm.birthDate.getValue());
                client.setClientId(clientForm.currentClient);

                boolean flag = clientForm.validate();

                if (client.getClientId() == 0 && flag == true) {
                    DatabaseConnection.addClient(client);
                    Notification.show("Client added", Notification.Type.HUMANIZED_MESSAGE);
                    client = DatabaseConnection.findClient(client.getFirstName(), client.getLastName());
                    clientsGrid.getContainerDataSource().removeAllItems();
                    clientsGrid.getContainerDataSource().addItem(client);
                    clientForm.setVisible(false);
                } else if (flag == true) {
                    DatabaseConnection.updateClient(client);
                    Notification.show("Information updated", Notification.Type.HUMANIZED_MESSAGE);
                    updateClientsList();
                    client = DatabaseConnection.findClient(client.getFirstName(), client.getLastName());
                    clientsGrid.getContainerDataSource().removeAllItems();
                    clientsGrid.getContainerDataSource().addItem(client);
                    clientForm.setVisible(false);
                } else {
                    Notification.show("Invalid input", Notification.Type.WARNING_MESSAGE);
                }

      }
        });

        clientForm.cancel.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                clientForm.setVisible(false);
                clientsGrid.getContainerDataSource().removeAllItems();
                updateClientsList();
            }
        });

        clientsGrid.addSelectionListener(new SelectionEvent.SelectionListener() {
            @Override
            public void select(SelectionEvent selectionEvent) {
                try {
                    clientForm.edit((Client) clientsGrid.getSelectedRow());
                    clientForm.viewCars.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });



        clientForm.viewCars.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                carsWindow.setVisible(true);
                carsWindow.fillCars(clientForm.currentClient);
                carsWindow.carForm.clean();
                carsWindow.carForm.setVisible(false);
                getUI().addWindow(carsWindow);
            }
        });

        clientForm.viewOrders.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                ordersWindow.setVisible(true);
                getUI().addWindow(ordersWindow);
            }
        });

        refresh.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                updateClientsList();
                clientForm.setVisible(false);
                searchForm.clean();
            }
        });
    }

    public void updateClientsList() {
        clientsGrid.getContainerDataSource().removeAllItems();
        ArrayList<Client> list = DatabaseConnection.getListFromDb();
        for (Client client1 : list) {
            clientsGrid.getContainerDataSource().addItem(client1);
        }
    }
}